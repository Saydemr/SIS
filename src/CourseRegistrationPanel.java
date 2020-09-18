import javax.swing.*;
import java.awt.*;

public class CourseRegistrationPanel extends JPanel {

    private final AddCoursePanel addCoursePanel;
    private final CoursesPanel coursesPanel;
    LoginInfoPanel loginInfoPanel;

    public CourseRegistrationPanel(LoginInfoPanel loginInfoPanel) {
        this.loginInfoPanel = loginInfoPanel;

        this.setLayout(new BorderLayout());

        addCoursePanel = new AddCoursePanel(this);
        coursesPanel = new CoursesPanel(this);

        this.add(addCoursePanel, BorderLayout.NORTH);
        this.add(coursesPanel, BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
    }

    public void addCourseToList(String id, String section) {
        this.coursesPanel.addCourses(id, section);
    }

    public void deleteLastRowFromList() {
        this.coursesPanel.deleteCourses();
    }
}
