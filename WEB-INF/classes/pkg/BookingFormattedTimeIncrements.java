package pkg;

import java.time.LocalTime;

public class BookingFormattedTimeIncrements {
    LocalTime timeIncrement;
    int colspan;

    public BookingFormattedTimeIncrements(LocalTime timeIncrement, int colspan) {
        this.timeIncrement = timeIncrement;
        this.colspan = colspan;
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
