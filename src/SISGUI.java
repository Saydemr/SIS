/*
    END OF SERVICE
*/ 
import javax.swing.*;
import java.awt.*;

public class SISGUI {

    public static void main(String[] args) {

        String OsInfo = System.getProperty("os.name").split(" ")[0];

        if (OsInfo.equals("Linux")) {
            JOptionPane.showMessageDialog(null, "Linux is not supported");
            System.exit(-1);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Special thanks to https://github.com/EsadSimitcioglu
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("OneClick");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(500,740));

            frame.setLayout(new BorderLayout());

            LoginInfoPanel loginInfoPanel = new LoginInfoPanel(frame);
            frame.add(loginInfoPanel, BorderLayout.NORTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
