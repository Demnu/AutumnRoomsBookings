package pkg;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String username;
    Integer password;
    User(){
        name = null;
        password = null;
    }
    User(String username, Integer password){
        this.username =username;
        this.password = password;
        name = UserDatabaseInterface.getStaffName(username,password);
    }

    public String getName() {
        return name;
    }
}
