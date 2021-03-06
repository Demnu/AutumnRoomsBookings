package pkg;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class Booking implements Comparable{
    private int bookingID;
    private int staffID;
    private Date dateBooked;
    private Time timeBooked;
    private Date dateOfBooking;
    private Time startTimeOfBooking;
    private Time endTimeOfBooking;
    private LocalTime startTimeOfBookingLocalTime;
    private LocalTime endTimeOfBookingLocalTime;
    private int numberOfPeople;
    private boolean confirmed;
    private ArrayList<ServableTable> assignedTables;
    private LocalTime timeRequiredAfterBookingIsFinished;
    private String staffName;
    private int tableID;
    private int joinedTablesID;
    private int sectionID;
    boolean hasSingleTable;
    private String tableNumber;
    private String joinedTableNumber;
    private int numberOfSeats;
    private boolean recommended;
    private ArrayList<BookingTime> bookingTimes = new ArrayList<>();

    public ArrayList<BookingTime> getBookingTimes() {
        return bookingTimes;
    }
    public void setBookingTimesIgnoreTimeToReset() {
        timeRequiredAfterBookingIsFinished = LocalTime.of(0,0);
        ArrayList<BookingTime> tempBookingTimes = new ArrayList<BookingTime>();
        int startOfBookingHour = startTimeOfBookingLocalTime.getHour();
        int startOfBookingMinute = startTimeOfBookingLocalTime.getMinute();
        int endOfBookingHour = endTimeOfBookingLocalTime.getHour();
        int endOfBookingMinute = endTimeOfBookingLocalTime.getMinute();
        int hoursOpened = endOfBookingHour - startOfBookingHour;
        int minutesOpened = endOfBookingMinute - startOfBookingMinute;
        int totalMinutes = (hoursOpened*60) + minutesOpened;
        int currentMinute = 0;
        LocalTime tempLocalTime = LocalTime.of(startOfBookingHour,startOfBookingMinute);
        while(currentMinute<=totalMinutes){
            BookingTime bookingTime = new BookingTime();
            if (tempLocalTime.compareTo(startTimeOfBookingLocalTime)==0){
                bookingTime.setIsStartTime(true);
            }
            else if (tempLocalTime.compareTo(endTimeOfBookingLocalTime)==0 && timeRequiredAfterBookingIsFinished.compareTo(LocalTime.of(0,0))==0){
                bookingTime.setIsEndTime(true);
            }
            else{
                bookingTime.setBetween(true);

            }
            bookingTime.setTimeIncrement(tempLocalTime);
            tempBookingTimes.add(bookingTime);
            tempLocalTime = tempLocalTime.plusMinutes(15);
            currentMinute +=15;
        }
        this.bookingTimes =  tempBookingTimes;

    }
    public void setBookingTimes() {
        ArrayList<BookingTime> tempBookingTimes = new ArrayList<BookingTime>();
        int startOfBookingHour = startTimeOfBookingLocalTime.getHour();
        int startOfBookingMinute = startTimeOfBookingLocalTime.getMinute();
        int endOfBookingHour = endTimeOfBookingLocalTime.getHour();
        int endOfBookingMinute = endTimeOfBookingLocalTime.getMinute();
        int hoursOpened = endOfBookingHour - startOfBookingHour;
        int minutesOpened = endOfBookingMinute - startOfBookingMinute;
        int totalMinutes = (hoursOpened*60) + minutesOpened;
        int currentMinute = 0;
        LocalTime tempLocalTime = LocalTime.of(startOfBookingHour,startOfBookingMinute);
        while(currentMinute<=totalMinutes){
            BookingTime bookingTime = new BookingTime();
            if (tempLocalTime.compareTo(startTimeOfBookingLocalTime)==0){
                bookingTime.setIsStartTime(true);
            }
            else if (tempLocalTime.compareTo(endTimeOfBookingLocalTime)==0 && timeRequiredAfterBookingIsFinished.compareTo(LocalTime.of(0,0))==0){
                bookingTime.setIsEndTime(true);
            }
            else{
                bookingTime.setBetween(true);

            }
            bookingTime.setTimeIncrement(tempLocalTime);
            tempBookingTimes.add(bookingTime);
            tempLocalTime = tempLocalTime.plusMinutes(15);
            currentMinute +=15;
        }
        //add time required after booking to increments
        int factorOf15 = (timeRequiredAfterBookingIsFinished.getHour()*60+timeRequiredAfterBookingIsFinished.getMinute())/15;
        tempLocalTime = tempBookingTimes.get(tempBookingTimes.size()-1).getTimeIncrement();
        for (int i = 0 ; i<factorOf15;i++){
            BookingTime bookingTime = new BookingTime();
            tempLocalTime = tempLocalTime.plusMinutes(15);
            if (i == (factorOf15-1)){
                bookingTime.setIsEndTime(true);
            }
            else{
                bookingTime.setBetween(true);
            }
            bookingTime.setTimeIncrement(tempLocalTime);
            tempBookingTimes.add(bookingTime);
        }
        this.bookingTimes =  tempBookingTimes;

    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public ArrayList<ServableTable> getAssignedTables() {
        return assignedTables;
    }

    public void setAssignedTables(ArrayList<ServableTable> assignedTables) {
        this.assignedTables = assignedTables;
    }

    public Booking() {
    }

    public String getStaffName() {
        return staffName;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
    }

    public Time getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(Time timeBooked) {
        this.timeBooked = timeBooked;
    }

    public void setTimeRequiredAfterBookingIsFinished(LocalTime timeRequiredAfterBookingIsFinished) {
        this.timeRequiredAfterBookingIsFinished = timeRequiredAfterBookingIsFinished;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public Time getStartTimeOfBooking() {
        return startTimeOfBooking;
    }

    public void setStartTimeOfBooking(Time startTimeOfBooking) {
        this.startTimeOfBooking = startTimeOfBooking;
        int tempHours = startTimeOfBooking.getHours();
        int tempMinutes = startTimeOfBooking.getMinutes();
        startTimeOfBookingLocalTime = LocalTime.of(tempHours,tempMinutes,0);
    }

    public Time getEndTimeOfBooking() {
        return endTimeOfBooking;
    }

    public void setEndTimeOfBooking(Time endTimeOfBooking) {
        this.endTimeOfBooking = endTimeOfBooking;
        int tempHours = endTimeOfBooking.getHours();
        int tempMinutes = endTimeOfBooking.getMinutes();
        endTimeOfBookingLocalTime = LocalTime.of(tempHours,tempMinutes,0);
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public LocalTime getTimeRequiredAfterBookingIsFinished() {
        return timeRequiredAfterBookingIsFinished;
    }

    public LocalTime getStartTimeOfBookingLocalTime() {
        return startTimeOfBookingLocalTime;
    }

    public void setStartTimeOfBookingLocalTime(LocalTime startTimeOfBookingLocalTime) {
        this.startTimeOfBookingLocalTime = startTimeOfBookingLocalTime;
    }

    public LocalTime getEndTimeOfBookingLocalTime() {
        return endTimeOfBookingLocalTime;
    }

    public void setEndTimeOfBookingLocalTime(LocalTime endTimeOfBookingLocalTime) {
        this.endTimeOfBookingLocalTime = endTimeOfBookingLocalTime;
    }
    public ArrayList<LocalTime> getTimeIncrementsForBooking(){
        ArrayList<LocalTime> timeIncrementsForBooking = new ArrayList<LocalTime>();
        int startOfBookingHour = startTimeOfBookingLocalTime.getHour();
        int startOfBookingMinute = startTimeOfBookingLocalTime.getMinute();
        LocalTime tempLocalTime = LocalTime.of(startOfBookingHour,startOfBookingMinute);
        int endOfBookingHour = endTimeOfBookingLocalTime.getHour();
        int endOfBookingMinute = endTimeOfBookingLocalTime.getMinute();
        int hoursOpened = endOfBookingHour - startOfBookingHour;
        int minutesOpened = endOfBookingMinute - startOfBookingMinute;
        int totalMinutes = (hoursOpened*60) + minutesOpened;
        int currentMinute = 0;


        while(currentMinute<=totalMinutes){
            timeIncrementsForBooking.add(tempLocalTime);
            tempLocalTime = tempLocalTime.plusMinutes(15);
            currentMinute +=15;

        }
        //add time required after booking to increments
        int factorOf15 = (timeRequiredAfterBookingIsFinished.getHour()*60+timeRequiredAfterBookingIsFinished.getMinute())/15;
        tempLocalTime = timeIncrementsForBooking.get(timeIncrementsForBooking.size()-1);
        for (int i = 0 ; i<factorOf15;i++){

            tempLocalTime = tempLocalTime.plusMinutes(15);
            timeIncrementsForBooking.add(tempLocalTime);
        }
        return  timeIncrementsForBooking;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getJoinedTablesID() {
        return joinedTablesID;
    }

    public void setJoinedTablesID(int joinedTablesID) {
        this.joinedTablesID = joinedTablesID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public boolean isHasSingleTable() {
        return hasSingleTable;
    }

    public void setHasSingleTable(boolean hasSingleTable) {
        this.hasSingleTable = hasSingleTable;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getJoinedTableNumber() {
        return joinedTableNumber;
    }

    public void setJoinedTableNumber(String joinedTableNumber) {
        this.joinedTableNumber = joinedTableNumber;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
