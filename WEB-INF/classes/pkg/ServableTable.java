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
    private LocalTime timeAllowedToStayAfterSectionClosed;
    private Time maxTimeOfBooking;
    private String sectionName;
    private Time timeRequiredAfterBookingIsFinished;
    private ArrayList<LocalTime> timeIncrementsBookedOutForDay = new ArrayList<LocalTime>();
    private ArrayList<LocalTime> availableTimeIncrements = new ArrayList<>();
    private ArrayList<Booking> bookingsOnDay = new ArrayList<Booking>();
    private ArrayList<Integer> bookingIDsOnDay = new ArrayList<>();
    private ArrayList<Booking> possibleBookings = new ArrayList<>();
    private ArrayList<BookingTime> allBookedTimeIncrementsForDay = new ArrayList<>();

    private ArrayList<BookingTime> timesBookedDuringDay = new ArrayList<>();

    public ArrayList<Booking> getPossibleBookings() {
        return possibleBookings;
    }

    public void setPossibleBookings(ArrayList<Booking> possibleBookings) {
        this.possibleBookings = possibleBookings;
    }


    public ArrayList<BookingTime> getAllBookedTimeIncrementsForDay() {
        return allBookedTimeIncrementsForDay;
    }

    public void setAllBookedTimeIncrementsForDay(ArrayList<BookingTime> allBookedTimeIncrementsForDay) {
        this.allBookedTimeIncrementsForDay = allBookedTimeIncrementsForDay;
    }

    public void setBookedTimesBookedOutForDay() {
        for (Booking booking : bookingsOnDay){
            for (BookingTime bookingTime : booking.getBookingTimes()){
                boolean saved = false;
                for (BookingTime savedBookingTime : timesBookedDuringDay){
                    if (savedBookingTime.getTimeIncrement().compareTo(bookingTime.getTimeIncrement())==0){
                        saved = true;
                       if (bookingTime.isStartTime()){
                           savedBookingTime.setIsStartTime(true);
                       }
                        if (bookingTime.isEndTime()){
                            savedBookingTime.setIsEndTime(true);
                        }
                        if (bookingTime.isBetween()){
                            savedBookingTime.setBetween(true);
                        }
                    }
                }
                if (!saved){
                    timesBookedDuringDay.add(bookingTime);
                }
            }
        }
        for (BookingTime bookingTime : timesBookedDuringDay){
            System.out.println(bookingTime);
        }
    }
    ServableTable(){

    }

    public ArrayList<BookingTime> getTimesBookedDuringDay() {
        return timesBookedDuringDay;
    }

    public void setTimesBookedDuringDay(ArrayList<BookingTime> timesBookedDuringDay) {
        this.timesBookedDuringDay = timesBookedDuringDay;
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
        if (bookingsOnDay!=null){
            System.out.println("Table - " +tableNumber + " Bookings on day: " + bookingsOnDay.size());
            this.bookingsOnDay = bookingsOnDay;
        }

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
//                    System.out.println("Removed " + bookedTimeIncrement);
                }
            }
        }
        venueTimeIncrements.removeAll(removedVenueTimeIncrements);
        Collections.sort(venueTimeIncrements);
        this.availableTimeIncrements = venueTimeIncrements;
    }

    public void setPossibleBookings(int numberOfPeople){
        for (LocalTime temp : availableTimeIncrements){
//            System.out.println(temp);
        }
//        System.out.println();
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
                booking.setStartTimeOfBookingLocalTime(bookingTimeIncrements.get(0));
                booking.setSectionID(sectionID);
                booking.setTableID(tableID);
                booking.setTableNumber(String.valueOf(tableNumber));
                booking.setHasSingleTable(true);
                booking.setNumberOfSeats(seats);

                possibleBookingsForTable.add(booking);
//                System.out.println(bookingTimeIncrements.get(0));
//                System.out.println(bookingTimeIncrements.get(bookingTimeIncrements.size()-1));
//                System.out.println();

            }
            else {
//                System.out.println("Not Possible: ");
//                System.out.println(bookingTimeIncrements.get(0));
//                System.out.println(endTime);
//                System.out.println();
            }



        }
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
//                System.out.println(timeIncrement);
                timeIncrementsBookedOutForDay.add(timeIncrement);
            }
        }
    }

    public LocalTime getTimeAllowedToStayAfterSectionClosed() {
        return timeAllowedToStayAfterSectionClosed;
    }

    public void setTimeAllowedToStayAfterSectionClosed(LocalTime timeAllowedToStayAfterSectionClosed) {
        this.timeAllowedToStayAfterSectionClosed = timeAllowedToStayAfterSectionClosed;
    }



    public void addBookedTimeIncrementsFromJoinedTable(ArrayList<BookingTime> timeIncrementsBookedOutForDayFromJoinedTable) {
        for (BookingTime bookingTimeFromAnotherTable : timeIncrementsBookedOutForDayFromJoinedTable){
            boolean duplicate = false;
            for (BookingTime savedBookingTime : timesBookedDuringDay){
                if (bookingTimeFromAnotherTable.getTimeIncrement().compareTo(savedBookingTime.getTimeIncrement())==0){
                    duplicate = true;
                    if (bookingTimeFromAnotherTable.isStartTime()){
                        savedBookingTime.setIsStartTime(true);
                    }
                    if (bookingTimeFromAnotherTable.isEndTime()){
                        savedBookingTime.setIsEndTime(true);
                    }
                    if (bookingTimeFromAnotherTable.isBetween()){
                        savedBookingTime.setBetween(true);
                    }
                }
            }
            if (!duplicate){
                timesBookedDuringDay.add(bookingTimeFromAnotherTable);
            }
        }
        Collections.sort(timesBookedDuringDay);
        for (BookingTime bookingTime : timesBookedDuringDay){
            System.out.println(bookingTime);
        }
    }


    public void setAvailableTimeIncrementsBookedTime(ArrayList<LocalTime> dayTimeIncrements) {
        ArrayList<BookingTime> sectionBookedTimeIncrements = new ArrayList<>();

        ArrayList<BookingTime> timesAfterClose = new ArrayList<>();
        LocalTime lengthOfBooking = maxTimeOfBooking.toLocalTime();
        LocalTime timeToReset = timeRequiredAfterBookingIsFinished.toLocalTime();


        //Make section increments into BookingTimeList
        for (LocalTime timeIncrement : dayTimeIncrements){
            BookingTime bookingTime = new BookingTime();
            bookingTime.setTimeIncrement(timeIncrement);
            sectionBookedTimeIncrements.add(bookingTime);
        }

        //Add Times past Close
        LocalTime endTime = dayTimeIncrements.get(dayTimeIncrements.size()-1);
        int minsTimeAllowedAfterClose = timeAllowedToStayAfterSectionClosed.getMinute() + timeAllowedToStayAfterSectionClosed.getHour()*60;
        int factorOf15Close = minsTimeAllowedAfterClose/15;
        LocalTime timePastClose = dayTimeIncrements.get(dayTimeIncrements.size()-1).plusMinutes(minsTimeAllowedAfterClose);
        LocalTime tempLocalTime = endTime;
        for (int i = 0 ; i <factorOf15Close; i++){
            tempLocalTime =  tempLocalTime.plusMinutes(15);
            BookingTime bookingTime = new BookingTime();
            bookingTime.setTimeIncrement(tempLocalTime);
            bookingTime.setAllowedToStayAfterClosed(true);
            sectionBookedTimeIncrements.add(bookingTime);
        }


        for (BookingTime bookingTime : timesBookedDuringDay){
            for (BookingTime sectionBookedTimeIncremeent : sectionBookedTimeIncrements){
                if (bookingTime.compareTo(sectionBookedTimeIncremeent)==0){
                    sectionBookedTimeIncremeent.setIsStartTime(bookingTime.isStartTime());
                    sectionBookedTimeIncremeent.setIsEndTime(bookingTime.isEndTime());
                    sectionBookedTimeIncremeent.setBetween(bookingTime.isBetween());
                    break;
                }
            }
        }

        this.allBookedTimeIncrementsForDay = sectionBookedTimeIncrements;
        for (BookingTime bookingTime : sectionBookedTimeIncrements){
            System.out.println(bookingTime);
        }

    }

    public void setPossibleBookingsBookedTime(int numberOfPeople){
        ArrayList<Booking>possibleBookingTemp = new ArrayList<>();
        LocalTime lengthOfBooking = maxTimeOfBooking.toLocalTime();
        LocalTime timeToReset = timeRequiredAfterBookingIsFinished.toLocalTime();
        int minutes = lengthOfBooking.getHour()*60 + lengthOfBooking.getMinute() + timeToReset.getHour()*60 + timeToReset.getMinute();
        int factorOf15 = minutes/15;
        int incrementsBetweenStartAndEnd = factorOf15 -2;

        for (int i = 0 ; i<allBookedTimeIncrementsForDay.size();i++){
            boolean bookingPossible = true;

            String errors = "";
            ArrayList<BookingTime> possibleBookingTimes = new ArrayList<>();
            BookingTime startOfBooking = allBookedTimeIncrementsForDay.get(i);
            possibleBookingTimes.add(startOfBooking);
            LocalTime endOfBooking = startOfBooking.getTimeIncrement().plusMinutes(minutes);
            LocalTime tempLocalTime = startOfBooking.getTimeIncrement();
            if(allBookedTimeIncrementsForDay.size()>(i+factorOf15)){
                for (int j = 1 ; j<factorOf15; j++){
                    possibleBookingTimes.add(allBookedTimeIncrementsForDay.get(i+j));
                }
                System.out.println(possibleBookingTimes);
                if(possibleBookingTimes.get(0).isStartTime()){
                    bookingPossible = false;
                    errors +="2 ";
                }
                if (possibleBookingTimes.get(possibleBookingTimes.size()-1).isEndTime()){
                    bookingPossible = false;
                    errors +="3 ";
                }
                if (bookingPossible){
                    for (int j = 0 ; j < possibleBookingTimes.size()-1; j++){
                        BookingTime bookingTime = possibleBookingTimes.get(j);
                        if(bookingTime.isStartTime()){
                            bookingPossible = false;
                            errors +="4 ";
                        }

                    }
                }
                if (bookingPossible){
                    for (int j = 1 ; j < possibleBookingTimes.size(); j++){
                        BookingTime bookingTime = possibleBookingTimes.get(j);
                        if(bookingTime.isEndTime()){
                            bookingPossible = false;
                            errors +="5 ";
                        }

                    }
                }
                if (bookingPossible){
                    for (int j = 0 ; j < possibleBookingTimes.size(); j++){
                        BookingTime bookingTime = possibleBookingTimes.get(j);
                        if(bookingTime.isBetween()){
                            bookingPossible = false;
                            errors +="6 ";
                        }

                    }
                }
                if (bookingPossible){
                    System.out.println("Possible");
                    Booking booking = new Booking();
                    LocalTime startTime = possibleBookingTimes.get(0).getTimeIncrement();
                    booking.setStartTimeOfBookingLocalTime(startTime);
                    int mins = lengthOfBooking.getHour()*60 + lengthOfBooking.getMinute();
                    LocalTime endTimeExcludingTimeReqToReset = startTime.plusMinutes(mins);
                    booking.setEndTimeOfBookingLocalTime(endTimeExcludingTimeReqToReset);
                    booking.setStartTimeOfBookingLocalTime(startTime);
                    booking.setSectionID(sectionID);
                    booking.setTableID(tableID);
                    booking.setTableNumber(String.valueOf(tableNumber));
                    booking.setHasSingleTable(true);
                    booking.setNumberOfSeats(seats);
                    possibleBookingTemp.add(booking);
                }
                else {
                    errors +="1 ";
                    System.out.println("Not Possible " + errors);

                }
            }

            else{
                bookingPossible=false;
                errors+=("End time is not in array ");
            }
//            //check possible booking
//            if (possibleBookingTimes.get(0).isStartTime()){
//                bookingPossible=false;
//                errors+=("Start time duplicate ");
//            }
//            if (possibleBookingTimes.get(0).isAllowedToStayAfterClosed()){
//                bookingPossible=false;
//                errors+=("Start time is after close ");
//            }
//            if (possibleBookingTimes.get(0).isBetween()){
//                bookingPossible=false;
//                errors+=("Start time during another Booking ");
//            }
//            if (possibleBookingTimes.get(possibleBookingTimes.size()-1).isEndTime()){
//                bookingPossible=false;
//                errors+=("End time duplicate ");
//            }
//            if (possibleBookingTimes.get(possibleBookingTimes.size()-1).isBetween()){
//                bookingPossible=false;
//                errors+=("End time during another booking");
//            }
//            if (bookingPossible){
//                //go through inbetween times
//                for (int j = 1 ; j<incrementsBetweenStartAndEnd;j++){
//                    if (possibleBookingTimes.get(j).isStartTime() || possibleBookingTimes.get(j).isEndTime() || possibleBookingTimes.get(j).isBetween()){
//                        errors+=(possibleBookingTimes.get(j) + " Did not pass");
//                        bookingPossible = false;
//                    }
//                }
//            }
//            //saveBooking
//            if (bookingPossible){
//                System.out.println(possibleBookingTimes + " is Possible");
//            }
//            else{
//                System.out.println(possibleBookingTimes + " not Possible");
//                System.out.println(errors);
//                System.out.println();
//
//            }
        }
        this.possibleBookings = possibleBookingTemp;
    }

}
