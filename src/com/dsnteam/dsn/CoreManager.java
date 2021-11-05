package com.dsnteam.dsn;

import dsn.Main;

import java.io.File;
import java.nio.Buffer;

import static java.lang.System.mapLibraryName;

public class CoreManager {

    public static native void initDB();

    public static void getUpdateCallBack(int getSize, int chatID) {
        System.out.print("update call back chatID ");
        System.out.println(chatID);

        if (Main.chatID == chatID) {
            System.out.println("opened chat callback");
            Main.chat.updateCallback(getSize);
        } else {
            System.out.println("closed chat callback");
            System.out.print("opened chat id: ");
            System.out.println(Main.chatID);
        }

    }

    public static native void setCallBackBuffer(Buffer callbackBuffer);

    public static native void writeBytes(Buffer writing, long length, long FriendID);

    public static native void runServer(String address);

    public static native void runClient(String address);

    public static native boolean login(long pos, String password);

    public static native boolean register(String username, String password, String address);

    public static native long loadProfiles();

    public static native String[] getProfilesNames();

    public static native void addFriend(String username, String address, String publicKey);

    public static native String getProfileName();

    public static native String getProfileAddress();

    public static native String getProfilePublicKey();

    public static native long loadFriends();

    public static native long[] getFriendsIds();

    public static native String[] getFriendsNames();

    public static native String[] getFriendsAddresses();

    public static native String[] getFriendsPublicKeys();

    public static native void connectToFriends();

    public static native void connectToFriend(long id);

    public static void loadLibrary() {
        File cur = new File("libs");
        System.load(cur.getAbsolutePath() + "/" + mapLibraryName("dsncore"));
    }
}
