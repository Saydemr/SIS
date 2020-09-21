import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            boolean exists = false;
            String[] nextdate = null;
            String[] nexttime = null;
            switch (Globals.driver) {
                case "opera":
                    WebElement sgBox = loginInfoPanel.operaDriver.findElementByClassName("gwt-SuggestBox");
                    sgBox.sendKeys("Sections");
                    sgBox.sendKeys(Keys.TAB);

                    WebDriverWait wait = new WebDriverWait(loginInfoPanel.operaDriver, 2,500);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_5X")));

                    loginInfoPanel.operaDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                    loginInfoPanel.operaDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                    loginInfoPanel.operaDriver.findElementById("isc_5X").click();

                    nextdate = loginInfoPanel.operaDriver.findElementByCssSelector("#isc_65 > h3 > center > font:nth-child(1) > strong").getText().split("/");
                    nexttime = loginInfoPanel.operaDriver.findElementByCssSelector("#isc_65 > h3 > center > font:nth-child(2) > strong").getText().split(":");


                    break;

                case "chrome":

                    WebElement sgBoxch = loginInfoPanel.chromeDriver.findElementByClassName("gwt-SuggestBox");
                    sgBoxch.sendKeys("Sections");
                    sgBoxch.sendKeys(Keys.TAB);

                    WebDriverWait waitch = new WebDriverWait(loginInfoPanel.chromeDriver, 3,500);
                    waitch.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_5X")));

                    loginInfoPanel.chromeDriver.findElementByName("SUBJECT").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", ""));
                    loginInfoPanel.chromeDriver.findElementByName("COURSENO").sendKeys(this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]", ""));
                    loginInfoPanel.chromeDriver.findElementById("isc_5X").click();

                    nextdate = loginInfoPanel.chromeDriver.findElementByCssSelector("#isc_FW > h3:nth-child(1) > center:nth-child(1) > font:nth-child(1)").toString().split("/");
                    nexttime = loginInfoPanel.chromeDriver.findElementByCssSelector("#isc_FW > h3:nth-child(1) > center:nth-child(1) > font:nth-child(2)").toString().split(":");

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

                    nextdate = loginInfoPanel.safariDriver.findElementByCssSelector("#isc_FW > h3:nth-child(1) > center:nth-child(1) > font:nth-child(1)").toString().split("/");
                    nexttime = loginInfoPanel.safariDriver.findElementByCssSelector("#isc_FW > h3:nth-child(1) > center:nth-child(1) > font:nth-child(2)").toString().split(":");

                    break;
            }

            if (exists) {
                LocalDate date = LocalDate.of(Integer.parseInt(nextdate[2]),Integer.parseInt(nextdate[1]),Integer.parseInt(nextdate[0]));
                LocalTime time = LocalTime.of(Integer.parseInt(nexttime[2]),Integer.parseInt(nexttime[1]),Integer.parseInt(nexttime[0]));
                LocalDateTime dateTime = LocalDateTime.of(date,time);
                scheduleTask(dateTime);
            }
            ScheduledExecutorService schedulerExec = Executors.newScheduledThreadPool(1);

            try {
                this.waitListPanel.waitListCoursesPanel.frequency1.commitEdit();
            } catch ( java.text.ParseException ignored) {}
            int value = (Integer) this.waitListPanel.waitListCoursesPanel.frequency1.getValue();

            switch (Globals.driver) {
                case "opera":
                    WebElement sgBox = loginInfoPanel.operaDriver.findElementByClassName("gwt-SuggestBox");
                    sgBox.sendKeys("Course Reg");
                    sgBox.sendKeys(Keys.TAB);

                    WebDriverWait wait = new WebDriverWait(loginInfoPanel.operaDriver, 2,500);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("")));

                    schedulerExec.scheduleAtFixedRate(() -> {

                    },0,value,TimeUnit.MINUTES);

                    break;

                case "chrome":

                    WebElement sgBoxch = loginInfoPanel.chromeDriver.findElementByClassName("gwt-SuggestBox");
                    sgBoxch.sendKeys("Course Reg");
                    sgBoxch.sendKeys(Keys.TAB);

                    WebDriverWait waitch = new WebDriverWait(loginInfoPanel.chromeDriver, 3,500);
                    waitch.until(ExpectedConditions.visibilityOfElementLocated(By.id("")));

                    schedulerExec.scheduleAtFixedRate(() -> {

                    },0,value,TimeUnit.MINUTES);


                    break;

                case "safari":

                    WebElement sgBoxsf = loginInfoPanel.safariDriver.findElementByClassName("gwt-SuggestBox");
                    sgBoxsf.sendKeys("Course Reg");
                    sgBoxsf.sendKeys(Keys.TAB);

                    WebDriverWait waitsf = new WebDriverWait(loginInfoPanel.safariDriver, 1,500);
                    waitsf.until(ExpectedConditions.visibilityOfElementLocated(By.name("")));

                    schedulerExec.scheduleAtFixedRate(() -> {

                    },0,value,TimeUnit.MINUTES);

                    break;
            }
        }
    }

    public void scheduleTask(LocalDateTime localDateTime)  {

        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(),localDateTime);
        if (delay < 30000) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {

                switch (Globals.driver) {

                    case "opera" :

                        WebElement sBox = loginInfoPanel.operaDriver.findElementByClassName("gwt-SuggestBox");
                        sBox.sendKeys("Course Reg");
                        sBox.sendKeys(Keys.ENTER);

                        //TODO passing the course and checking the register

                        loginInfoPanel.operaDriver.findElementById("isc_3J").click();

                        break;

                    case "chrome" :

                        WebElement sBoxch = loginInfoPanel.chromeDriver.findElementByClassName("gwt-SuggestBox");
                        sBoxch.sendKeys("Course Reg");
                        sBoxch.sendKeys(Keys.ENTER);

                        //TODO passing the course and checking the register

                        loginInfoPanel.chromeDriver.findElementById("isc_3J").click();

                        break;

                    case "safari" :

                        WebElement sBoxsf = loginInfoPanel.safariDriver.findElementByClassName("gwt-SuggestBox");
                        sBoxsf.sendKeys("Course Reg");
                        sBoxsf.sendKeys(Keys.ENTER);

                        //TODO passing the course and checking the register

                        loginInfoPanel.safariDriver.findElementById("isc_3J").click();

                        break;
                }

            },delay, TimeUnit.MILLISECONDS);
        }
        else {

            try {
                Thread.sleep(delay/2);
            } catch (InterruptedException ignored) {
            }

            scheduleTask(localDateTime);

        }
    }
}
