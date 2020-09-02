

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SISGUI {
    static LoginInfoPanel loginInfoPanel;
    static JFrame frame;
    static ChromeDriver chromeDriver;
    static FirefoxDriver firefoxDriver;
    static EdgeDriver edgeDriver;

    public static void main(String[] args) {

        frame = new JFrame("OneClick");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 900);
        frame.setMinimumSize(new Dimension(650, 850));
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());

        loginInfoPanel = new LoginInfoPanel();
        frame.add(loginInfoPanel, BorderLayout.NORTH);
        loginInfoPanel.initiateSession.addActionListener(new openSessionListener());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static class openSessionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean loginCheck = false;

            if (loginInfoPanel.usernameField.getText().isEmpty() || loginInfoPanel.passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username and password");
            } else {
                if (loginInfoPanel.edge.isSelected()) {
                    loginCheck = edge();
                } else if (loginInfoPanel.firefox.isSelected()) {
                    loginCheck = firefox();
                } else if (loginInfoPanel.chrome.isSelected()) {
                    loginCheck = chrome();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the browser you are using in this computer");
                }

            }
            if (loginCheck) {
                frame.setVisible(false);
                JTabbedPane jTabbedPane = new JTabbedPane();

                CourseRegistrationPanel courseRegistrationPanel = new CourseRegistrationPanel();
                WaitListPanel waitListPanel = new WaitListPanel();

                jTabbedPane.add("Course Registration", courseRegistrationPanel);
                jTabbedPane.add("WaitList", waitListPanel);

                frame.add(jTabbedPane, BorderLayout.CENTER);
                frame.repaint();
                frame.revalidate();
                frame.setVisible(true);
            } else {

            }
        }

        private boolean chrome() {

            try {
                String path = System.getProperty("user.dir");

                String username = loginInfoPanel.usernameField.getText();
                String password = loginInfoPanel.passwordField.getText();
                String url = "https://sis.ozyegin.edu.tr/OZU_GWT/login.jsp";

                System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
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
                    JOptionPane.showMessageDialog(null, "Invalid login info. Please close the browser and start again.");
                    return false;
                }

            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Please restart the session.");
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

                System.setProperty("webdriver.gecko.driver", path + "\\geckodriver.exe");
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
                    JOptionPane.showMessageDialog(null, "Invalid login info. Please close the browser and start again.");
                    return false;
                }
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Please restart the session.");
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

                System.setProperty("webdriver.edge.driver", path + "\\msedgedriver.exe");
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
                    JOptionPane.showMessageDialog(null, "Invalid login info. Please close the browser and start again.");
                    return false;
                }

            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Please restart the session.");
                return false;
            }
            return true;
        }
    }


}
