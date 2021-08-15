package pkg;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
public class Section {
    private int sectionID;
    private String name;
    private String description;
    private Integer maxCoversSection;
    private Time maxTimeOfBooking;
    private LocalTime maxTimeOfBookingLocalTime;
    private LocalTime timeRequiredAfterBookingIsFinishedLocalTime;
    private Time timeRequiredAfterBookingIsFinishedTime;
    private ArrayList<ServableTable> servableTables;
    private ArrayList<JoinedTables> joinedTables;

    Section(){

    }
    Section(int sectionID, String name, String description, Integer maxCoversSection, Time maxTimeOfBooking, Time timeRequiredAfterBookingIsFinishedTime){
        this.name = name;
        this.description = description;
        this.maxCoversSection = maxCoversSection;
        this.maxTimeOfBooking = maxTimeOfBooking;
        this.timeRequiredAfterBookingIsFinishedTime = timeRequiredAfterBookingIsFinishedTime;
        joinedTables = new ArrayList<>();
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

    public void setMaxCoversSection(Integer maxCoversSection) {
        this.maxCoversSection = maxCoversSection;
    }

    public void setMaxTimeOfBooking(Time maxTimeOfBooking) {
        this.maxTimeOfBooking = maxTimeOfBooking;
        maxTimeOfBookingLocalTime = maxTimeOfBooking.toLocalTime();
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setTimeRequiredAfterBookingIsFinishedTime(Time timeRequiredAfterBookingIsFinishedTime) {
        this.timeRequiredAfterBookingIsFinishedTime = timeRequiredAfterBookingIsFinishedTime;
        timeRequiredAfterBookingIsFinishedLocalTime = timeRequiredAfterBookingIsFinishedTime.toLocalTime();
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

    public Integer getMaxCoversSection() {
        return maxCoversSection;
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

    public Time getTimeRequiredAfterBookingIsFinishedTime() {
        return timeRequiredAfterBookingIsFinishedTime;
    }

    public ArrayList<JoinedTables> getJoinedTables() {
        return joinedTables;
    }

    public void setJoinedTables(ArrayList<JoinedTables> joinedTables) {
        this.joinedTables = joinedTables;
    }

    public LocalTime getMaxTimeOfBookingLocalTime() {
        return maxTimeOfBookingLocalTime;
    }

    public void setMaxTimeOfBookingLocalTime(LocalTime maxTimeOfBookingLocalTime) {
        this.maxTimeOfBookingLocalTime = maxTimeOfBookingLocalTime;
    }

    public LocalTime getTimeRequiredAfterBookingIsFinishedLocalTime() {
        return timeRequiredAfterBookingIsFinishedLocalTime;
    }

    public void setTimeRequiredAfterBookingIsFinishedLocalTime(LocalTime timeRequiredAfterBookingIsFinishedTimeLocalTime) {
        this.timeRequiredAfterBookingIsFinishedLocalTime = timeRequiredAfterBookingIsFinishedTimeLocalTime;
    }
}
