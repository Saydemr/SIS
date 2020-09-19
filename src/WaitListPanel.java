import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            System.out.println(Globals.driver);
            switch (Globals.driver) {
                case "opera":
                    WebElement sgBox = loginInfoPanel.operaDriver.findElementByClassName("gwt-SuggestBox");
                    sgBox.sendKeys("Sections");
                    sgBox.sendKeys(Keys.TAB);

                    WebDriverWait wait = new WebDriverWait(loginInfoPanel.operaDriver, 1,500);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("SUBJECT")));

                    loginInfoPanel.operaDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                    loginInfoPanel.operaDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                    loginInfoPanel.operaDriver.findElementById("isc_5X").click();

                    break;

                case "chrome":

                    WebElement sgBoxch = loginInfoPanel.chromeDriver.findElementByClassName("gwt-SuggestBox");
                    sgBoxch.sendKeys("Sections");
                    sgBoxch.sendKeys(Keys.TAB);

                    WebDriverWait waitch = new WebDriverWait(loginInfoPanel.chromeDriver, 1,500);
                    waitch.until(ExpectedConditions.visibilityOfElementLocated(By.name("SUBJECT")));

                    loginInfoPanel.chromeDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                    loginInfoPanel.chromeDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                    loginInfoPanel.chromeDriver.findElementById("isc_5X").click();

                    break;

                case "safari":

                    WebElement sgBoxsf = loginInfoPanel.safariDriver.findElementByClassName("gwt-SuggestBox");
                    sgBoxsf.sendKeys("Sections");
                    sgBoxsf.sendKeys(Keys.TAB);

                    WebDriverWait waitsf = new WebDriverWait(loginInfoPanel.safariDriver, 1,500);
                    waitsf.until(ExpectedConditions.visibilityOfElementLocated(By.name("SUBJECT")));

                    loginInfoPanel.safariDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                    loginInfoPanel.safariDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                    loginInfoPanel.safariDriver.findElementById("isc_5X").click();

                    break;
            }
        }
    }
}
