import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaitListPanel extends JPanel {
    LoginInfoPanel loginInfoPanel;
    WaitListCoursesPanel waitListCoursesPanel;

    public WaitListPanel(LoginInfoPanel loginInfoPanel) {
        this.loginInfoPanel = loginInfoPanel;
        this.setLayout(new BorderLayout());

        waitListCoursesPanel = new WaitListCoursesPanel(this);
        this.add(waitListCoursesPanel, BorderLayout.CENTER);

        JButton startWaitList = new JButton("Start WaitListing Process");
        this.add(startWaitList, BorderLayout.SOUTH);
        startWaitList.addActionListener(new waitListListener(loginInfoPanel, this));

    }

    public class waitListListener implements ActionListener {
        LoginInfoPanel loginInfoPanel;
        WaitListPanel waitListPanel;

        public waitListListener(LoginInfoPanel loginInfoPanel, WaitListPanel waitListPanel) {
            this.loginInfoPanel = loginInfoPanel;
            this.waitListPanel = waitListPanel;
        }

        public void actionPerformed(ActionEvent e) {
            switch (Globals.driver) {
                case "opera":
                    if (loginInfoPanel.operaDriver.toString().contains("null")) {
                        JOptionPane.showMessageDialog(null,"Start again. You should not close the browser.");
                        System.exit(-1);
                    }
                    else {
                        WebElement sgBox = loginInfoPanel.operaDriver.findElementByClassName("gwt-SuggestBox");
                        sgBox.sendKeys("Sections");
                        sgBox.sendKeys(Keys.TAB);

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ignored) {
                        }

                        loginInfoPanel.operaDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                        loginInfoPanel.operaDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                        loginInfoPanel.operaDriver.findElementByName("isc_5Xicon").click();
                    }
                case "chrome":
                    if (loginInfoPanel.chromeDriver.toString().contains("null")) {
                        JOptionPane.showMessageDialog(null,"Start again. You should not close the browser.");
                        System.exit(-1);
                    } else {
                        WebElement sgBoxch = loginInfoPanel.chromeDriver.findElementByClassName("gwt-SuggestBox");
                        sgBoxch.sendKeys("Sections");
                        sgBoxch.sendKeys(Keys.TAB);

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ignored) {
                        }

                        loginInfoPanel.chromeDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                        loginInfoPanel.chromeDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                        loginInfoPanel.chromeDriver.findElementByName("isc_5Xicon").click();
                    }


                case "safari":
                    if (loginInfoPanel.operaDriver.toString().contains("null")) {
                        JOptionPane.showMessageDialog(null,"Start again. You should not close the browser.");
                        System.exit(-1);
                    } else {
                        WebElement sgBoxsf = loginInfoPanel.safariDriver.findElementByClassName("gwt-SuggestBox");
                        sgBoxsf.sendKeys("Sections");
                        sgBoxsf.sendKeys(Keys.TAB);

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ignored) {
                        }

                        loginInfoPanel.safariDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                        loginInfoPanel.safariDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                        loginInfoPanel.safariDriver.findElementByName("isc_5Bicon").click();
                    }
            }
        }
    }
}
