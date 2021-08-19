package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeIncrementSection {
    LocalTime timeIncrement;
    private int sectionID;
    private String sectionName;
    private ArrayList<Booking> bookingsInSectionTimeIncrement = new ArrayList<>();

    public TimeIncrementSection(int sectionID, String sectionName) {
        this.sectionID = sectionID;
        this.sectionName = sectionName;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Booking> getBookingsInSectionTimeIncrement() {
        return bookingsInSectionTimeIncrement;
    }

    public void setBookingsInSectionTimeIncrement(ArrayList<Booking> bookingsInSectionTimeIncrement) {
        this.bookingsInSectionTimeIncrement = bookingsInSectionTimeIncrement;
    }

    public LocalTime getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(LocalTime timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public void addBooking(Booking booking) {
        bookingsInSectionTimeIncrement.add(booking);
    }
}
