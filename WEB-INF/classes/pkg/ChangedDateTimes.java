package pkg;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChangedDateTimes implements Comparable<ChangedDateTimes> {
    private int changedDateID;
    private LocalDate changedDate;
    private LocalTime changedOpenTime;
    private LocalTime changedCloseTime;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChangedDateID() {
        return changedDateID;
    }

    public void setChangedDateID(int changedDateID) {
        this.changedDateID = changedDateID;
    }

    @Override
    public int compareTo(ChangedDateTimes o) {
        return changedDate.compareTo(o.changedDate);
    }
}
