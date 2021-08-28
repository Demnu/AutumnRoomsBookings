package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class BookingFormattedTimeIncrements {
    LocalTime timeIncrement;
    int colspan;
    int covers;
    ArrayList<Booking> bookings = new ArrayList<>();

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public BookingFormattedTimeIncrements() {
    }

    public BookingFormattedTimeIncrements(LocalTime timeIncrement, int colspan) {
        this.timeIncrement = timeIncrement;
        this.colspan = colspan;
    }

    public int getCovers() {
        return covers;
    }

    public void setCovers(int covers) {
        this.covers = covers;
    }

    public void setCovers() {
        this.covers = 0;
        for (Booking booking : bookings){
            covers +=booking.getNumberOfPeople();
        }
    }
    public LocalTime getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(LocalTime timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }
}
