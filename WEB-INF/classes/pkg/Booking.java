package pkg;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Booking {
    private int bookingID;
    private int staffID;
    private Date dateBooked;
    private Time timeBooked;
    private Date dateOfBooking;
    private Time startTimeOfBooking;
    private Time endTimeOfBooking;
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
    }

    public Time getEndTimeOfBooking() {
        return endTimeOfBooking;
    }

    public void setEndTimeOfBooking(Time endTimeOfBooking) {
        this.endTimeOfBooking = endTimeOfBooking;
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
}
