package dsn.ui;

import com.dsnteam.dsn.CoreManager;
import dsn.Main;
import dsn.User;

import javax.swing.*;

import static com.dsnteam.dsn.CoreManager.*;

public class Home {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel news;
    private JPanel chats;
    private JPanel friends;
    private JPanel chatsPanel;
    private JPanel friendsPanel;
    private JPanel newsPanel;
    private JButton addFriendButton;
    private JButton showPublicKeyButton;
    private JPanel settings;
    private JTextField profileAddress;
    private JButton profileSaveButton;
    private JButton profileDeleteButton;
    private JLabel profileName;

    public void updateCallback(int getSize) {

    }

    private void getFriends(JFrame frame) {
        long count = loadFriends();
        if (count > 0) {
            long[] ids = getFriendsIds();
            String[] names = getFriendsNames();
            String[] address = getFriendsAddresses();
            String[] publicKeys = getFriendsPublicKeys();

            for (int i = 0; i < names.length; i++) {
                Main.profile.friends.add(new User(ids[i], names[i], address[i], publicKeys[i]));
            }

            friendsPanel.removeAll();
            for (User friend : Main.profile.friends) {
                JPanel panel = new JPanel();
                JButton chat = new JButton();

                chat.setText("Написать");
                chat.addActionListener(e -> new Chat(frame, friend));
                panel.add(new JLabel(friend.username));
                panel.add(chat);

                friendsPanel.add(panel);
                friendsPanel.revalidate();
            }
            friendsPanel.repaint();
        }
    }

    public Home(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        Main.home = this;
        Main.chat = null;
        Main.chatID = 0;

        CoreManager.setCallBackBuffer(Main.callBackBuffer);

        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        chatsPanel.setLayout(new BoxLayout(chatsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));

        Main.profile = new User(0, getProfileName(), getProfileAddress(), getProfilePublicKey());
        if (Main.profile.address == null) {
            Main.profile.address = "0.0.0.0:4444";
        }

        profileName.setText(Main.profile.username);
        profileAddress.setText(Main.profile.address);

        if (!Main.serverRunning) {
            CoreManager.runServer(Main.profile.address);
            Main.serverRunning = true;
        }

        getFriends(frame);
//        connectToFriends();

        addFriendButton.addActionListener(e -> {
            AddFriend dialog = new AddFriend();
            dialog.pack();
            dialog.setVisible(true);
            getFriends(frame);
        });
        showPublicKeyButton.addActionListener(e -> {
            ShowPublicKey dialog = new ShowPublicKey(Main.profile.publicKey);
            dialog.pack();
            dialog.setVisible(true);
        });
        profileSaveButton.addActionListener(e -> {

        });
        profileDeleteButton.addActionListener(e -> {

        });
    }


}
