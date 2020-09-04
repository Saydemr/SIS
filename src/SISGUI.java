// GUI feel code inherited from LamalasGD

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SISGUI {
    static LoginInfoPanel loginInfoPanel;
    static JFrame frame;

    static String OsInfo;

    public static void main(String[] args) {

        OsInfo = System.getProperty("os.name").split(" ")[0];
        System.out.println(OsInfo);

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

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("OneClick");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(650, 900);
                frame.setMinimumSize(new Dimension(650, 875));
                frame.setResizable(false);

                frame.setLayout(new BorderLayout());

                loginInfoPanel = new LoginInfoPanel(frame);


                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }



}
