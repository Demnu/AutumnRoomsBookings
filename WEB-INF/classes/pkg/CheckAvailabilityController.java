package pkg;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
            changedDateTimes = ChangedDateDatabaseInterface.getChangedDateTimes(1);
            if (openTimes.get(dayOfWeek).equals(LocalTime.parse("00:01"))){
                venueClosed = true;
                System.out.println("closed");

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
                availableTable.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateTableID(dateOfBooking,availableTable.getTableID()));
                availableTable.setTimeIncrementsBookedOutForDay();
            }
            //Get all bookings for remaining joined tables
            for(JoinedTables joinedTables : servableJoinedTablesForBooking){
                joinedTables.setBookingsOnDay(BookingDatabaseInterface.getBookingsInputtedDateJoinedTablesID(dateOfBooking,joinedTables.getJoinedTablesID()));
                joinedTables.setTimeIncrementsBookedOutForDay();
            }

            //Add booking times in joined tables booking to their associated tables
            ArrayList<ServableTable> tempAvailableTables = new ArrayList();
            tempAvailableTables.addAll(servableTablesForBooking);

            for (ServableTable availableTable: servableTablesForBooking){
                for (JoinedTables availableJoinedTables: servableJoinedTablesForBooking){
                    for (ServableTable joinedTable : availableJoinedTables.getJoinedTablesList()){
                        if (joinedTable.getTableID() == availableTable.getTableID()){
                            System.out.println("Found Joined Table - " + availableJoinedTables + " : Table - " + availableTable.getTableNumber() );
                            ArrayList<LocalTime> timeIncrementsFromSingleTable = new ArrayList<>();
                            timeIncrementsFromSingleTable.addAll(availableTable.getTimeIncrementsBookedOutForDay());
                            System.out.println("Increments added to " + availableTable.getTableNumber());
                            availableTable.addTimeIncrementsFromJoinedTable(availableJoinedTables.getTimeIncrementsBookedOutForDay());

                        }
                    }
                }
            }

            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                for (ServableTable joinedTable : joinedTables.getJoinedTablesList()){
                    for (ServableTable singleTable : servableTablesForBooking){
                        if (joinedTable.getTableID() == singleTable.getTableID()){
                            System.out.println("Found Joined Table - " + joinedTables + " : Table - " + singleTable.getTableNumber() );
                            System.out.println("Increments added to " + joinedTables);
                            joinedTables.addTimeIncrementsFromSingleTable(singleTable.getTimeIncrementsBookedOutForDay());
                        }
                    }

                }
            }
            //not needed
            for (ServableTable servableTable : servableTablesForBooking){
                System.out.println(servableTable.getTableNumber() + " Booked out time increments");
                for (LocalTime timeIncrement : servableTable.getTimeIncrementsBookedOutForDay()){
                    System.out.println(timeIncrement);
                }
            }

            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                System.out.println(joinedTables + " Booked out time increments");
                for (LocalTime timeIncrement : joinedTables.getTimeIncrementsBookedOutForDay()){
                    System.out.println(timeIncrement);
                }
            }
            //

            //Set times available for joined seats and single seats
            venue.setTimeIncrements();
            System.out.println("Removing time increments");
            for (ServableTable servableTable : servableTablesForBooking){
                System.out.println("Table: " + servableTable.getTableNumber());
                servableTable.setAvailableTimeIncrements(venue.getTimeIncrements());
            }
            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                System.out.println("Table: " + joinedTables);
                joinedTables.setAvailableTimeIncrements(venue.getTimeIncrements());
            }
            //Find available bookings for each single and joined table
            for (ServableTable servableTable : servableTablesForBooking){
                System.out.println("Table " + servableTable.getTableNumber() + ": Possible Bookings");
                servableTable.setPossibleBookings();
            }
            for (JoinedTables joinedTables : servableJoinedTablesForBooking){
                System.out.println("JoinedTable " + joinedTables + ": Possible Bookings");
                joinedTables.setPossibleBookings();
            }

            ArrayList<TimeIncrementBooking> timeIncrementBookings = new ArrayList<>();
            ArrayList<TimeIncrementSection> sectionsTimeIncrement = new ArrayList<>();
            ArrayList<LocalTime>timeIncrementsForDay = venue.getTimeIncrements();
            System.out.println(servableTablesForBooking.size() + "-----");


            for (LocalTime timeIncrements : venue.getTimeIncrements()){
                System.out.println("1");
                TimeIncrementBooking timeIncrementBooking = new TimeIncrementBooking();
                timeIncrementBooking.setTimeIncrement(timeIncrements);
                ArrayList<TimeIncrementSection> timeIncrementSections = new ArrayList<>();
                for (Section section :  sections){
                    System.out.println("2");
                    TimeIncrementSection timeIncrementSection = new TimeIncrementSection(section.getSectionID(),section.getName());
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
                timeIncrementBookings.add(timeIncrementBooking);
            }
            request.setAttribute("timeIncrementsForDay",venue.getTimeIncrements());
            request.setAttribute("table",timeIncrementBookings);

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

        }

        request.setAttribute("dateOfBooking",dateOfBooking);
        request.setAttribute("numberOfPeople",numberOfPeople);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/AvailableBookings.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


