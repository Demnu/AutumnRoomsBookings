package pkg;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeIncrementSection {
    LocalTime timeIncrement;
    private int sectionID;
    private String sectionName;
    private ArrayList<Booking> bookingsInSectionTimeIncrement = new ArrayList<>();
    private int maxCovers;
    private int amountCovers = 0;
    private LocalTime endTime;
    private boolean recommended = false;
    public TimeIncrementSection(int sectionID, String sectionName, int maxCovers, Time maxTimeOfBooking, LocalTime timeIncrements) {
        this.sectionID = sectionID;
        this.sectionName = sectionName;
        this.maxCovers = maxCovers;
        this.timeIncrement = timeIncrements;
        LocalTime localTime = maxTimeOfBooking.toLocalTime();
        int mins = localTime.getHour()*60 + localTime.getMinute();
        endTime = timeIncrements.plusMinutes(mins);

    }

    public boolean isRecommended(int numberOfPeople) {
        if ((numberOfPeople+amountCovers)>maxCovers)
        {
            recommended = false;
        }
        else{
            for (Booking booking: bookingsInSectionTimeIncrement){
                if (booking.getNumberOfSeats()==numberOfPeople){
                    booking.setRecommended(true);
                    recommended=true;

                }
            }
        }
        return recommended;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Booking> getBookingsInSectionTimeIncrement() {
        return bookingsInSectionTimeIncrement;
    }

    public void setBookingsInSectionTimeIncrement(ArrayList<Booking> bookingsInSectionTimeIncrement) {
        this.bookingsInSectionTimeIncrement = bookingsInSectionTimeIncrement;
    }

    public LocalTime getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(LocalTime timeIncrement) {
        this.timeIncrement = timeIncrement;
    }

    public void addBooking(Booking booking) {
        bookingsInSectionTimeIncrement.add(booking);
    }

    public int getMaxCovers() {
        return maxCovers;
    }

    public void setMaxCovers(int maxCovers) {
        this.maxCovers = maxCovers;
    }

    public int getAmountCovers() {
        return amountCovers;
    }

    public void setAmountCovers(int amountCovers) {
        this.amountCovers = amountCovers;
    }
    public boolean moreThanCovers(int numberOfPeople){
        if ((amountCovers+numberOfPeople)>maxCovers){
            return true;
        }
        return false;
    }

    public void addAmountCovers(int numberOfPeople) {
        this.amountCovers+=numberOfPeople;
    }
    public int getAmountCovers(int numberOfPeople){
        return amountCovers+numberOfPeople;
    }
}
