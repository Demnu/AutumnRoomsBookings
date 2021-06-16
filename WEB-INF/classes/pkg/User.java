package pkg;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String username;
    private Integer password;
    private Integer staffID;
    User(){
        name = null;
        password = null;
    }
    User(String username, Integer password){
        this.username =username;
        this.password = password;
        this.name = UserDatabaseInterface.getStaffName(username,password);
        this.staffID = UserDatabaseInterface.getStaffID(username,password);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPassword() {
        return password;
    }

    public Integer getStaffID() {
        return staffID;
    }
}
