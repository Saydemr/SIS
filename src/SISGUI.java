import java.awt.*;
import javax.swing.*;

public class SISGUI {

    public static void main(String[] args) {

        String OsInfo = System.getProperty("os.name").split(" ")[0];
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("os.name"));


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

        // Thanks to https://github.com/EsadSimitcioglu
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("OneClick");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setSize(new Dimension());
                frame.setMinimumSize(new Dimension(500,750));

                frame.setLayout(new BorderLayout());

                LoginInfoPanel loginInfoPanel = new LoginInfoPanel(frame);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
