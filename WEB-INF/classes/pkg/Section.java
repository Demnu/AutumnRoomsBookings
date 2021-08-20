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
    private LocalTime timeAllowedToStayAfterSectionClosed;
    private LocalTime timeRequiredAfterBookingIsFinishedLocalTime;
    private Time timeRequiredAfterBookingIsFinishedTime;
    private ArrayList<ServableTable> servableTables;
    private ArrayList<JoinedTables> joinedTables;
    private  ArrayList<LocalTime> startTimes;
    private  ArrayList<LocalTime> endTimes;
    private  LocalTime startTime;
    private  LocalTime endTime;
    private boolean timeConstrained;
    private ArrayList<LocalTime> timeIncrements = new ArrayList<>();
    private int maxCovers;

    public void setTimeIncrements() {
        int openHour = startTime.getHour();
        int openMinute = startTime.getMinute();
        LocalTime tempLocalTime = LocalTime.of(openHour,openMinute);
        int closeHour = endTime.getHour();
        int closeMinute = endTime.getMinute();
        int hoursOpened = closeHour - openHour;
        int minutesOpened = closeMinute - openMinute;
        int totalMinutes = (hoursOpened*60) + minutesOpened;
        int currentMinute = 0;


        while(currentMinute<=totalMinutes){
            timeIncrements.add(tempLocalTime);
            tempLocalTime = tempLocalTime.plusMinutes(15);
            currentMinute +=15;

        }
    }

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

    public boolean isTimeConstrained() {
        return timeConstrained;
    }

    public void setTimeConstrained(boolean timeConstrained) {
        this.timeConstrained = timeConstrained;
    }

    public ArrayList<LocalTime> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(ArrayList<LocalTime> startTimes) {
        this.startTimes = startTimes;
    }

    public ArrayList<LocalTime> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(ArrayList<LocalTime> endTimes) {
        this.endTimes = endTimes;
    }

    public LocalTime getTimeAllowedToStayAfterSectionClosed() {
        return timeAllowedToStayAfterSectionClosed;
    }

    public void setTimeAllowedToStayAfterSectionClosed(LocalTime timeAllowedToStayAfterSectionClosed) {
        this.timeAllowedToStayAfterSectionClosed = timeAllowedToStayAfterSectionClosed;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<LocalTime> getTimeIncrements() {
        return timeIncrements;
    }

    public int getMaxCovers() {
        return maxCovers;
    }

    public void setMaxCovers(int maxCovers) {
        this.maxCovers = maxCovers;
    }
}
