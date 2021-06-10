package pkg;

import java.sql.Date;
import java.sql.Time;

public class Booking {
    private int bookingID;
    private int intStaffID;
    private Date dateBooked;
    private Time timeBooked;
    private Time startTimeOfBooking;
    private Time endTimeOfBooking;
    private int numberOfPeople;
    private boolean confirmed;

    public Booking(int bookingID, int intStaffID, Date dateBooked, Time timeBooked, Time startTimeOfBooking, Time endTimeOfBooking, int numberOfPeople, boolean confirmed) {
        this.bookingID = bookingID;
        this.intStaffID = intStaffID;
        this.dateBooked = dateBooked;
        this.timeBooked = timeBooked;
        this.startTimeOfBooking = startTimeOfBooking;
        this.endTimeOfBooking = endTimeOfBooking;
        this.numberOfPeople = numberOfPeople;
        this.confirmed = confirmed;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getIntStaffID() {
        return intStaffID;
    }

    public void setIntStaffID(int intStaffID) {
        this.intStaffID = intStaffID;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
