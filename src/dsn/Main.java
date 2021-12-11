package dsn;

import com.dsnteam.dsn.CoreManager;
import dsn.ui.Auth;
import dsn.ui.Chat;
import dsn.ui.Home;

import javax.swing.*;
import java.nio.ByteBuffer;

import static com.dsnteam.dsn.CoreManager.setCallBackBuffer;

public class Main {
    public static Auth auth;
    public static ByteBuffer byteBuffer = ByteBuffer.allocateDirect(256);
    public static ByteBuffer callBackBuffer = ByteBuffer.allocateDirect(256);
    public static User profile;
    public static boolean serverRunning = false;
    public static long chatID = 0;
    public static Chat chat;
    public static Home home;


    public static void main(String[] args) {
        try {
            CoreManager.loadLibrary();
        } catch (Exception ignored) {
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        CoreManager.initDB();
        JFrame frame = new JFrame("Dsn Auth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        setCallBackBuffer(callBackBuffer);
        auth = new Auth(frame);
    }
}