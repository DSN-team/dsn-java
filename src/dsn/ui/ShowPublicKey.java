package dsn.ui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class ShowPublicKey extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane publicKeyPane;
    private JButton copy;
    private JPanel QRCode;
    private JLabel QRValue;

    public ShowPublicKey(String publicKey) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            ImageIcon icon=new ImageIcon(MatrixToImageWriter.toBufferedImage(writer.encode(publicKey,BarcodeFormat.QR_CODE,256,256)));
            QRValue.setIcon(icon);
        } catch (WriterException e2) {
            e2.printStackTrace();
        }
        buttonOK.addActionListener(e -> onOK());
        publicKeyPane.setText(publicKey);
        copy.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(publicKey);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
