import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
    JButton startWaitList;

    public WaitListPanel(LoginInfoPanel loginInfoPanel) {
        this.loginInfoPanel = loginInfoPanel;
        this.setLayout(new BorderLayout());

        waitListCoursesPanel = new WaitListCoursesPanel(this);
        this.add(waitListCoursesPanel, BorderLayout.CENTER);

        startWaitList = new JButton("Start WaitListing Process");
        this.add(startWaitList, BorderLayout.SOUTH);
        startWaitList.addActionListener(new waitListListener(loginInfoPanel, this));

    }

    public class waitListListener implements ActionListener {
        LoginInfoPanel loginInfoPanel;
        WaitListPanel waitListPanel;
        String subject;
        String courseNo;
        String sectionNo;
        boolean suConfirm;
        boolean coConfirm;
        boolean seConfirm;

        public waitListListener(LoginInfoPanel loginInfoPanel, WaitListPanel waitListPanel) {
            this.loginInfoPanel = loginInfoPanel;
            this.waitListPanel = waitListPanel;
        }

        public void actionPerformed(ActionEvent e) {
            this.subject = this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^A-Za-z]+", "");
            this.courseNo = this.waitListPanel.waitListCoursesPanel.course1.getText().replaceAll("[^0-9]+", "");
            this.sectionNo = this.waitListPanel.waitListCoursesPanel.section1.getText().replaceAll("[^A-Za-z]+", "");

            loginInfoPanel.jTabbedPane.setEnabled(false);
            waitListPanel.startWaitList.setEnabled(false);

            boolean exists = false;
            String[] nextdate;
            String[] nexttime;

            WebElement sgBox = loginInfoPanel.driver.findElement(By.className("gwt-SuggestBox"));
            sgBox.sendKeys("Sections");
            sgBox.sendKeys(Keys.TAB);

            WebDriverWait wait = new WebDriverWait(loginInfoPanel.driver, 4,750);

            if (Globals.doubleLogin) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_5X")));
            } else {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_5A")));
            }

            loginInfoPanel.driver.findElement(By.name("SUBJECT")).sendKeys(subject);
            loginInfoPanel.driver.findElement(By.name("COURSENO")).sendKeys(courseNo);
            loginInfoPanel.driver.findElement(By.name("SECTIONNO")).sendKeys(sectionNo);

            if (Globals.doubleLogin) {
                loginInfoPanel.driver.findElement(By.id("isc_5X")).click();
                nextdate = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_65\"]/h3/center/font[1]/strong")).getText().split("/");
                nexttime = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_65\"]/h3/center/font[2]/strong")).getText().split(":");

                try {
                    suConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ytable\"]/tbody/tr/td[1]/div/nobr")).getText().equals(this.subject);
                    coConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ytable\"]/tbody/tr/td[2]/div/nobr")).getText().equals(this.courseNo);
                    seConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ytable\"]/tbody/tr/td[3]/div/nobr")).getText().equals(this.sectionNo);
                    exists = suConfirm && coConfirm && seConfirm;
                } catch (Exception ignored) {
                }
            }
            else {
                loginInfoPanel.driver.findElement(By.id("isc_5A")).click();
                nextdate = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5J\"]/h3/center/font[1]/strong")).getText().split("/");
                nexttime = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5J\"]/h3/center/font[2]/strong")).getText().split(":");

                try {
                    suConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ctable\"]/tbody/tr/td[1]/div/nobr")).getText().equals(this.subject);
                    coConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ctable\"]/tbody/tr/td[2]/div/nobr")).getText().equals(this.courseNo);
                    seConfirm = loginInfoPanel.driver.findElement(By.xpath("//*[@id=\"isc_5Ctable\"]/tbody/tr/td[3]/div/nobr")).getText().equals(this.sectionNo);
                    exists = suConfirm && coConfirm && seConfirm;
                } catch (Exception exception) {
                    exists = false;
                }
            }

            LocalDateTime dateTime;

            ScheduledExecutorService schedulerExec = Executors.newScheduledThreadPool(3);
            
            loginInfoPanel.driver.findElement(By.id("isc_46")).click();

            WebElement sBox = loginInfoPanel.driver.findElement(By.className("gwt-SuggestBox"));
            sBox.sendKeys("Course Reg");
            sBox.sendKeys(Keys.TAB);

            if (exists) {
                LocalDate date = LocalDate.of(Integer.parseInt(nextdate[2]), Integer.parseInt(nextdate[1]), Integer.parseInt(nextdate[0]));
                LocalTime time = LocalTime.of(Integer.parseInt(nexttime[2]), Integer.parseInt(nexttime[1]), Integer.parseInt(nexttime[0]));
                dateTime = LocalDateTime.of(date, time);
                scheduleTask(dateTime, schedulerExec);
            } else {
                try {
                    waitListCoursesPanel.frequency1.commitEdit();
                } catch (ParseException ignored) {}
                int value = (int) waitListCoursesPanel.frequency1.getValue();

                String courseNameConverted = subjectConverter(subject);

                if (Globals.doubleLogin) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_7I")));
                    loginInfoPanel.driver.findElement(By.id("isc_7I")).sendKeys(subject);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='"+ courseNameConverted + "']")));
                    loginInfoPanel.driver.findElement(By.xpath("//*[text()='"+ courseNameConverted + "']")).click();
                    WebElement courseNoField = loginInfoPanel.driver.findElement(By.name("COURSENO"));
                    courseNoField.click();
                    courseNoField.sendKeys(courseNo);

                } else {

                }

//                List<WebElement> elementList = loginInfoPanel.driver.findElements(By.tagName("SUBJECT"));
//                elementList.get(0).sendKeys(subject);

                schedulerExec.scheduleAtFixedRate(() -> {

                },0,value,TimeUnit.SECONDS);
            }

        }
    }

    public void scheduleTask(LocalDateTime localDateTime,ScheduledExecutorService executorService)  {

        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(),localDateTime);
        if (delay < 30000) {
            executorService.schedule(() -> {

                //TODO passing the course and checking the register

                loginInfoPanel.driver.findElement(By.id("isc_3J")).click();

            },delay, TimeUnit.MILLISECONDS);
        }
        else {

            try {
                Thread.sleep(delay/2);
            } catch (InterruptedException ignored) {
            }

            scheduleTask(localDateTime,executorService);
        }
    }
    public String subjectConverter(String sub) {
        String subj = sub.toUpperCase();
        switch (subj) {
            case "ACCT" :
                return "Accounting";
            case "ARB" :
                return "Arabic";
            case "ARCH" :
                return "Architecture";
            case "ART" :
                return "Art";
            case "AVM" :
                return  "Aviation Management";
            case "AVT" :
                return "Aviation";
            case "BUS" :
                return "Business Administration";
            case "CE" :
                return "Civil Engineering";
            case "CHEM" :
                return "Chemistry";
            case "CHN" :
                return "Chinese";
            case "FE" :
                return  "Engineering Facultyie";
            case "LAW" :
            case "HUK" :
                return "Law";
            case "CS" :
                return  "Computer Science";
            case  "EE" :
                return  "Electrical and Electronics Engineering";
            case "ME" :
                return "Mechanical Engineering";
        }
        return "";
    }
}
