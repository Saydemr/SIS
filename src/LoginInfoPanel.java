/*
    END OF SERVICE
*/
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginInfoPanel extends JPanel {

    JButton initiateSession;
    JRadioButton safari;
    JRadioButton chrome;
    JRadioButton opera;
    JTextField usernameField;
    JPasswordField passwordField;
    JFrame jFrame;
    JTabbedPane jTabbedPane;
    WebDriver driver;

    public static String OsInfo;

    public LoginInfoPanel(JFrame jFrame) {
        this.jFrame = jFrame;
        OsInfo = System.getProperty("os.name").split(" ")[0];
        Globals.OsInfo = OsInfo;

        this.setLayout(new GridLayout(3, 2, 7, 7));
        this.setBorder(BorderFactory.createTitledBorder("SIS Login Info"));
        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");



        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();

        this.passwordField.setEchoChar('*');

        initiateSession = new JButton("Open Session");

        this.initiateSession.addActionListener(new openSessionListener(this));

        ButtonGroup buttonGroup = new ButtonGroup();

        chrome = new JRadioButton("Chrome");
        opera = new JRadioButton("Opera");
        safari = new JRadioButton("Safari");

        buttonGroup.add(chrome);
        buttonGroup.add(opera);
        buttonGroup.add(safari);

        JPanel radioButtonPanel = new JPanel();

        if (OsInfo.equals("Windows")) {
            radioButtonPanel.add(chrome);
            radioButtonPanel.add(opera);
            chrome.setSelected(true);
        }
        else if (OsInfo.equals("Mac")) {
            radioButtonPanel.add(safari);
            safari.setSelected(true);
        }

        this.add(username);
        this.add(usernameField);
        this.add(password);
        this.add(passwordField);
        this.add(radioButtonPanel);
        this.add(initiateSession);
    }

    public class openSessionListener implements ActionListener {
        LoginInfoPanel loginInfoPanel;

        public openSessionListener(LoginInfoPanel loginInfoPanel) {
            this.loginInfoPanel = loginInfoPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean loginCheck = false;

            if (loginInfoPanel.usernameField.getText().isEmpty() || loginInfoPanel.passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the username and password");
            } else {
                Globals.setUsername(this.loginInfoPanel.usernameField.getText());
                Globals.setPassword(this.loginInfoPanel.passwordField.getText());
                loginCheck = browserInit();
            }

            if (loginCheck) {

                loginInfoPanel.jFrame.setResizable(false);

                loginInfoPanel.jTabbedPane = new JTabbedPane();

                //RegistrationPanel registrationPanel = new RegistrationPanel(loginInfoPanel);
                WaitListPanel waitListPanel = new WaitListPanel(loginInfoPanel);

                //loginInfoPanel.jTabbedPane.add("Course Registration", registrationPanel);
                loginInfoPanel.jTabbedPane.add("WaitList", waitListPanel);

                loginInfoPanel.chrome.setEnabled(false);
                loginInfoPanel.setVisible(false);

                loginInfoPanel.jFrame.add(loginInfoPanel.jTabbedPane, BorderLayout.NORTH);
                loginInfoPanel.jFrame.repaint();
                loginInfoPanel.jFrame.revalidate();
            }
        }
    }

    private boolean browserInit() {

        String path = System.getProperty("user.dir");
        String username = Globals.getUsername();
        String password = Globals.getPassword();
        String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

        if (this.chrome.isSelected()) {
            System.setProperty("webdriver.chrome.driver", path + "\\Wchromedriver.exe");
            driver = new ChromeDriver();
        } else if (this.opera.isSelected()) {
            System.setProperty("webdriver.opera.driver", path + "\\Woperadriver.exe");
            driver = new OperaDriver();
        } else if (this.safari.isSelected()) {
            if (!OsInfo.equals("Mac")) {
                JOptionPane.showMessageDialog(null, "You should not be here");
                System.exit(-1);
            }
            driver = new SafariDriver();
        } else {
            JOptionPane.showMessageDialog(null, "Please select the browser you are using in this computer");
        }

        driver.manage().window().maximize();
      //  driver.manage().window().setPosition(new org.openqa.selenium.Point(-2000, 0));

        try {
            driver.get(url);
        } catch (Exception e) {
            driver.findElement(By.id("enableTls10Button")).click();
        }

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        Select select = new Select(driver.findElement(By.id("language")));
        select.selectByValue("en");
        driver.findElement(By.id("submit")).click();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
            driver.quit();
            return false;
        }

        if (driver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
            JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
            driver.quit();
            return false;
        }

        WebElement frame = driver.findElement(By.cssSelector("[id^='SIS']"));
        driver.switchTo().frame(frame);
        try {
            driver.findElement(By.xpath("//*[@id=\"isc_2H\"]/img")).click();
            Globals.doubleLogin = true;
        } catch (Exception ignored) {}
        return true;
    }
}
