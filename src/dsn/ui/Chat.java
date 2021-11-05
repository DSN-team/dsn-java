package dsn.ui;

import dsn.Main;
import dsn.User;

import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.dsnteam.dsn.CoreManager.connectToFriend;
import static com.dsnteam.dsn.CoreManager.writeBytes;
import static dsn.Main.*;

public class Chat {
    private JPanel mainPanel;
    private JTextField input;
    private JButton sendButton;
    private JButton backButton;
    private JButton friendProfileButton;
    private JPanel messagesPanel;
    private JLabel friendName;

    public void updateCallback(int size) {
        System.out.println("buffer:" + callBackBuffer);
        callBackBuffer.position(0);
        callBackBuffer.limit(size);
        String msg = StandardCharsets.UTF_16.decode(callBackBuffer).toString();
        callBackBuffer.position(0);
        System.out.println("Got callback msg is:" + msg);
        callBackBuffer.limit(64);
        byte[] bytes = new byte[64];
        callBackBuffer.get(bytes);
        callBackBuffer.position(0);
        System.out.println("Got callback buffer is:" + Arrays.toString(bytes));
        addMessage(friendName.getText(), msg);
    }

    public void addMessage(String ownerName, String message) {
        JPanel messagePanel = new JPanel();
        messagePanel.add(new JLabel(ownerName));
        messagePanel.add(new JLabel(message));
        messagesPanel.add(messagePanel);
        messagePanel.revalidate();
        messagePanel.repaint();
    }

    public Chat(JFrame frame, User friend) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        chat = this;
        home = null;
        chatID = friend.id;

        friendName.setText(friend.username);

        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));


        backButton.addActionListener(e -> {
            new Home(frame);
        });
        friendProfileButton.addActionListener(e -> {

        });
        sendButton.addActionListener(e -> {
            byteBuffer.position(0);
            byte[] bytes = (input.getText()).getBytes(StandardCharsets.UTF_16);
            byteBuffer.put(bytes);
            byteBuffer.put((byte) 0);
            System.out.println(byteBuffer);
            System.out.println("Byte buffer pos:" + byteBuffer.position());
            System.out.print("Friend id:");
            System.out.println(friend.id);
            writeBytes(byteBuffer, bytes.length, friend.id);

            addMessage(profile.username, input.getText());

            input.setText("");
        });

        connectToFriend(friend.id);
    }
}
