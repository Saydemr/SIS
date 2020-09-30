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

            boolean exists = false;
            String[] nextdate = null;
            String[] nexttime = null;

            WebElement sgBox = loginInfoPanel.driver.findElement(By.className("gwt-SuggestBox"));
            sgBox.sendKeys("Sections");
            sgBox.sendKeys(Keys.TAB);

            WebDriverWait wait = new WebDriverWait(loginInfoPanel.driver, 5,0);
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

            System.out.println(seConfirm);
            System.out.println(suConfirm);
            System.out.println(coConfirm);
            System.out.println(exists);

            ScheduledExecutorService schedulerExec = Executors.newScheduledThreadPool(2);

            if (exists) {
                LocalDate date = LocalDate.of(Integer.parseInt(nextdate[2]),Integer.parseInt(nextdate[1]),Integer.parseInt(nextdate[0]));
                LocalTime time = LocalTime.of(Integer.parseInt(nexttime[2]),Integer.parseInt(nexttime[1]),Integer.parseInt(nexttime[0]));
                LocalDateTime dateTime = LocalDateTime.of(date,time);
                scheduleTask(dateTime,schedulerExec);
            }

            try {
                this.waitListPanel.waitListCoursesPanel.frequency1.commitEdit();
            } catch (ParseException ignored) {}
            int value = (int) this.waitListPanel.waitListCoursesPanel.frequency1.getValue();

            sgBox.sendKeys("Course Reg");
            sgBox.sendKeys(Keys.TAB);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("")));

            schedulerExec.scheduleAtFixedRate(() -> {

            },0,value,TimeUnit.SECONDS);
        }
    }

    public void scheduleTask(LocalDateTime localDateTime,ScheduledExecutorService executorService)  {

        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(),localDateTime);
        if (delay < 30000) {
            executorService.schedule(() -> {

                WebElement sBox = loginInfoPanel.driver.findElement(By.className("gwt-SuggestBox"));
                sBox.sendKeys("Course Reg");
                sBox.sendKeys(Keys.ENTER);

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
            case "AVM" :
                return  "Aviation Management";
            case "FE" :
                return  "Engineering Facultyie";
            case "LAW" :
            case "HUK" :
                return "Law";
            case "CS" :
                return  "Computer Science";
            case  "EE" :
                return  "Electrical and Electronics Engineering";
        }
        return "";
    }
}
