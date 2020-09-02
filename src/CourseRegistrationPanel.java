

import javax.swing.*;
import java.awt.*;


public class CourseRegistrationPanel extends JPanel {

    private AddCoursePanel addCoursePanel;
    private CoursesPanel coursesPanel;


    public CourseRegistrationPanel() {
        this.setLayout(new BorderLayout());

        addCoursePanel = new AddCoursePanel(this);
        coursesPanel = new CoursesPanel(this);

        this.add(addCoursePanel, BorderLayout.CENTER);
        this.add(coursesPanel, BorderLayout.SOUTH);

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
