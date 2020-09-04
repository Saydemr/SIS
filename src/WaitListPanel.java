
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaitListPanel extends JPanel {

    public WaitListPanel() {
        this.setLayout(new BorderLayout());

        WaitListCoursesPanel waitListCoursesPanel = new WaitListCoursesPanel(this);
        this.add(waitListCoursesPanel, BorderLayout.CENTER);

        JButton startWaitList = new JButton("Start WaitListing Process");
        this.add(startWaitList, BorderLayout.SOUTH);
        startWaitList.addActionListener(new waitListListener());



    }
    public class waitListListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            // TODO Implement selenium browser
        }
    }

}
