package pkg;

import java.lang.reflect.Array;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.Duration;

public class Venue {
    private String venueName;
    private int venueID;
    private LocalTime timeVenueOpens;
    private LocalTime timeVenueCloses;
    private ArrayList<LocalTime> timeIncrements = new ArrayList<>();
    private ArrayList<LocalTime> openTimes= new ArrayList<>();
    private ArrayList<LocalTime> closeTimes= new ArrayList<>();
    private ArrayList<ChangedDateTimes> changedDateTimes= new ArrayList<>();
    private int maxCovers;

    public Venue(String venueName) {

    }

    public Venue() {

    }



    public LocalTime getTimeVenueCloses() {
        return timeVenueCloses;
    }

    public Venue(LocalTime timeVenueOpens, LocalTime timeVenueCloses) {
        this.timeVenueOpens = timeVenueOpens;
        this.timeVenueCloses = timeVenueCloses;
        timeIncrements = new ArrayList<LocalTime>();
        setTimeIncrements();

    }

    public void setTimeVenueCloses(LocalTime timeVenueCloses) {
        this.timeVenueCloses = timeVenueCloses;
    }

    public LocalTime getTimeVenueOpens() {
        return timeVenueOpens;
    }

    public void setTimeIncrements() {
        int openHour = timeVenueOpens.getHour();
        int openMinute = timeVenueOpens.getMinute();
        LocalTime tempLocalTime = LocalTime.of(openHour,openMinute);
        int closeHour = timeVenueCloses.getHour();
        int closeMinute = timeVenueCloses.getMinute();
        int hoursOpened = closeHour - openHour;
        int minutesOpened = closeMinute - openMinute;
        int totalMinutes = (hoursOpened*60) + minutesOpened;
        int currentMinute = 0;


        while(currentMinute<=totalMinutes){
            timeIncrements.add(tempLocalTime);
            tempLocalTime = tempLocalTime.plusMinutes(15);
            currentMinute +=15;

        }

    }

    public void setTimeVenueOpens(LocalTime timeVenueOpens) {
        this.timeVenueOpens = timeVenueOpens;
    }

    public void setTimeIncrements(ArrayList<LocalTime> timeIncrements) {
        this.timeIncrements = timeIncrements;
    }

    public ArrayList<LocalTime> getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(ArrayList<LocalTime> openTimes) {
        this.openTimes = openTimes;
    }

    public ArrayList<LocalTime> getCloseTimes() {
        return closeTimes;
    }

    public void setCloseTimes(ArrayList<LocalTime> closeTimes) {
        this.closeTimes = closeTimes;
    }

    public ArrayList<ChangedDateTimes> getChangedDateTimes() {
        return changedDateTimes;
    }

    public void setChangedDateTimes() {
        this.changedDateTimes = ChangedDateDatabaseInterface.getChangedDateTimes(this.venueID);
    }

    public ArrayList<LocalTime> getTimeIncrements() {
        return timeIncrements;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getMaxCovers() {
        return maxCovers;
    }

    public void setMaxCovers(int maxCovers) {
        this.maxCovers = maxCovers;
    }

    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
    }
}
