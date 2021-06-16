package pkg;

import java.util.ArrayList;

public class ServableTable {
    private int tableID;
    private int sectionID;
    private int tableNumber;
    private int seats;

    ServableTable(){

    }
    ServableTable(int tableID, int sectionID, int tableNumber, int seats){
        this.tableID = tableID;
        this.sectionID = sectionID;
        this.tableNumber = tableNumber;
        this.seats = seats;
    }
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getTableID() {
        return tableID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeats() {
        return seats;
    }

    public ArrayList<ServableTable> getTableList(int sectionID){
        ArrayList<ServableTable> tableList;
        tableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        return tableList;
    }
}
