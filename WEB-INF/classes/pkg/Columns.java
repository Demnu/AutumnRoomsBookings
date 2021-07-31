package pkg;
import java.time.LocalTime;
import java.sql.*;
import java.util.ArrayList;
public class Columns implements Comparable<Columns>{
    private LocalTime timeIncrementLocalTime;
    private String timeIncrement;
    private boolean booked;
    private String bookingDetails;
    private boolean startOfBooking = false;
    private int amountOfTimeIncrements;
    private String endTimeOfBooking;
    private String startTimeOfBooking;
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
        amountOfTimeIncrements = 0;
        endTimeOfBooking = "";
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

    public int getAmountOfTimeIncrements() {
        return amountOfTimeIncrements;
    }

    public void setAmountOfTimeIncrements(int amountOfTimeIncrements) {
        this.amountOfTimeIncrements = amountOfTimeIncrements;
    }

    public String getEndTimeOfBooking() {
        return endTimeOfBooking;
    }

    public void setEndTimeOfBooking(Time endTimeOfBooking) {
        String time;
        int hoursInt = endTimeOfBooking.getHours();
        int minutesInt = endTimeOfBooking.getMinutes();
        String hoursStr;
        String minutesStr;
        if (hoursInt < 10){
            hoursStr = "0" + hoursInt;
        }
        else{
            hoursStr = Integer.toString(hoursInt);
        }
        if (endTimeOfBooking.getMinutes()==0){
            minutesStr ="00";
        }
        else{
            minutesStr = Integer.toString(minutesInt);
        }
        time = hoursStr + ":" + minutesStr;
        this.endTimeOfBooking = time;
    }

    public String getStartTimeOfBooking() {
        return startTimeOfBooking;
    }

    public void setStartTimeOfBooking(Time startTimeOfBooking) {
        String time;
        int hoursInt = startTimeOfBooking.getHours();
        int minutesInt = startTimeOfBooking.getMinutes();
        String hoursStr;
        String minutesStr;
        if (hoursInt < 10){
            hoursStr = "0" + hoursInt;
        }
        else{
            hoursStr = Integer.toString(hoursInt);
        }
        if (startTimeOfBooking.getMinutes()==0){
            minutesStr ="00";
        }
        else{
            minutesStr = Integer.toString(minutesInt);
        }
        time = hoursStr + ":" + minutesStr;
        this.startTimeOfBooking = time;
    }
}
