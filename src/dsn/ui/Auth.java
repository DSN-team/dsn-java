package dsn.ui;

import com.dsnteam.dsn.CoreManager;

import javax.swing.*;

import static com.dsnteam.dsn.CoreManager.*;


public class Auth {
    private JPanel mainPanel;
    private JButton authButton;
    private JButton regButton;
    private JFormattedTextField usernameText;
    private JPasswordField authPasswordInput;
    private JButton loginButton;
    private JComboBox authUsernameInput;
    private JTextField registerUsernameInput;
    private JPasswordField registerPasswordInput1;
    private JButton registerButton;
    private JPasswordField registerPasswordInput2;
    private JTextField registerAddressInput;
    JFrame frame;

    public Auth(JFrame frame) {
        loadProfiles();
        String[] usernames = CoreManager.getProfilesNames();
        this.frame = frame;
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
        for (String username : usernames) {
            authUsernameInput.addItem(username);
        }
        loginButton.addActionListener(e -> {
            System.out.println("login");
            loadProfiles();
            frame.getContentPane().remove(mainPanel);
            boolean result = login(authUsernameInput.getSelectedIndex(), new String(authPasswordInput.getPassword()));
            if (result) {
                new Home(frame);
            } else {
                new Auth(frame);
            }
        });
        registerButton.addActionListener(e -> {
            System.out.println("register");
            String password1 = new String(registerPasswordInput2.getPassword());
            String password2 = new String(registerPasswordInput2.getPassword());
            if (password1.equals(password2)) {
                register(registerUsernameInput.getText(), password1, registerAddressInput.getText());
                loadProfiles();
                new Home(frame);
            }
        });
    }
}
