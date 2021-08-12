package pkg;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChangedDateTimes {
    private LocalDate changedDate;
    private LocalTime changedOpenTime;
    private LocalTime changedCloseTime;

    public ChangedDateTimes() {
    }

    public LocalDate getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(LocalDate changedDate) {
        this.changedDate = changedDate;
    }

    public LocalTime getChangedOpenTime() {
        return changedOpenTime;
    }

    public void setChangedOpenTime(LocalTime changedOpenTime) {
        this.changedOpenTime = changedOpenTime;
    }

    public LocalTime getChangedCloseTime() {
        return changedCloseTime;
    }

    public void setChangedCloseTime(LocalTime changedCloseTime) {
        this.changedCloseTime = changedCloseTime;
    }
}
