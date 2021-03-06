/*
    END OF SERVICE
*/ 

import javax.swing.*;
import java.awt.*;

public class WaitListCoursesPanel extends JPanel {
    WaitListPanel waitListPanel;
    JTextField course1;
    JSpinner frequency1;
    JTextField section1;
    JCheckBox enrolled1;


    public WaitListCoursesPanel(WaitListPanel waitListPanel) {
        this.waitListPanel = waitListPanel;

        this.setLayout(new GridLayout(14, 2, 10, 20));
        this.setBorder(BorderFactory.createTitledBorder("Course Info"));

        JLabel courseID = new JLabel("Course ID");
        JLabel period = new JLabel("Frequency (seconds)");
        JLabel courseSection = new JLabel("Course Section");
        JLabel alreadyEnrolled = new JLabel("Enrolled to another section ?");

        course1 = new JTextField();
        frequency1 = new JSpinner(new SpinnerNumberModel(30, 30, 300, 15));
        frequency1.setFocusable(false);
        frequency1.setIgnoreRepaint(true);
        section1 = new JTextField();
        enrolled1 = new JCheckBox();

        ((JSpinner.DefaultEditor) frequency1.getEditor()).getTextField().setEditable(false);

        this.add(courseID);
        this.add(course1);

        this.add(courseSection);
        this.add(section1);

        this.add(period);
        this.add(frequency1);

        this.add(alreadyEnrolled);
        this.add(enrolled1);

        for (int i = 0; i < 20; i++) {
            JLabel empty = new JLabel();
            empty.setVisible(false);
            this.add(empty);
        }
    }
}
