package pkg;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String username;
    private Integer password;
    private Integer staffID;
    private Integer venueID;
    User(){
        name = null;
        password = null;
    }
    User(String username, Integer password){
        User tempUser = UserDatabaseInterface.getUserDetails(username,password);
        this.username = username;
        this.name = tempUser.getName();
        this.staffID = tempUser.staffID;
        this.venueID = tempUser.getVenueID();

    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }


    public Integer getStaffID() {
        return staffID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Integer getVenueID() {
        return venueID;
    }

    public void setVenueID(Integer venueID) {
        this.venueID = venueID;
    }
}
