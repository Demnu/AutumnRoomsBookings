package pkg;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;

public class ServableTable {
    private int tableID;
    private int sectionID;
    private int tableNumber;
    private int seats;
    private Time maxTimeOfBooking;
    private String sectionName;
    private Time timeRequiredAfterBookingIsFinished;
    private ArrayList<LocalTime> timeIncrementsBookedOutForDay = new ArrayList<LocalTime>();
    private ArrayList<LocalTime> availableTimeIncrements = new ArrayList<>();
    private ArrayList<Booking> bookingsOnDay = new ArrayList<Booking>();
    private ArrayList<Integer> bookingIDsOnDay = new ArrayList<>();
    private ArrayList<Booking> possibleBookings = new ArrayList<>();
    ServableTable(){

    }
    ServableTable(int tableID, int sectionID, int tableNumber, int seats){
        this.tableID = tableID;
        this.sectionID = sectionID;
        this.tableNumber = tableNumber;
        this.seats = seats;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setMaxTimeOfBooking(Time maxTimeOfBooking) {
        this.maxTimeOfBooking = maxTimeOfBooking;
    }

    public void setTimeRequiredAfterBookingIsFinished(Time timeRequiredAfterBookingIsFinished) {
        this.timeRequiredAfterBookingIsFinished = timeRequiredAfterBookingIsFinished;
    }

    public int getTableID() {
        return tableID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getSeats() {
        return seats;
    }

    public String getSectionName() {
        return sectionName;
    }

    public Time getMaxTimeOfBooking() {
        return maxTimeOfBooking;
    }

    public ArrayList<ServableTable> getTableList(int sectionID){
        ArrayList<ServableTable> tableList;
        tableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        return tableList;
    }

    public Time getTimeRequiredAfterBookingIsFinished() {
        return timeRequiredAfterBookingIsFinished;
    }
    public LocalTime getTimeRequiredAfterBookingIsFinishedLocalTime() {
        return timeRequiredAfterBookingIsFinished.toLocalTime();
    }
    public ArrayList<LocalTime> getTimeIncrementsBookedOutForDay() {
        return timeIncrementsBookedOutForDay;
    }

    public ArrayList<Booking> getBookingsOnDay() {
        return bookingsOnDay;
    }

    public void setBookingsOnDay(Booking booking) {
        bookingsOnDay.add(booking);
    }

    public void addTimeIncrement(LocalTime timeIncrement) {
        timeIncrementsBookedOutForDay.add(timeIncrement);
    }

    public void setBookingsOnDay(ArrayList<Booking> bookingsOnDay) {
        System.out.println("Table - " +tableNumber + " Bookings on day: " + bookingsOnDay.size());
        this.bookingsOnDay = bookingsOnDay;
    }
    public void setTimeIncrementsBookedOutForDay() {
        ArrayList<LocalTime> tempTimeIncrementsBookedOutForToday = new ArrayList<>();
        for (Booking booking : bookingsOnDay){
            booking.setTimeRequiredAfterBookingIsFinished(timeRequiredAfterBookingIsFinished.toLocalTime());

            ArrayList<LocalTime> timeIncrementsOfBooking = booking.getTimeIncrementsForBooking();
            System.out.println("Time Increments:");

            for (LocalTime timeIncrementOfBooking: timeIncrementsOfBooking){
                tempTimeIncrementsBookedOutForToday.add(timeIncrementOfBooking);
                System.out.println(timeIncrementOfBooking);
            }

        }
        this.timeIncrementsBookedOutForDay = tempTimeIncrementsBookedOutForToday;

    }

    public ArrayList<Integer> getBookingIDsOnDay() {
        return bookingIDsOnDay;
    }

    public void setBookingIDsOnDay(ArrayList<Integer> bookingIDsOnDay) {
        this.bookingIDsOnDay = bookingIDsOnDay;
    }
    public void addBookingOnDay(Booking booking){
        bookingsOnDay.add(booking);
    }

    public void setTimeIncrementsBookedOutForDay(ArrayList<LocalTime> timeIncrementsBookedOutForDay) {
        this.timeIncrementsBookedOutForDay = timeIncrementsBookedOutForDay;
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

    public void setPossibleBookings(){
        for (LocalTime temp : availableTimeIncrements){
            System.out.println(temp);
        }
        System.out.println();
        LocalTime lengthOfBooking = maxTimeOfBooking.toLocalTime();
        LocalTime timeToReset = timeRequiredAfterBookingIsFinished.toLocalTime();
        int minutes = lengthOfBooking.getHour()*60 + lengthOfBooking.getMinute() + timeToReset.getHour()*60 + timeToReset.getMinute();
        int factorOf15 = minutes/15;
        ArrayList<Booking> possibleBookingsForTable = new ArrayList<>();
        boolean bookingPossible = true;
        LocalTime endTime;

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
                for (LocalTime availableTimeIncrement : availableTimeIncrements){
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
                booking.setTableID(tableID);
                booking.setTableNumber(String.valueOf(tableNumber));
                booking.setHasSingleTable(true);
                possibleBookingsForTable.add(booking);
                System.out.println(bookingTimeIncrements.get(0));
                System.out.println(bookingTimeIncrements.get(bookingTimeIncrements.size()-1));
                System.out.println();

            }
            else {
                System.out.println("Not Possible: ");
                System.out.println(bookingTimeIncrements.get(0));
                System.out.println(endTime);
                System.out.println();
            }


        }
        //add hour zero
        this.possibleBookings = possibleBookingsForTable;
    }

    public void addTimeIncrementsFromJoinedTable(ArrayList<LocalTime> timeIncrementsBookedOutForDayFromJoinedTable) {
        for (LocalTime timeIncrement : timeIncrementsBookedOutForDayFromJoinedTable){
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


    public ArrayList<Booking> getPossibleBookings() {
        return possibleBookings;
    }

    public void setPossibleBookings(ArrayList<Booking> possibleBookings) {
        this.possibleBookings = possibleBookings;
    }
}
