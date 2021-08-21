package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class BookingTime implements Comparable<BookingTime>{
    private LocalTime timeIncrement;
    private boolean isStartTime;
    private boolean isBetween;
    private boolean isEndTime;
    private boolean allowedToStayAfterClosed;

    public BookingTime() {
        isStartTime = false;
        isBetween = false;
        isEndTime = false;
        allowedToStayAfterClosed = false;
    }

    public LocalTime getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(LocalTime timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public boolean isStartTime() {
        return isStartTime;
    }

    public void setIsStartTime(boolean startTime) {
        isStartTime = startTime;
    }

    public boolean isBetween() {
        return isBetween;
    }

    public void setBetween(boolean between) {
        isBetween = between;
    }

    public boolean isEndTime() {
        return isEndTime;
    }

    public void setIsEndTime(boolean endTime) {
        isEndTime = endTime;
    }

    public boolean isAllowedToStayAfterClosed() {
        return allowedToStayAfterClosed;
    }

    public void setAllowedToStayAfterClosed(boolean allowedToStayAfterClosed) {
        this.allowedToStayAfterClosed = allowedToStayAfterClosed;
    }
    public boolean isOpen(){
        if (!isBetween && !isEndTime && !isStartTime){
           return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String str = "";
        str += timeIncrement;
        if (isStartTime){
            str += " " + "Start Time";
        }
        if (isEndTime){
            str += " " + "End Time";
        }
        if (isBetween){
            str += " " + "Between Time";
        }
        if (allowedToStayAfterClosed){
            str += " " + "Closed Time";
        }
        if (!isBetween && !isEndTime && !isStartTime){
            str += " " + "Open Time";
        }
        return str;
    }

    @Override
    public int compareTo(BookingTime o) {
        return  timeIncrement.compareTo(o.getTimeIncrement());
    }
}
