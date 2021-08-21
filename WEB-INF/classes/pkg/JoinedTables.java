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

    private ArrayList<BookingTime> allBookedTimeIncrementsForDay = new ArrayList<>();
    private LocalTime timeAllowedToStayAfterSectionClosed;
    private ArrayList<BookingTime> timesBookedDuringDay = new ArrayList<>();

    public ArrayList<BookingTime> getAllBookedTimeIncrementsForDay() {
        return allBookedTimeIncrementsForDay;
    }

    public void setAllBookedTimeIncrementsForDay(ArrayList<BookingTime> allBookedTimeIncrementsForDay) {
        this.allBookedTimeIncrementsForDay = allBookedTimeIncrementsForDay;
    }

    public ArrayList<BookingTime> getTimesBookedDuringDay() {
        return timesBookedDuringDay;
    }

    public void setTimesBookedDuringDay(ArrayList<BookingTime> timesBookedDuringDay) {
        this.timesBookedDuringDay = timesBookedDuringDay;
    }

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
            System.out.println("Joined Table - " + toString() + " Bookings on Day: " + bookingsOnDay.size());

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
//            System.out.println(timeIncrement);
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
//                    System.out.println("Removed " + bookedTimeIncrement);
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
//                System.out.println(timeIncrement);
                timeIncrementsBookedOutForDay.add(timeIncrement);
            }
        }
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
                booking.setSectionID(sectionID);
                booking.setJoinedTablesID(joinedTablesID);
                booking.setTableNumber(toString());
                booking.setHasSingleTable(false);
                booking.setNumberOfSeats(numberSeats);
                possibleBookingsForTable.add(booking);
//                System.out.println(bookingTimeIncrements.get(0));
//                System.out.println(bookingTimeIncrements.get(bookingTimeIncrements.size()-1));
//                System.out.println();

            }
            else {

//                System.out.println(endTime);
//                System.out.println();
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
        System.out.println("Time Increments:");
        for (BookingTime bookingTime : timesBookedDuringDay){
                System.out.println(bookingTime);
        }
    }

    public void addBookedTimeIncrementsFromSingleTable(ArrayList<BookingTime> timeIncrementsBookedOutForDayFromJoinedTable) {
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
                    booking.setJoinedTablesID(joinedTablesID);
                    booking.setTableNumber(toString());
                    booking.setHasSingleTable(false);
                    booking.setNumberOfSeats(numberSeats);
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
