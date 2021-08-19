package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeIncrementBooking {
    private LocalTime timeIncrement;
    private ArrayList<TimeIncrementSection> sections;

    TimeIncrementBooking(LocalTime timeIncrement, ArrayList<TimeIncrementSection> sections){
        this.timeIncrement = timeIncrement;
        this.sections = sections;
    }

    public TimeIncrementBooking() {
    }

    public LocalTime getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(LocalTime timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public ArrayList<TimeIncrementSection> getSections() {
        return sections;
    }

    public void setSections(ArrayList<TimeIncrementSection> sections) {
        this.sections = sections;
    }
}
