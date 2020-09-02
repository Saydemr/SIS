
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginInfoPanel extends JPanel {
    JButton initiateSession;
    JRadioButton edge;
    JRadioButton firefox;
    JRadioButton chrome;
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginInfoPanel() {

        this.setLayout(new GridLayout(3, 2, 5, 5));
        this.setBorder(BorderFactory.createTitledBorder("SIS Login Info"));
        JLabel username = new JLabel("Username");
        JLabel password = new JLabel("Password");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        passwordField.setEchoChar('*');

        JLabel empty = new JLabel();
        empty.setVisible(false);

        initiateSession = new JButton("OPEN SESSION");

        ButtonGroup buttonGroup = new ButtonGroup();

        edge = new JRadioButton("Edge");
        firefox = new JRadioButton("Firefox");
        chrome = new JRadioButton("Chrome");

        buttonGroup.add(edge);
        buttonGroup.add(firefox);
        buttonGroup.add(chrome);

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.add(edge);
        radioButtonPanel.add(firefox);
        radioButtonPanel.add(chrome);
        edge.setSelected(true);

        this.add(username);
        this.add(usernameField);
        this.add(password);
        this.add(passwordField);
        this.add(radioButtonPanel);
        this.add(initiateSession);


    }


}
