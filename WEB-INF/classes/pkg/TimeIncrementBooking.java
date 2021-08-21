package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class TimeIncrementBooking implements Comparable<TimeIncrementBooking> {
    private LocalTime timeIncrement;
    private ArrayList<TimeIncrementSection> sections;
    private boolean isClosed = false;
    private int venueCovers;
    private int amountCovers=0;
    int numberOfPeople = 0;


    TimeIncrementBooking(LocalTime timeIncrement, ArrayList<TimeIncrementSection> sections){
        this.timeIncrement = timeIncrement;
        this.sections = sections;
    }
    public TimeIncrementBooking() {
    }

    public void setRecommended() {

        boolean noRecommended = true;
        for (TimeIncrementSection timeIncrementSection : sections){
            if (timeIncrementSection.isRecommended(numberOfPeople)){
                noRecommended = false;
            }
        }
        ArrayList<Booking> foundBookings = new ArrayList<>();
        ArrayList<Booking> bookingsRecommended = new ArrayList<>();
        int largestNumberOfSeats = 0;
        if (noRecommended){
            for (TimeIncrementSection timeIncrementSection : sections){
                for (Booking booking : timeIncrementSection.getBookingsInSectionTimeIncrement()){
                    foundBookings.add(booking);
                    if (booking.getNumberOfSeats()>largestNumberOfSeats){
                        largestNumberOfSeats = booking.getNumberOfSeats();
                    }
                }
            }
            System.out.println(" Largest Number of Seats: " + largestNumberOfSeats);
            boolean foundSeats = false;
            int counter = numberOfPeople;
            int smallestNumberOfSeats = 0;
            for (int i = counter ; i<largestNumberOfSeats ; i++){
                for (Booking booking : foundBookings){
                    if (booking.getNumberOfSeats()==i){
                        smallestNumberOfSeats = i;
                        System.out.println("Tables made recommended with table size: " + smallestNumberOfSeats);

                        foundSeats = true;
                    }
                }
                if (foundSeats){
                    break;
                }
            }
            //make bookings recommended
            for (TimeIncrementSection timeIncrementSection : sections){
                for (Booking booking : timeIncrementSection.getBookingsInSectionTimeIncrement()){
                    if (!timeIncrementSection.moreThanCovers(numberOfPeople) && booking.getNumberOfSeats()==smallestNumberOfSeats){
                        booking.setRecommended(true);
                        timeIncrementSection.setRecommended(true);
                    }
                }
            }

        }
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
