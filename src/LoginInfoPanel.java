import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginInfoPanel extends JPanel {
    JButton initiateSession;
    JRadioButton safari;
    JRadioButton chrome;
    JRadioButton opera;
    JTextField usernameField;
    JPasswordField passwordField;
    JFrame jFrame;
    LoginInfoPanel loginInfoPanel = this;

    ChromeDriver chromeDriver;
    OperaDriver operaDriver;
    SafariDriver safariDriver;

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

        this.initiateSession.addActionListener(new openSessionListener(loginInfoPanel));

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

    public static class openSessionListener implements ActionListener {
        LoginInfoPanel loginInfoPanel;

        public openSessionListener(LoginInfoPanel loginInfoPanel) {
            this.loginInfoPanel = loginInfoPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean loginCheck = false;

            if (loginInfoPanel.usernameField.getText().isEmpty() || loginInfoPanel.passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username and password");
            } else {
                Globals.setUsername(this.loginInfoPanel.usernameField.getText());
                Globals.setPassword(this.loginInfoPanel.passwordField.getText());
                if (loginInfoPanel.chrome.isSelected()) {
                    loginCheck = loginInfoPanel.chrome();
                } else if (loginInfoPanel.opera.isSelected()) {
                    loginCheck = loginInfoPanel.opera();
                } else if (loginInfoPanel.safari.isSelected()) {
                    loginCheck = loginInfoPanel.safari();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the browser you are using in this computer");
                }
            }

            if (loginCheck) {
                loginInfoPanel.jFrame.setSize(new Dimension(600,775));
                loginInfoPanel.jFrame.setResizable(false);

                JTabbedPane jTabbedPane = new JTabbedPane();

                CourseRegistrationPanel courseRegistrationPanel = new CourseRegistrationPanel(loginInfoPanel);
                WaitListPanel waitListPanel = new WaitListPanel(loginInfoPanel);

                jTabbedPane.add("Course Registration", courseRegistrationPanel);
                jTabbedPane.add("WaitList", waitListPanel);

                loginInfoPanel.chrome.setEnabled(false);
                loginInfoPanel.setVisible(false);

                loginInfoPanel.jFrame.add(jTabbedPane, BorderLayout.NORTH);
                loginInfoPanel.jFrame.repaint();
                loginInfoPanel.jFrame.revalidate();
            }
        }
    }

    private boolean chrome() {

        try {
            Globals.driver = "chrome";
            String path = System.getProperty("user.dir");

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.chrome.driver", path + "\\Wchromedriver.exe");
            } else if (OsInfo.equals("Mac")){
                Runtime.getRuntime().exec("chmod +x " + path + "/chromedriver");
                System.setProperty("webdriver.chrome.driver", path + "/chromedriver");
            }

            chromeDriver = new ChromeDriver();
            chromeDriver.manage().window().maximize();

            try {
                chromeDriver.get(url);
            } catch (Exception e) {
                chromeDriver.findElementById("enableTls10Button").click();
            }

            chromeDriver.findElementById("username").sendKeys(username);
            chromeDriver.findElementById("password").sendKeys(password);
            Select select = new Select(chromeDriver.findElementById("language"));
            select.selectByValue("en");
            chromeDriver.findElementById("submit").click();

            Thread.sleep(1500);

            if (chromeDriver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
                JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
                chromeDriver.quit();
                return false;
            }
            WebElement frame = chromeDriver.findElementByCssSelector("[id^='SIS']");
            chromeDriver.switchTo().frame(frame);
            try {
                chromeDriver.findElementByXPath("//*[@id=\"isc_2H\"]/img").click();
            }
            catch (Exception ignored) {
            }

        } catch (InterruptedException | IOException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            chromeDriver.quit();
            return false;
        }
        return true;
    }
    private boolean opera() {
        try {
            Globals.driver = "opera";
            String path = System.getProperty("user.dir");

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.opera.driver", path + "\\Woperadriver.exe");
            }

            operaDriver = new OperaDriver();
            operaDriver.manage().window().maximize();

            try {
                operaDriver.get(url);
            } catch (Exception e) {
                operaDriver.findElementById("enableTls10Button").click();
            }

            operaDriver.findElementById("username").sendKeys(username);
            operaDriver.findElementById("password").sendKeys(password);
            Select select = new Select(operaDriver.findElementById("language"));
            select.selectByValue("en");
            operaDriver.findElementById("submit").click();

            Thread.sleep(1500);

            if (operaDriver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
                JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
                operaDriver.quit();
                return false;
            }

            WebElement frame = operaDriver.findElementByCssSelector("[id^='SIS']");
            operaDriver.switchTo().frame(frame);

            try {

                operaDriver.findElementByXPath("//*[@id=\"isc_2H\"]/img").click();
            }
            catch (Exception ignored) {
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            operaDriver.quit();
            return false;
        }
        return true;
    }
    private boolean safari() {
        try {
            Globals.driver = "safari";

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (!OsInfo.equals("Mac")) {
               JOptionPane.showMessageDialog(null,"You should not be here");
               System.exit(-1);
            }

            safariDriver = new SafariDriver();
            safariDriver.manage().window().maximize();

            try {
                safariDriver.get(url);
            } catch (Exception e) {
                safariDriver.findElementById("enableTls10Button").click();
            }

            safariDriver.findElementById("username").sendKeys(username);
            safariDriver.findElementById("password").sendKeys(password);
            Select select = new Select(safariDriver.findElementById("language"));
            select.selectByValue("en");
            safariDriver.findElementById("submit").click();

            Thread.sleep(1500);

            if (safariDriver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
                JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
                safariDriver.quit();
                return false;
            }

            WebElement frame = safariDriver.findElementByCssSelector("[id^='SIS']");
            safariDriver.switchTo().frame(frame);

            try {
                safariDriver.findElementByXPath("//*[@id=\"isc_2H\"]/img").click();
            }
            catch (Exception ignored) {
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            safariDriver.quit();
            return false;
        }
        return true;
    }
}
