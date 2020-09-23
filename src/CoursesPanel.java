import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoursesPanel extends JPanel {
    JTable table;
    DefaultTableModel tableModel;
    JButton continueButton;
    CourseRegistrationPanel courseRegistrationPanel;

    public CoursesPanel(CourseRegistrationPanel courseRegistrationPanel) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Courses to be registered"));
        this.courseRegistrationPanel = courseRegistrationPanel;

        Object[] headers = new Object[]{"Course ID", "Section", "Subject"};
        this.tableModel = new DefaultTableModel(headers, 0);
        this.table = new JTable(this.tableModel);
        table.setEnabled(false);
        JScrollPane jScrollPane = new JScrollPane(table);
        this.add(jScrollPane, BorderLayout.NORTH);

        continueButton = new JButton("Start Registration Process");
        continueButton.setEnabled(false);
        continueButton.addActionListener(new registrationListener(this));
        this.add(continueButton, BorderLayout.SOUTH);

    }

    public void addCourses(String id, String section) {

        String subject = id;
        subject = subject.replaceAll("[^A-Za-z]+", "");
        Object[] courseInfo = new Object[]{id, section, subject};
        tableModel.addRow(courseInfo);

        if (tableModel.getRowCount() != 0) {
            this.continueButton.setEnabled(true);
        }
    }

    public void deleteCourses() {
        tableModel.removeRow(tableModel.getRowCount() - 1);
        if (tableModel.getRowCount() == 0) {
            this.continueButton.setEnabled(false);
        }
        this.revalidate();
        this.repaint();
    }

    public static class registrationListener implements ActionListener {
        CoursesPanel coursesPanel;

        public registrationListener(CoursesPanel coursesPanel) {
            this.coursesPanel = coursesPanel;
        }

        public void actionPerformed(ActionEvent e) {
            this.coursesPanel.courseRegistrationPanel.loginInfoPanel.jTabbedPane.setEnabled(false);

            // TODO Implement selenium browser
        }
    }
}
