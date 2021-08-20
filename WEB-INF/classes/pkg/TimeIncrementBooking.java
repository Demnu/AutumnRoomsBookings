package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeIncrementBooking implements Comparable<TimeIncrementBooking> {
    private LocalTime timeIncrement;
    private ArrayList<TimeIncrementSection> sections;
    private boolean isClosed = false;
    private int venueCovers;
    private int amountCovers=0;


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

    @Override
    public int compareTo(TimeIncrementBooking o) {
        return timeIncrement.compareTo(o.getTimeIncrement());
    }

    public boolean isEmpty(){

        for (TimeIncrementSection section :sections){
            if (!section.getBookingsInSectionTimeIncrement().isEmpty()){
                return  false;
            }
        }
        return true;
    }

    public int getVenueCovers() {
        return venueCovers;
    }

    public void setVenueCovers(int venueCovers) {
        this.venueCovers = venueCovers;
    }

    public int getAmountCovers() {
        return amountCovers;
    }

    public void setAmountCovers() {
        for (TimeIncrementSection timeIncrementSection : sections){
            amountCovers+= timeIncrementSection.getAmountCovers();
        }
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean reachedMax(){
        if (amountCovers>=venueCovers){
            return true;
        }
        return false;
    }
    public boolean willGoPastMaxCovers(int numPeople){
        if((amountCovers+numPeople)>venueCovers){
            return true;
        }
        return false;
    }
}
