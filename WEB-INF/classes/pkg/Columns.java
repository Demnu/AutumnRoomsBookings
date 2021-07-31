package pkg;
import java.time.LocalTime;
import java.util.ArrayList;
public class Columns implements Comparable<Columns>{
    private LocalTime timeIncrementLocalTime;
    private String timeIncrement;
    private boolean booked;
    private String bookingDetails;
    private boolean startOfBooking = false;

    @Override
    public String toString() {
        if (bookingDetails.isEmpty()){
            return timeIncrement;
        }
        else{
            return bookingDetails;
        }
    }

    @Override
    public int compareTo(Columns o) {
        return timeIncrementLocalTime.compareTo(o.timeIncrementLocalTime);
    }

    public Columns(String timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public Columns() {
        timeIncrement = "";
        bookingDetails = "";
        startOfBooking = false;
    }

    public LocalTime getTimeIncrementLocalTime() {
        return timeIncrementLocalTime;
    }

    public void setTimeIncrementLocalTime(LocalTime timeIncrementLocalTime) {
        this.timeIncrementLocalTime = timeIncrementLocalTime;
    }

    public String getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(String timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public boolean isStartOfBooking() {
        return startOfBooking;
    }

    public void setStartOfBooking(boolean startOfBooking) {
        this.startOfBooking = startOfBooking;
    }
}
