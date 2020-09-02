import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCoursePanel extends JPanel {
    CourseRegistrationPanel courseRegistrationPanel;
    JTextField idField;
    JTextField sectionField;

    public AddCoursePanel(CourseRegistrationPanel courseRegistrationPanel) {
        this.courseRegistrationPanel = courseRegistrationPanel;
        this.setBorder(BorderFactory.createTitledBorder("Add Courses"));

        this.setLayout(new GridLayout(5, 2));

        JLabel courseID = new JLabel("Course ID");
        JLabel section = new JLabel("Section");
        JLabel faculty = new JLabel("Faculty");

        faculty.setVisible(false);

        idField = new JTextField();
        sectionField = new JTextField();
        JLabel facultyLabel = new JLabel();

        JLabel empty = new JLabel();
        empty.setVisible(false);

        JPanel addDelete = new JPanel();

        JButton addCourse = new JButton("      Add Course     ");
        addCourse.addActionListener(new addCourseListener());

        JButton deleteCourse = new JButton("Delete Last Course");
        deleteCourse.addActionListener(new deleteCourseListener());

        addDelete.add(addCourse);
        addDelete.add(deleteCourse);

        this.add(courseID);
        this.add(idField);
        this.add(section);
        this.add(sectionField);
        this.add(faculty);
        this.add(facultyLabel);
        this.add(empty);
        this.add(addDelete);
    }

    public class addCourseListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            courseRegistrationPanel.addCourseToList(idField.getText(), sectionField.getText());
        }
    }

    public class deleteCourseListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            courseRegistrationPanel.deleteLastRowFromList();

        }
    }
}

