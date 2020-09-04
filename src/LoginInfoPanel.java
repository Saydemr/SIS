import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.opera.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginInfoPanel extends JPanel {
    JButton initiateSession;
    JRadioButton edge;
    JRadioButton firefox;
    JRadioButton chrome;
    JRadioButton opera;
    JTextField usernameField;
    JPasswordField passwordField;
    JFrame jFrame;
    LoginInfoPanel loginInfoPanel = this;

    ChromeDriver chromeDriver;
    FirefoxDriver firefoxDriver;
    EdgeDriver edgeDriver;
    OperaDriver operaDriver;

    String OsInfo;

    public LoginInfoPanel(JFrame jFrame) {
        this.jFrame = jFrame;
        OsInfo = System.getProperty("os.name").split(" ")[0];

        this.setLayout(new GridLayout(3, 2, 5, 5));
        this.setBorder(BorderFactory.createTitledBorder("SIS Login Info"));
        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");

        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();

        this.passwordField.setEchoChar('*');

        initiateSession = new JButton("OPEN SESSION");
        jFrame.add(this, BorderLayout.NORTH);
        this.initiateSession.addActionListener(new openSessionListener(loginInfoPanel));

        ButtonGroup buttonGroup = new ButtonGroup();

        edge = new JRadioButton("Edge");
        firefox = new JRadioButton("Firefox");
        chrome = new JRadioButton("Chrome");
        opera = new JRadioButton("Opera");

        buttonGroup.add(edge);
        buttonGroup.add(firefox);
        buttonGroup.add(chrome);
        buttonGroup.add(opera);

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.add(edge);
        radioButtonPanel.add(firefox);
        radioButtonPanel.add(chrome);
        radioButtonPanel.add(opera);
        edge.setSelected(true);

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
                if (loginInfoPanel.edge.isSelected()) {
                    loginCheck = loginInfoPanel.edge();
                } else if (loginInfoPanel.firefox.isSelected()) {
                    loginCheck = loginInfoPanel.firefox();
                } else if (loginInfoPanel.chrome.isSelected()) {
                    loginCheck = loginInfoPanel.chrome();
                } else if (loginInfoPanel.opera.isSelected()) {
                    loginCheck = loginInfoPanel.opera();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the browser you are using in this computer");
                }

            }
            if (loginCheck) {
                loginInfoPanel.jFrame.setVisible(false);
                JTabbedPane jTabbedPane = new JTabbedPane();

                CourseRegistrationPanel courseRegistrationPanel = new CourseRegistrationPanel();
                WaitListPanel waitListPanel = new WaitListPanel();

                jTabbedPane.add("Course Registration", courseRegistrationPanel);
                jTabbedPane.add("WaitList", waitListPanel);

                loginInfoPanel.edge.setEnabled(false);
                loginInfoPanel.firefox.setEnabled(false);
                loginInfoPanel.chrome.setEnabled(false);
                loginInfoPanel.setVisible(false);

                loginInfoPanel.jFrame.add(jTabbedPane, BorderLayout.NORTH);
                loginInfoPanel.jFrame.repaint();
                loginInfoPanel.jFrame.revalidate();
                loginInfoPanel.jFrame.setVisible(true);
            }
            else {
                System.out.println("WTH. How the world you even jumped here ?");
                System.exit(-1);
            }
        }
    }

    private boolean chrome() {

        try {
            String path = System.getProperty("user.dir");

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.chrome.driver", path + "\\Wchromedriver.exe");
            } else if (OsInfo.equals("Mac")){

            }

            chromeDriver = new ChromeDriver();

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

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            chromeDriver.quit();
            return false;
        }
        return true;
    }
    private boolean firefox() {
        try {
            String path = System.getProperty("user.dir");

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.gecko.driver", path + "\\Wgeckodriver.exe");
            } else if (OsInfo.equals("Mac")){

            }

            firefoxDriver = new FirefoxDriver();

            try {
                firefoxDriver.get(url);
            } catch (Exception e) {
                firefoxDriver.findElementById("enableTls10Button").click();
            }

            firefoxDriver.findElementById("username").sendKeys(username);
            firefoxDriver.findElementById("password").sendKeys(password);
            Select select = new Select(firefoxDriver.findElementById("language"));
            select.selectByValue("en");
            firefoxDriver.findElementById("submit").click();

            Thread.sleep(1500);

            if (firefoxDriver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
                JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
                firefoxDriver.quit();
                return false;
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            firefoxDriver.quit();
            return false;
        }
        return true;
    }
    private boolean edge() {

        try {
            String path = System.getProperty("user.dir");
            System.out.println(path);
            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.edge.driver", path + "\\Wmsedgedriver.exe");
            } else if (OsInfo.equals("Mac")){

            }

            edgeDriver = new EdgeDriver();

            try {
                edgeDriver.get(url);
            } catch (Exception e) {
                edgeDriver.findElementById("enableTls10Button").click();
            }

            edgeDriver.findElementById("username").sendKeys(username);
            edgeDriver.findElementById("password").sendKeys(password);
            Select select = new Select(edgeDriver.findElementById("language"));
            select.selectByValue("en");
            edgeDriver.findElementById("submit").click();

            Thread.sleep(1500);
            if (edgeDriver.getCurrentUrl().equals("https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp")) {
                JOptionPane.showMessageDialog(null, "Invalid login info. Please start again.");
                edgeDriver.quit();
                return false;
            }

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            edgeDriver.quit();
            return false;
        }
        return true;
    }
    private boolean opera() {
        try {
            String path = System.getProperty("user.dir");

            String username = loginInfoPanel.usernameField.getText();
            String password = loginInfoPanel.passwordField.getText();
            String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

            if (OsInfo.equals("Windows")) {
                System.setProperty("webdriver.opera.driver", path + "\\Woperadriver.exe");
            } else if (OsInfo.equals("Mac")){

            }

            operaDriver = new OperaDriver();

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
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Please restart the session.");
            operaDriver.quit();
            return false;
        }
        return true;
    }

}
