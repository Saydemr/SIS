
import javax.swing.*;
import java.awt.*;

public class WaitListPanel extends JPanel {

    public WaitListPanel() {
        this.setLayout(new BorderLayout());

        WaitListCoursesPanel waitListCoursesPanel = new WaitListCoursesPanel(this);
        this.add(waitListCoursesPanel, BorderLayout.CENTER);

        JButton startWaitList = new JButton("Start WaitListing Process");
        this.add(startWaitList, BorderLayout.SOUTH);
        startWaitList.setBackground(new java.awt.Color(117, 94, 168, 255));

    }

}
