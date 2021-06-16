package pkg;
import java.sql.Time;
import java.util.*;
public class Section {
    private int sectionID;
    private String name;
    private String description;
    private Integer maxCapacity;
    private Time maxTimeOfBooking;
    private ArrayList<ServableTable> servableTables;

    Section(){

    }
    Section(int sectionID, String name, String description, Integer maxCapacity, Time maxTimeOfBooking){
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.maxTimeOfBooking = maxTimeOfBooking;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setMaxTimeOfBooking(Time maxTimeOfBooking) {
        this.maxTimeOfBooking = maxTimeOfBooking;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setServableTables(ArrayList<ServableTable> servableTables) {
        this.servableTables = servableTables;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Section> getSectionList(){
        ArrayList<Section> sectionList;
        sectionList = SectionDatabaseInterface.getAllSections();
        return sectionList;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public Time getMaxTimeOfBooking() {
        return maxTimeOfBooking;
    }

    public ArrayList<ServableTable> getServableTables() {
        return servableTables;
    }

    public int getAmountSeats(){
        int amountSeats=0;
        for (int i =0 ; i<servableTables.size();i++){
            amountSeats+= servableTables.get(i).getSeats();
        }
        return amountSeats;
    }
}
