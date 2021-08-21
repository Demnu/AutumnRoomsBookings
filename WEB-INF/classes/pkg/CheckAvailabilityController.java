package pkg;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkAvailability")
public class CheckAvailabilityController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateBookingStaff.jsp");
        dispatcher.forward(request, response);
        return;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }
        ArrayList<String> errors = new ArrayList<>();

        String dateOfBookingStr = (String) request.getParameter("dateOfBooking");
        String numberOfPeopleStr = (String) request.getParameter("numberOfPeople");

        LocalDate dateOfBooking = LocalDate.parse(dateOfBookingStr);
        int numberOfPeople = Integer.parseInt(numberOfPeopleStr);

        //Availability Algorithm
        ArrayList<Section> sections = SectionDatabaseInterface.getAllSections();
        ArrayList<ServableTable> servableTablesForBooking = ServableTableDatabaseInterface.getAllServableTabless();
        ArrayList<JoinedTables> servableJoinedTablesForBooking = JoinedTablesDatabaseInterface.getAllJoinedServeableTables();
        int dayOfWeek;
        Venue venue = new Venue();
        ArrayList<LocalTime> openTimes;
        ArrayList<LocalTime> closeTimes;
        ArrayList<ChangedDateTimes> changedDateTimes;
        boolean venueClosed = false;
        boolean inPresentOrPast = false;
        boolean isChangedDate = false;


        //Check if date of booking is before or current current date
        if(dateOfBooking.isBefore(LocalDate.now())||dateOfBooking.compareTo(LocalDate.now())==0){
            errors.add("Cannot book on the day or in the past!");
            System.out.println("past/present");
            inPresentOrPast = true;
        }
        else{
            //Check if Venue is Open
            DayOfWeek dayOfWeekDayOfWeek = dateOfBooking.getDayOfWeek();
            dayOfWeek = dayOfWeekDayOfWeek.getValue()-1;
            venue = VenueDetailsDatabaseInterface.getOpenCloseTimes(1);
            //get open and close time for specific date
            openTimes = venue.getOpenTimes();
            closeTimes = venue.getCloseTimes();
            venue.setTimeVenueOpens(openTimes.get(dayOfWeek));
            venue.setTimeVenueCloses(closeTimes.get(dayOfWeek));
            //set section start end times for dateOfBooking
            for (Section section : sections){
                section.setStartTime(section.getStartTimes().get(dayOfWeek));
                section.setEndTime(section.getEndTimes().get(dayOfWeek));
                section.setTimeIncrements();
                System.out.println(section.getName());
                System.out.println(section.getStartTime());
                System.out.println(section.getEndTime());
            }
            changedDateTimes = ChangedDateDatabaseInterface.getChangedDateTimes(1);
            if (openTimes.get(dayOfWeek).equals(LocalTime.parse("00:01"))){
                venueClosed = true;
                System.out.println("closed");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateBookingStaff.jsp");
                dispatcher.forward(request, response);
                return;

            }
            else{
                for (ChangedDateTimes changedDateTime : changedDateTimes){
                    if (changedDateTime.getChangedDate().compareTo(dateOfBooking)==0){
                        //save Open and Close time for changed date
                        venue.setTimeVenueOpens(changedDateTime.getChangedOpenTime());
                        venue.setTimeVenueCloses(changedDateTime.getChangedCloseTime());
                        isChangedDate = true;
                        if (changedDateTime.getChangedOpenTime().equals(LocalTime.parse("00:01"))){
                            System.out.println("closed special");
                            venueClosed = true;
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateBookingStaff.jsp");
                            dispatcher.forward(request, response);
                            return;
                        }
                    }
                }
            }
        }
        boolean noTables = false;
        int counterRemovedTables = 0;
        int counterRemovedJoinedTables = 0;
        if (!inPresentOrPast && !venueClosed){
            //go through each table and joined table that doesnt have enough seats
            //TODO Development: Make a select input for CreateBookingStaff.jsp that has options up to the table with the most seats
            ArrayList<ServableTable> removedTables = new ArrayList<>();
            ArrayList<JoinedTables> removedJoinedTables = new ArrayList<>();
            for (ServableTable servableTable : servableTablesForBooking){
                if (servableTable.getSeats()<numberOfPeople){
                    removedTables.add(servableTable);
                    counterRemovedTables++;
                }
            }
            servableTablesForBooking.removeAll(removedTables);
            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                if (joinedTables.getNumberSeats()<numberOfPeople){
                    removedJoinedTables.add(joinedTables);
                    counterRemovedJoinedTables++;
                }
            }
            servableJoinedTablesForBooking.removeAll(removedJoinedTables);
            if(servableTablesForBooking.size()==0 && servableJoinedTablesForBooking.size()==0){
                noTables = true;
                System.out.println("No tables with enough seats");
                errors.add("No tables with enough seats");
            }

        }
        else{
            errors.add("Venue is Closed");
        }

        if (!noTables && !inPresentOrPast && !venueClosed) {
            //Get all bookings for remaining single tables
            for (ServableTable availableTable: servableTablesForBooking){
                //BOOKING TIME VERSION
                availableTable.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateTableIDSectionID(dateOfBooking,availableTable.getTableID(),availableTable.getSectionID()));
                availableTable.setBookedTimesBookedOutForDay();

                //OLD VERSION
//                availableTable.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateTableID(dateOfBooking,availableTable.getTableID()));
//                availableTable.setTimeIncrementsBookedOutForDay();
            }
            //Get all bookings for remaining joined tables
            for(JoinedTables joinedTables : servableJoinedTablesForBooking){
                joinedTables.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateJoinedTablesIDSectionID(dateOfBooking,joinedTables.getJoinedTablesID(),joinedTables.getSectionID()));
                joinedTables.setBookedTimesBookedOutForDay();
//
//                joinedTables.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateJoinedTablesID(dateOfBooking,joinedTables.getJoinedTablesID()));
//                joinedTables.setTimeIncrementsBookedOutForDay();
            }

            //Add booking times in joined tables booking to their associated tables
            ArrayList<ServableTable> tempAvailableTables = new ArrayList();
            tempAvailableTables.addAll(servableTablesForBooking);

            for (ServableTable availableTable: servableTablesForBooking){
                for (JoinedTables availableJoinedTables: servableJoinedTablesForBooking){
                    for (ServableTable joinedTable : availableJoinedTables.getJoinedTablesList()){
                        if (joinedTable.getTableID() == availableTable.getTableID()){
                            System.out.println("BookedTime Increments added to " + availableTable.getTableNumber() + " From Joined Table: " + availableJoinedTables.toString());

                            //BOOKING TIME VERSION
                            ArrayList<BookingTime> bookingTimeIncrementsFromSingleTable = new ArrayList<>();
                            bookingTimeIncrementsFromSingleTable.addAll(availableTable.getTimesBookedDuringDay());
                            availableTable.addBookedTimeIncrementsFromJoinedTable(availableJoinedTables.getTimesBookedDuringDay());
                            //OLD VERSION
//                            ArrayList<LocalTime> timeIncrementsFromSingleTable = new ArrayList<>();
//                            timeIncrementsFromSingleTable.addAll(availableTable.getTimeIncrementsBookedOutForDay());
//                            System.out.println("Increments added to " + availableTable.getTableNumber());
//                            availableTable.addTimeIncrementsFromJoinedTable(availableJoinedTables.getTimeIncrementsBookedOutForDay());
                        }
                    }
                }
            }

            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                for (ServableTable joinedTable : joinedTables.getJoinedTablesList()){
                    for (ServableTable singleTable : servableTablesForBooking){
                        if (joinedTable.getTableID() == singleTable.getTableID()){
                            System.out.println("BookedTime Increments added to " + joinedTables.toString() + " From Single Table Table: " + singleTable.getTableNumber());
                            //BOOKING TIME VERSION
                            joinedTables.addBookedTimeIncrementsFromSingleTable(singleTable.getTimesBookedDuringDay());

//
//                            //OLD VERSION
//                            joinedTables.addTimeIncrementsFromSingleTable(singleTable.getTimeIncrementsBookedOutForDay());
                        }
                    }

                }
            }
            //not needed
//            for (ServableTable servableTable : servableTablesForBooking){
//                System.out.println(servableTable.getTableNumber() + " Booked out time increments");
//                for (LocalTime timeIncrement : servableTable.getTimeIncrementsBookedOutForDay()){
//                    System.out.println(timeIncrement);
//                }
//            }
//
//            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
//                System.out.println(joinedTables + " Booked out time increments");
//                for (LocalTime timeIncrement : joinedTables.getTimeIncrementsBookedOutForDay()){
//                    System.out.println(timeIncrement);
//                }
//            }
            //

            //Set times available for joined seats and single seats
            venue.setTimeIncrements();
            for (ServableTable servableTable : servableTablesForBooking){
                System.out.println("All Time Increments for Table: " + servableTable.getTableNumber());
                for (Section section : sections){
                    if (servableTable.getSectionID()==section.getSectionID()){
                        //NEW VERSION
                        servableTable.setAvailableTimeIncrementsBookedTime(section.getTimeIncrements());
//                        //OLD VERSION
//                        servableTable.setAvailableTimeIncrements(section.getTimeIncrements());

                    }
                }
            }
            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                System.out.println("All Time Increments for Tables: " + joinedTables.toString());
                for (Section section : sections){
                    if (joinedTables.getSectionID()==section.getSectionID()){
                        //NEW VERSION
                        joinedTables.setAvailableTimeIncrementsBookedTime(section.getTimeIncrements());
//
//                        //OLD VERSION
//                        joinedTables.setAvailableTimeIncrements(section.getTimeIncrements());
                    }
                }
            }
            //Find available bookings for each single and joined table
            for (ServableTable servableTable : servableTablesForBooking){
                System.out.println("Table " + servableTable.getTableNumber() + ": Possible Bookings");
                //NEW VERSION
                servableTable.setPossibleBookingsBookedTime(numberOfPeople);
//
//                //OLD VERSION
//                servableTable.setPossibleBookings(numberOfPeople);
            }
            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                System.out.println("JoinedTable " + joinedTables + ": Possible Bookings");
                //NEW VERSION
                joinedTables.setPossibleBookingsBookedTime(numberOfPeople);
//
//                //OLD VERSION
//                joinedTables.setPossibleBookings(numberOfPeople);
            }

            ArrayList<TimeIncrementBooking> timeIncrementBookings = new ArrayList<>();
            ArrayList<TimeIncrementSection> sectionsTimeIncrement = new ArrayList<>();
            ArrayList<LocalTime>timeIncrementsForDay = venue.getTimeIncrements();
            System.out.println(servableTablesForBooking.size() + "-----");


            for (LocalTime timeIncrements : venue.getTimeIncrements()){
                System.out.println("1");
                TimeIncrementBooking timeIncrementBooking = new TimeIncrementBooking();
                timeIncrementBooking.setNumberOfPeople(numberOfPeople);
                timeIncrementBooking.setVenueCovers(venue.getMaxCovers());
                timeIncrementBooking.setTimeIncrement(timeIncrements);
                if (timeIncrementBooking.getTimeIncrement().compareTo(venue.getTimeIncrements().get(venue.getTimeIncrements().size()-1))==0){
                    timeIncrementBooking.setClosed(true);
                }
                ArrayList<TimeIncrementSection> timeIncrementSections = new ArrayList<>();
                for (Section section :  sections){
                    System.out.println("2");
                    TimeIncrementSection timeIncrementSection = new TimeIncrementSection(section.getSectionID(),section.getName(),section.getMaxCoversSection(),section.getMaxTimeOfBooking(),timeIncrements);
                    for (ServableTable servableTable : servableTablesForBooking){
                        System.out.println("3");
                        for (Booking booking : servableTable.getPossibleBookings()){
                            System.out.println("4");
                            if (booking.getStartTimeOfBookingLocalTime().compareTo(timeIncrements)==0){
                                System.out.println("5");
                                if (booking.getSectionID()==section.getSectionID()){
                                    System.out.println("6");
                                    timeIncrementSection.addBooking(booking);
                                }
                            }
                        }
                    }
                    for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                        System.out.println("8");
                        for (Booking booking : joinedTables.getPossibleBookings()){
                            System.out.println("9");
                            if (booking.getStartTimeOfBookingLocalTime().compareTo(timeIncrements)==0){
                                System.out.println("10");
                                if (booking.getSectionID()==section.getSectionID()){
                                    System.out.println("11");

                                    timeIncrementSection.addBooking(booking);
                                }
                            }
                        }
                    }
                    timeIncrementSections.add(timeIncrementSection);
                }
                timeIncrementBooking.setSections(timeIncrementSections);
                timeIncrementBooking.setRecommended();
                timeIncrementBookings.add(timeIncrementBooking);


                }
            //add extra closed times before open time
            if (timeIncrementBookings.get(0).getTimeIncrement().getMinute()!=0){
                int minute = timeIncrementBookings.get(0).getTimeIncrement().getMinute();
                int hour = timeIncrementBookings.get(0).getTimeIncrement().getHour();
                LocalTime tempLocalTime = LocalTime.of(hour,0);
                System.out.println("Adding");
                System.out.println(tempLocalTime);

                ArrayList<TimeIncrementSection> sectionList = new ArrayList<>();
                TimeIncrementBooking tempTimeIncrementBooking = new TimeIncrementBooking(tempLocalTime,sectionList);
                tempTimeIncrementBooking.setClosed(true);
                timeIncrementBookings.add(tempTimeIncrementBooking);
                int factorOf15 = minute/15;
                System.out.println(factorOf15);
                for (int i = 0 ; i<factorOf15-1 ; i++){
                    tempLocalTime = tempLocalTime.plusMinutes(15);
                    System.out.println(tempLocalTime);
                    tempTimeIncrementBooking = new TimeIncrementBooking(tempLocalTime,sectionList);
                    tempTimeIncrementBooking.setClosed(true);
                    timeIncrementBookings.add(tempTimeIncrementBooking);
                }
            }
            //add extra closed times after close time
            Collections.sort(timeIncrementBookings);
            if (timeIncrementBookings.get(timeIncrementBookings.size()-1).getTimeIncrement().getMinute()==0){
                ArrayList<TimeIncrementSection> sectionList = new ArrayList<>();
                int minute = timeIncrementBookings.get(timeIncrementBookings.size()-1).getTimeIncrement().getMinute();
                int hour = timeIncrementBookings.get(timeIncrementBookings.size()-1).getTimeIncrement().getHour();
                LocalTime tempLocalTime = LocalTime.of(hour,minute);
                for (int i = 0 ; i<3 ; i++){
                    tempLocalTime = tempLocalTime.plusMinutes(15);
                    System.out.println(tempLocalTime + " added");
                    TimeIncrementBooking tempTimeIncrementBooking = new TimeIncrementBooking(tempLocalTime,sectionList);
                    tempTimeIncrementBooking.setClosed(true);
                    timeIncrementBookings.add(tempTimeIncrementBooking);

                }

            }
            //add covers to TimeIncrementSection
            ArrayList<ServableTable>allTables = ServableTableDatabaseInterface.getAllServableTabless();
            ArrayList<JoinedTables>allJoinedTables = JoinedTablesDatabaseInterface.getAllJoinedServeableTables();
            for (ServableTable availableTable: allTables){
                availableTable.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateTableID(dateOfBooking,availableTable.getTableID()));
                availableTable.setTimeIncrementsBookedOutForDay();
            }
            for(JoinedTables joinedTables : allJoinedTables){
                joinedTables.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateJoinedTablesID(dateOfBooking,joinedTables.getJoinedTablesID()));
                joinedTables.setTimeIncrementsBookedOutForDay();
            }

            for (TimeIncrementBooking timeIncrementBooking : timeIncrementBookings){
                for (TimeIncrementSection timeIncrementSection : timeIncrementBooking.getSections()){
                    for (ServableTable servableTable : allTables){
                        if (servableTable.getSectionID()==timeIncrementSection.getSectionID()){
                            for (Booking booking : servableTable.getBookingsOnDay()){
                                for (LocalTime localTime : booking.getTimeIncrementsForBooking()){
                                    if (localTime.compareTo(timeIncrementBooking.getTimeIncrement())==0){
                                        timeIncrementSection.addAmountCovers(booking.getNumberOfPeople());
                                    }
                                }
                            }
                        }
                    }
                    for (JoinedTables joinedTables : allJoinedTables){
                        if (joinedTables.getSectionID()==timeIncrementSection.getSectionID()){
                            for (Booking booking : joinedTables.getBookingsOnDay()){
                                for (LocalTime localTime : booking.getTimeIncrementsForBooking()){
                                    if (localTime.compareTo(timeIncrementBooking.getTimeIncrement())==0){
                                        timeIncrementSection.addAmountCovers(booking.getNumberOfPeople());
                                    }
                                }
                            }
                        }
                    }
                }
                timeIncrementBooking.setAmountCovers();
            }

            Collections.sort(timeIncrementBookings);
            request.setAttribute("table",timeIncrementBookings);
            }


            request.setAttribute("timeIncrementsForDay",venue.getTimeIncrements());

//            for (TimeIncrementBooking timeIncrementBooking : timeIncrementBookings ){
//                System.out.println(timeIncrementBooking.getTimeIncrement());
//                for (TimeIncrementSection timeIncrementSection : timeIncrementBooking.getSections()){
//                    System.out.println(timeIncrementSection.getSectionName());
//                    for (Booking booking : timeIncrementSection.getBookingsInSectionTimeIncrement()){
//                        if (booking.isHasSingleTable()){
//                            System.out.println("Table: " + booking.getTableNumber() + " Time Start: " + booking.getStartTimeOfBookingLocalTime() + " Time End: " + booking.getEndTimeOfBookingLocalTime());
//                        }
//                        else {
//                            System.out.println("Tables: " + booking.getJoinedTableNumber() + " Time Start: " + booking.getStartTimeOfBookingLocalTime() + " Time End: " + booking.getEndTimeOfBookingLocalTime());
//                        }
//                    }
//                }
//            }


        request.setAttribute("dateOfBooking",dateOfBooking);
        request.setAttribute("numberOfPeople",numberOfPeople);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/AvailableBookings.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


