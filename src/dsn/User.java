package dsn;

import java.util.ArrayList;

public class User {
    public long id;
    public String username;
    public String address;
    public String publicKey;
    public final ArrayList<User> friends;

    public User(long id, String username, String address, String publicKey) {
        friends = new ArrayList<>();
        this.id = id;
        this.username = username;
        this.address = address;
        this.publicKey = publicKey;
    }

    public String toString() {
        return username + ' ' + address + ' ' + publicKey + ' ' + friends.size();
    }
}
