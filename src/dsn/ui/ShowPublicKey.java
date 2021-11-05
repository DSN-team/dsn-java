package dsn.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPublicKey extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane publicKeyPane;

    public ShowPublicKey(String publicKey) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        publicKeyPane.setText(publicKey);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
