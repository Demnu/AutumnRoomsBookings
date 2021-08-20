package pkg;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class JoinedTables {
    private int joinedTablesID;
    private int numberSeats;
    private int sectionID;
    ArrayList<ServableTable> joinedTablesList;
    ArrayList<Booking> bookingsOnDay = new ArrayList<>();
    private Time maxTimeOfBooking;
    private String sectionName;
    private Time timeRequiredAfterBookingIsFinished;
    private ArrayList<LocalTime> timeIncrementsBookedOutForDay = new ArrayList<LocalTime>();
    private ArrayList<Integer> bookingIDsOnDay = new ArrayList<>();
    private ArrayList<LocalTime> availableTimeIncrements = new ArrayList<>();
    private ArrayList<Booking> possibleBookings = new ArrayList<>();
    private LocalTime timeAllowedToStayAfterSectionClosed;

    public LocalTime getTimeAllowedToStayAfterSectionClosed() {
        return timeAllowedToStayAfterSectionClosed;
    }

    public void setTimeAllowedToStayAfterSectionClosed(LocalTime timeAllowedToStayAfterSectionClosed) {
        this.timeAllowedToStayAfterSectionClosed = timeAllowedToStayAfterSectionClosed;
    }

    public int getJoinedTablesID() {
        return joinedTablesID;
    }

    public void setJoinedTablesID(int joinedTablesID) {
        this.joinedTablesID = joinedTablesID;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public ArrayList<ServableTable> getJoinedTablesList() {
        return joinedTablesList;
    }

    public void setJoinedTablesList(ArrayList<ServableTable> joinedTablesList) {
        this.joinedTablesList = joinedTablesList;
    }

    public ArrayList<Booking> getBookingsOnDay() {
        return bookingsOnDay;
    }

    public void setBookingsOnDay(ArrayList<Booking> bookingsOnDay) {
        if (bookingsOnDay != null){
            this.bookingsOnDay = bookingsOnDay;
            System.out.println("Tables - ");
            System.out.println(" Bookings on day: " + bookingsOnDay.size());
            for (ServableTable servableTable : joinedTablesList){
                System.out.print(servableTable.getTableNumber()+ ", ");
            }

        }
    }
    public void setTimeIncrementsBookedOutForDay() {
        ArrayList<LocalTime> tempTimeIncrementsBookedOutForToday = new ArrayList<>();
        for (Booking booking : bookingsOnDay){
            booking.setTimeRequiredAfterBookingIsFinished(timeRequiredAfterBookingIsFinished.toLocalTime());
            ArrayList<LocalTime> timeIncrementsOfBooking = booking.getTimeIncrementsForBooking();
            for (LocalTime timeIncrementOfBooking: timeIncrementsOfBooking){
                tempTimeIncrementsBookedOutForToday.add(timeIncrementOfBooking);
            }

        }
        for (LocalTime timeIncrement : tempTimeIncrementsBookedOutForToday){
            System.out.println(timeIncrement);
        }
        this.timeIncrementsBookedOutForDay = tempTimeIncrementsBookedOutForToday;

    }

    public Time getMaxTimeOfBooking() {
        return maxTimeOfBooking;
    }

    public void setMaxTimeOfBooking(Time maxTimeOfBooking) {
        this.maxTimeOfBooking = maxTimeOfBooking;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Time getTimeRequiredAfterBookingIsFinished() {
        return timeRequiredAfterBookingIsFinished;
    }

    public void setTimeRequiredAfterBookingIsFinished(Time timeRequiredAfterBookingIsFinished) {
        this.timeRequiredAfterBookingIsFinished = timeRequiredAfterBookingIsFinished;
    }

    public ArrayList<LocalTime> getTimeIncrementsBookedOutForDay() {
        return timeIncrementsBookedOutForDay;
    }

    public void setTimeIncrementsBookedOutForDay(ArrayList<LocalTime> timeIncrementsBookedOutForDay) {
        this.timeIncrementsBookedOutForDay = timeIncrementsBookedOutForDay;
    }

    public ArrayList<Integer> getBookingIDsOnDay() {
        return bookingIDsOnDay;
    }

    public void setBookingIDsOnDay(ArrayList<Integer> bookingIDsOnDay) {
        this.bookingIDsOnDay = bookingIDsOnDay;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public ArrayList<LocalTime> getAvailableTimeIncrements() {
        return availableTimeIncrements;
    }

    public void setAvailableTimeIncrements(ArrayList<LocalTime> dayTimeIncrements) {
        ArrayList<LocalTime> venueTimeIncrements = new ArrayList<>();
        ArrayList<LocalTime> removedVenueTimeIncrements = new ArrayList<>();
        for (LocalTime timeIncrement : dayTimeIncrements){
            venueTimeIncrements.add(timeIncrement);
        }
        for (LocalTime venueTimeIncrement : venueTimeIncrements){
            for (LocalTime bookedTimeIncrement : timeIncrementsBookedOutForDay){
                if (venueTimeIncrement.compareTo(bookedTimeIncrement)==0){
                    removedVenueTimeIncrements.add(bookedTimeIncrement);
                    System.out.println("Removed " + bookedTimeIncrement);
                }
            }
        }
        venueTimeIncrements.removeAll(removedVenueTimeIncrements);
        Collections.sort(venueTimeIncrements);
        this.availableTimeIncrements = venueTimeIncrements;
    }

    public void setAvailableTimeIncrementsGivenOpenTimeCloseTime(ArrayList<LocalTime> availableTimeIncrements) {
        this.availableTimeIncrements = availableTimeIncrements;
    }

    @Override
    public String toString() {
        String temp = "";
        for (ServableTable servableTable : joinedTablesList){
            temp += servableTable.getTableNumber() + " ";
        }
        return temp;
    }

    public void addTimeIncrementsFromSingleTable(ArrayList<LocalTime> timeIncrementsFromSingleTable) {
        for (LocalTime timeIncrement : timeIncrementsFromSingleTable){
            boolean duplicate = false;
            for (LocalTime addedTimeIncrement : timeIncrementsBookedOutForDay){
                if (timeIncrement.compareTo(addedTimeIncrement)==0){
                    duplicate = true;
                }
            }
            if (duplicate == false){
                System.out.println(timeIncrement);
                timeIncrementsBookedOutForDay.add(timeIncrement);
            }
        }
    }
    public void setPossibleBookings(){
        for (LocalTime temp : availableTimeIncrements){
            System.out.println(temp);
        }
        System.out.println();
        LocalTime lengthOfBooking = maxTimeOfBooking.toLocalTime();
        LocalTime timeToReset = timeRequiredAfterBookingIsFinished.toLocalTime();

        LocalTime endTime = availableTimeIncrements.get(availableTimeIncrements.size()-1);
        int minsTimeAllowedAfterClose = timeAllowedToStayAfterSectionClosed.getMinute() + timeAllowedToStayAfterSectionClosed.getHour()*60;
        int factorOf15Close = minsTimeAllowedAfterClose/15;
        LocalTime timePastClose = availableTimeIncrements.get(availableTimeIncrements.size()-1).plusMinutes(minsTimeAllowedAfterClose);
        ArrayList<LocalTime> allowedTimesPastClose = new ArrayList<>();
        LocalTime tempLocalTime = endTime;
        ArrayList<LocalTime> allowedTimes = new ArrayList<>();
        for (LocalTime localTime : availableTimeIncrements){
            allowedTimes.add(localTime);
        }

        for (int i = 0 ; i <factorOf15Close; i++){
            tempLocalTime =  tempLocalTime.plusMinutes(15);
            allowedTimes.add(tempLocalTime);
        }


        int minutes = lengthOfBooking.getHour()*60 + lengthOfBooking.getMinute() + timeToReset.getHour()*60 + timeToReset.getMinute();
        int factorOf15 = minutes/15;
        ArrayList<Booking> possibleBookingsForTable = new ArrayList<>();
        boolean bookingPossible = true;

        for (LocalTime startTime : availableTimeIncrements){
            LocalTime tempTime = startTime;
            endTime = startTime.plusMinutes(15*factorOf15);
            ArrayList<LocalTime> bookingTimeIncrements = new ArrayList<>();
            bookingTimeIncrements.add(startTime);
            for (int i = 0 ; i<factorOf15; i++){
                tempTime = tempTime.plusMinutes(15);
                bookingTimeIncrements.add(tempTime);
            }
            int count = 0;

            for (LocalTime bookingTimeIncrement : bookingTimeIncrements){
                for (LocalTime availableTimeIncrement : allowedTimes){
                    if (availableTimeIncrement.compareTo(bookingTimeIncrement)==0){
                        count++;
                    }
                }
            }

            if (count == (factorOf15 +1)){
                Booking booking = new Booking();
                booking.setStartTimeOfBookingLocalTime(startTime);
                int mins = lengthOfBooking.getHour()*60 + lengthOfBooking.getMinute();
                LocalTime endTimeExcludingTimeReqToReset = startTime.plusMinutes(mins);
                booking.setEndTimeOfBookingLocalTime(endTimeExcludingTimeReqToReset);
                booking.setSectionID(sectionID);
                booking.setJoinedTablesID(joinedTablesID);
                booking.setTableNumber(toString());
                booking.setHasSingleTable(false);
                possibleBookingsForTable.add(booking);
                System.out.println(bookingTimeIncrements.get(0));
                System.out.println(bookingTimeIncrements.get(bookingTimeIncrements.size()-1));
                System.out.println();

            }
            else {
                //check for possible bookings that go past close time
                if (timeAllowedToStayAfterSectionClosed.compareTo(LocalTime.of(0,0))!=0){

                }

                System.out.println("Not Possible: ");
                System.out.println(bookingTimeIncrements.get(0));
                System.out.println(endTime);
                System.out.println();
            }



        }
        //add hour zero
        this.possibleBookings = possibleBookingsForTable;
    }

    public ArrayList<Booking> getPossibleBookings() {
        return possibleBookings;
    }

    public void setPossibleBookings(ArrayList<Booking> possibleBookings) {
        this.possibleBookings = possibleBookings;
    }
}
