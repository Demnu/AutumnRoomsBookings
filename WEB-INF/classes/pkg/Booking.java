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
    private Time timeRequiredAfterBookingIsFinished;
    private String staffName;

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

    public void setTimeRequiredAfterBookingIsFinished(Time timeRequiredAfterBookingIsFinished) {
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

    public Time getTimeRequiredAfterBookingIsFinished() {
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
        return  timeIncrementsForBooking;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
