import javax.swing.*;
import java.awt.*;

public class WaitListCoursesPanel extends JPanel {
    WaitListPanel waitListPanel;

    public WaitListCoursesPanel(WaitListPanel waitListPanel) {
        this.waitListPanel = waitListPanel;

        this.setLayout(new GridLayout(12, 3, 10, 20));
        this.setBorder(BorderFactory.createTitledBorder("Course Info"));

        JLabel courseID = new JLabel("Course ID");
        JLabel period = new JLabel("Frequency of Checks (minutes)");
        JLabel alreadyEnrolled = new JLabel("Enrolled to another section ?");

        JTextField course1 = new JTextField();
        JSpinner frequency1 = new JSpinner(new SpinnerNumberModel(30, 5, 60, 2.5));
        frequency1.setFocusable(false);
        frequency1.setIgnoreRepaint(true);
        JCheckBox enrolled1 = new JCheckBox();

        JTextField course2 = new JTextField();
        JSpinner frequency2 = new JSpinner(new SpinnerNumberModel(30, 2.5, 60, 0.5));
        JCheckBox enrolled2 = new JCheckBox();

        course2.setVisible(false);
        frequency2.setVisible(false);
        enrolled2.setVisible(false);

        ((JSpinner.DefaultEditor) frequency1.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) frequency2.getEditor()).getTextField().setEditable(false);

        this.add(courseID);
        this.add(period);
        this.add(alreadyEnrolled);

        this.add(course1);
        this.add(frequency1);
        this.add(enrolled1);

        this.add(course2);
        this.add(frequency2);
        this.add(enrolled2);

        for (int i = 0; i < 27; i++) {
            JLabel empty = new JLabel();
            empty.setVisible(false);
            this.add(empty);
        }
    }
}
