package pkg;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.Duration;

public class VenueDetails {
    private LocalTime timeVenueOpens;
    private LocalTime timeVenueCloses;
    private ArrayList<LocalTime> timeIncrements;

    public LocalTime getTimeVenueCloses() {
        return timeVenueCloses;
    }

    public VenueDetails(LocalTime timeVenueOpens, LocalTime timeVenueCloses) {
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

    public ArrayList<LocalTime> getTimeIncrements() {
        return timeIncrements;
    }
}
