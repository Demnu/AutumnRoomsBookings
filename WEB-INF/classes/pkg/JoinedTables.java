package pkg;

import java.util.ArrayList;

public class JoinedTables {
    private int joinedTablesID;
    private int numberSeats;
    ArrayList<ServableTable> joinedTablesList;

    public int getJoinedTablesID() {
        return joinedTablesID;
    }

    public void setJoinedTablesID(int joinedTablesID) {
        this.joinedTablesID = joinedTablesID;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public ArrayList<ServableTable> getJoinedTablesList() {
        return joinedTablesList;
    }

    public void setJoinedTablesList(ArrayList<ServableTable> joinedTablesList) {
        this.joinedTablesList = joinedTablesList;
    }
}
