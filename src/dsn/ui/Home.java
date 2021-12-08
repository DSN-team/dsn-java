package dsn.ui;

import com.dsnteam.dsn.CoreManager;
import dsn.Main;
import dsn.User;

import javax.swing.*;

import java.util.Arrays;

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
    private JTabbedPane tabbedPane1;
    private JPanel friendsRequestsInPanel;
    private JTabbedPane tabbedPane2;
    private JPanel friendsRequestsOutPanel;
    private final JFrame frame;

    public void updateCallback(int getSize) {
        getFriends();
        getFriendsRequestsIn();
    }

    private void getFriends() {
        Main.profile.friends.clear();
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

    private void getFriendsRequestsIn() {
        String[] usernames = CoreManager.getFriendsRequestsIn();
        friendsRequestsInPanel.removeAll();
        if (usernames.length > 0) {
            for (int i = 0; i < usernames.length; i++) {
                String username = usernames[i];
                JPanel panel = new JPanel();
                JButton add = new JButton();
                JButton reject = new JButton();
                int pos = i;

                add.setText("Добавить");
                add.addActionListener(e -> {
                    System.out.print("Accept: ");
                    System.out.println(pos);
                    acceptFriendRequest(pos);
                    getFriendsRequestsIn();
                    getFriends();
                });
                reject.setText("Отклонить");
                reject.addActionListener(e -> {
                    System.out.print("Reject: ");
                    System.out.println(pos);
                    rejectFriendRequest(pos);
                    getFriendsRequestsIn();
                });
                panel.add(new JLabel(username));
                panel.add(add);
                panel.add(reject);

                friendsRequestsInPanel.add(panel);
                friendsRequestsInPanel.revalidate();
            }
        }
        friendsRequestsInPanel.repaint();
    }

    private void getFriendsRequestsOut() {
        String[] usernames = CoreManager.getFriendsRequestsOut();
        friendsRequestsOutPanel.removeAll();
        if (usernames.length > 0) {
            for (int i = 0; i < usernames.length; i++) {
                String username = usernames[i];
                JPanel panel = new JPanel();
                JButton cancel = new JButton();
                int pos = i;
                cancel.setText("Отменить");
                cancel.addActionListener(e -> {
                    System.out.print("Cancel: ");
                    System.out.println(pos);
                    deleteFriendRequest(pos);
                    getFriendsRequestsOut();
                });
                panel.add(new JLabel(username));
                panel.add(cancel);

                friendsRequestsOutPanel.add(panel);
                friendsRequestsOutPanel.revalidate();
            }
        }
        friendsRequestsOutPanel.repaint();
    }

    public Home(JFrame frame) {
        this.frame = frame;
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
        friendsRequestsInPanel.setLayout(new BoxLayout(friendsRequestsInPanel, BoxLayout.Y_AXIS));

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

        getFriends();
        getFriendsRequestsIn();
        getFriendsRequestsOut();

        addFriendButton.addActionListener(e -> {
            AddFriend dialog = new AddFriend();
            dialog.pack();
            dialog.setVisible(true);
            getFriends();
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
