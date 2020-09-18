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

                case "chrome":


                case "safari":

            }
        }
    }
}
