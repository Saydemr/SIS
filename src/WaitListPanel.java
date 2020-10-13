import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.Duration;
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

            WebDriverWait wait = new WebDriverWait(loginInfoPanel.driver, 5,750);

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
            if (Globals.doubleLogin) {
                loginInfoPanel.driver.findElement(By.id("isc_46")).click();
            } else {
                loginInfoPanel.driver.findElement(By.id("isc_3J")).click();
            }

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
                    System.out.print("DL");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_7J")));
                    loginInfoPanel.driver.findElement(By.id("isc_7E")).sendKeys(subject);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='"+ courseNameConverted + "']")));
                    loginInfoPanel.driver.findElement(By.xpath("//*[text()='"+ courseNameConverted + "']")).click();
                    WebElement courseNoField = loginInfoPanel.driver.findElement(By.id("isc_7M"));
                    courseNoField.click();
                    courseNoField.sendKeys(courseNo);

                    loginInfoPanel.driver.findElement(By.id("isc_84")).click();

                } else {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_8A")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("isc_8B")));
                    loginInfoPanel.driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
                    loginInfoPanel.driver.findElement(By.id("isc_6S")).sendKeys(subject);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='"+ courseNameConverted + "']")));
                    loginInfoPanel.driver.findElement(By.xpath("//*[text()='"+ courseNameConverted + "']")).click();
                    WebElement courseNoField = loginInfoPanel.driver.findElement(By.id("isc_70"));
                    courseNoField.click();
                    courseNoField.sendKeys(courseNo);

                    loginInfoPanel.driver.findElement(By.id("isc_7I")).click();

                    WebElement selectAncestor = loginInfoPanel.driver.findElement(By.xpath("//nobr[text()='"+ subject.toUpperCase() + " " + courseNo + "'] and //[contains(text(),'" + sectionNo.toUpperCase() + "')]"));
                    System.out.println(selectAncestor.getText());
                    wait.withTimeout(Duration.ofSeconds(1));

                    JavascriptExecutor jse = (JavascriptExecutor)loginInfoPanel.driver;
                    jse.executeScript("arguments[0].click()", selectAncestor);

                    System.out.print("NDL");

                }



             //   document.querySelector("#isc_9Stable > tbody > tr:nth-child(1) > td:nth-child(2) > div > nobr > a")

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
    public String getElementXPath(WebDriver driver, WebElement element) {
        return (String)((JavascriptExecutor)driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();", element);
    }
    public String subjectConverter(String sub) {
        String subj = sub.toUpperCase();
        switch (subj) {
            case "ACCT" :
                return "Accounting";
            case "ARB" :
                return "Arabic";
            case "ARCH" :
            case "MİM" :
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
            case "CINE" :
                return "Cinema";
            case "COD" :
                return "Communication Design";
            case "CS" :
                return  "Computer Science";
            case "DIS" :
                return "Design";
            case "DS" :
                return "Data Science";
            case "ECON" :
                return "Economy";
            case  "EE" :
                return  "Electrical and Electronics Engineering";
            case "ENG" :
                return "English";
            case "ENTR" :
                return "Entrepreneurship";
            case "FERM" :
                return "Financial Engineering and Risk Management";
            case "FIN" :
                return "Finance";
            case "FRE" :
                return "French";
            case "GARM" :
                return "Gastronomy and Restaurant Management";
            case "GER" :
                return "German";
            case "GSE" :
                return "GSSE";
            case "HIST" :
                return "History";
            case "HMAN" :
                return "Hotel Management";
            case "HUM" :
                return "Humanities";
            case "IBUS" :
                return "International Business";
            case "IDE" :
                return "Industrial Design";
            case "IE" :
                return "Industrial Engineering";
            case "INAR" :
                return "Interior Architecture and Environmental Design";
            case "IR" :
                return "International Relations";
            case "ITA" :
                return "Italian";
            case "KMI" :
                return "City and Architecture";
            case "MATH" :
                return "Mathematics";
            case "ME" :
                return "Mechanical Engineering";
            case "MGMT" :
                return "Management";
            case "MIS" :
                return "Management Information System";
            case "MKTG" :
                return "Marketing";
            case "MUSIC" :
                return "Music";
            case "OPER" :
                return "Operations Management";
            case "PE" :
                return "Physical Education";
            case "PHYS" :
                return "Physics";
            case "PLT" :
                return "Pilot Training";
           // case "PSY" :
          //      return "Psychology";
            case "RUS" :
                return "Russian";
            case "SAS" :
                return "School of Applied Sciences";
            case "SEC" :
                return "Sectoral Solutions";
            case "SOC" :
                return "Sociology";
            case "SPA" :
                return "Spanish";
            case "TLL" :
                return "Turkish Language and Literature";
            case "TURK" :
                return "Turkish";
            case "FE" :
                return  "Engineering Faculty";
            case "LAW" :
            case "HUK" :
                return "Law";
        }
        return "";
    }
}
