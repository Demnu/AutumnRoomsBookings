package pkg;

import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
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

@WebServlet("/viewAllBookingsFormatted")
public class ViewAllBookingsFormattedController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }
        DayOfWeek dayOfWeek;
        java.sql.Date dateForBookings;
        LocalDate showDate;
        //Check if user has selected a date
        String inputtedDateStr = request.getParameter("inputtedDate");
        if (inputtedDateStr!=null){
            LocalDate inputtedDate = LocalDate.parse(inputtedDateStr);
            dayOfWeek = inputtedDate.getDayOfWeek();

            int inputDay = inputtedDate.getDayOfMonth();
            int inputMonth = inputtedDate.getMonthValue();
            int inputYear = inputtedDate.getYear();
            dateForBookings = Date.valueOf(inputtedDate);
            showDate = LocalDate.of(inputYear,inputMonth,inputDay);
        }
        else{
            dateForBookings = new java.sql.Date(System.currentTimeMillis());
            showDate = LocalDate.now();
            dayOfWeek = showDate.getDayOfWeek();
        }
        //Convert showDate to DD/MM/YYYY
        String showDateStr = showDate.getDayOfMonth() +"/"+showDate.getMonthValue() + "/" + showDate.getYear();
        ArrayList<Booking>bookingsOnDay = new ArrayList<Booking>();
        bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateForBookings);
        System.out.println(bookingsOnDay.size());
        //TODO Development: Add table in database for venue details
        //Get Venues Open time and Close time for today
        Venue venue = VenueDetailsDatabaseInterface.getOpenCloseTimesInputDate(1,dateForBookings.toLocalDate());
        venue.setTimeIncrements();
        LocalTime openTime = venue.getTimeVenueOpens();
        LocalTime closeTime = venue.getTimeVenueCloses();
        //Get time increments for day
        ArrayList<LocalTime> timeIncrements = venue.getTimeIncrements();
        ArrayList<BookingFormattedTimeIncrements> bookingFormattedTimeIncrements = new ArrayList<>();
        //create object to help generating th time increments
        boolean duplicate = false;
        for (int i  = 0 ; i < timeIncrements.size(); i++){
            for (BookingFormattedTimeIncrements timeIncrement : bookingFormattedTimeIncrements){
                if (timeIncrements.get(i).getHour()==timeIncrement.getTimeIncrement().getHour()){
                    duplicate = true;
                    timeIncrement.setColspan(timeIncrement.getColspan()+1);
                }
            }
            if (!duplicate) {
                BookingFormattedTimeIncrements tempTimeIncrement = new BookingFormattedTimeIncrements(timeIncrements.get(i),1);
                bookingFormattedTimeIncrements.add(tempTimeIncrement);
            }
            duplicate = false;
        }

        //Get all tables
        ArrayList<ServableTable> allTables = ServableTableDatabaseInterface.getAllServableTabless();
        ArrayList<JoinedTables> servableJoinedTablesForBooking = JoinedTablesDatabaseInterface.getAllJoinedServeableTables();


        for (ServableTable servableTable: allTables) {
            ArrayList<Booking> bookings = new ArrayList<>();
            for (Booking booking : bookingsOnDay){
                for (ServableTable bookingTable : booking.getAssignedTables()){
                    if (bookingTable.getTableID()==servableTable.getTableID()){
                        booking.setBookingTimesIgnoreTimeToReset();
                        bookings.add(booking);
                        System.out.println("Booking " + booking.getStartTimeOfBookingLocalTime() + " Added to " + servableTable);
                    }
                }
            }
            servableTable.setBookingsOnDay(bookings);
            servableTable.setBookedTimesBookedOutForDay();
            Collections.sort(servableTable.getTimesBookedDuringDay());
        }

        Table table = new Table(allTables, timeIncrements);

        //create object to help generating th 15 minute time increments
        ArrayList<BookingFormattedTimeIncrements> bookingFormattedMinuteTimeIncrements = new ArrayList<>();
        for (LocalTime timeIncrement: timeIncrements){
            BookingFormattedTimeIncrements tempTimeIncrement = new BookingFormattedTimeIncrements();
            tempTimeIncrement.setTimeIncrement(timeIncrement);
            int count = 0;
            ArrayList<Booking> bookingsInColumn = new ArrayList<>();
            for (Booking booking : bookingsOnDay){
                for (BookingTime bookingTimeIncrement : booking.getBookingTimes()){
                    if (bookingTimeIncrement.getTimeIncrement().compareTo(timeIncrement)==0 && !bookingTimeIncrement.isEndTime()){
                        duplicate = false;
                        for (Booking savedBooking : bookingsInColumn){
                            if (savedBooking.getBookingID()==booking.getBookingID()){
                                duplicate = true;
                            }
                        }
                        if (!duplicate){
                            bookingsInColumn.add(booking);
                        }
                    }
                }
            }
            tempTimeIncrement.setBookings(bookingsInColumn);
            tempTimeIncrement.setCovers();
            bookingFormattedMinuteTimeIncrements.add(tempTimeIncrement);
        }

        ArrayList<Section> allSections = SectionDatabaseInterface.getAllSections();

        Functions functions = new Functions();
        request.setAttribute("bookingFormattedTimeIncrements",bookingFormattedTimeIncrements);
        request.setAttribute("bookingFormattedMinuteTimeIncrements",bookingFormattedMinuteTimeIncrements);
        request.setAttribute("dayOfWeek",dayOfWeek.toString());
        request.setAttribute("openTime",openTime);
        request.setAttribute("closeTime",closeTime);
        request.setAttribute("allTables",allTables);
        request.setAttribute("todaysBookingsList",bookingsOnDay);
        request.setAttribute("timeIncrements",timeIncrements);
        request.setAttribute("showDate",showDate);
        request.setAttribute("showDateStr",showDateStr);
        request.setAttribute("functions",functions);
        request.setAttribute("table",table);
        request.setAttribute("sections",allSections);
        request.setAttribute("chosenSections",allSections);


        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ViewAllBookingsFormatted.jsp");
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
        DayOfWeek dayOfWeek;
        java.sql.Date dateForBookings;
        LocalDate showDate;
        //Check if user has selected a date
        String inputtedDateStr = request.getParameter("inputtedDate");
        String chosenSectionIDStr = request.getParameter("sectionID");
        if (inputtedDateStr!=null){
            LocalDate inputtedDate = LocalDate.parse(inputtedDateStr);
            dayOfWeek = inputtedDate.getDayOfWeek();

            int inputDay = inputtedDate.getDayOfMonth();
            int inputMonth = inputtedDate.getMonthValue();
            int inputYear = inputtedDate.getYear();
            dateForBookings = Date.valueOf(inputtedDate);
            showDate = LocalDate.of(inputYear,inputMonth,inputDay);
        }
        else{
            dateForBookings = new java.sql.Date(System.currentTimeMillis());
            showDate = LocalDate.now();
            dayOfWeek = showDate.getDayOfWeek();
        }
        //Convert showDate to DD/MM/YYYY
        String showDateStr = showDate.getDayOfMonth() +"/"+showDate.getMonthValue() + "/" + showDate.getYear();
        ArrayList<Booking>bookingsOnDay = new ArrayList<Booking>();
        bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateForBookings);
        System.out.println(bookingsOnDay.size());
        //TODO Development: Add table in database for venue details
        //Get Venues Open time and Close time for today
        Venue venue = VenueDetailsDatabaseInterface.getOpenCloseTimesInputDate(1,dateForBookings.toLocalDate());
        venue.setTimeIncrements();
        LocalTime openTime = venue.getTimeVenueOpens();
        LocalTime closeTime = venue.getTimeVenueCloses();
        //Get time increments for day
        ArrayList<LocalTime> timeIncrements = venue.getTimeIncrements();
        ArrayList<BookingFormattedTimeIncrements> bookingFormattedTimeIncrements = new ArrayList<>();
        //create object to help generating th  hour time increments
        boolean duplicate = false;
        for (int i  = 0 ; i < timeIncrements.size(); i++){
            for (BookingFormattedTimeIncrements timeIncrement : bookingFormattedTimeIncrements){
                if (timeIncrements.get(i).getHour()==timeIncrement.getTimeIncrement().getHour()){
                    duplicate = true;
                    timeIncrement.setColspan(timeIncrement.getColspan()+1);
                }
            }
            if (!duplicate) {
                BookingFormattedTimeIncrements tempTimeIncrement = new BookingFormattedTimeIncrements(timeIncrements.get(i),1);
                bookingFormattedTimeIncrements.add(tempTimeIncrement);
            }
            duplicate = false;
        }


        //Get all tables
        ArrayList<ServableTable> allTables = ServableTableDatabaseInterface.getAllServableTabless();
        ArrayList<JoinedTables> servableJoinedTablesForBooking = JoinedTablesDatabaseInterface.getAllJoinedServeableTables();


        for (ServableTable servableTable: allTables) {
            ArrayList<Booking> bookings = new ArrayList<>();
            for (Booking booking : bookingsOnDay){
                for (ServableTable bookingTable : booking.getAssignedTables()){
                    if (bookingTable.getTableID()==servableTable.getTableID()){
                        booking.setBookingTimesIgnoreTimeToReset();
                        bookings.add(booking);
                        System.out.println("Booking " + booking.getStartTimeOfBookingLocalTime() + " Added to " + servableTable);
                    }
                }
            }
            servableTable.setBookingsOnDay(bookings);
            servableTable.setBookedTimesBookedOutForDay();
            Collections.sort(servableTable.getTimesBookedDuringDay());
        }

        //create object to help generating th 15 minute time increments
        ArrayList<BookingFormattedTimeIncrements> bookingFormattedMinuteTimeIncrements = new ArrayList<>();
        for (LocalTime timeIncrement: timeIncrements){
            BookingFormattedTimeIncrements tempTimeIncrement = new BookingFormattedTimeIncrements();
            tempTimeIncrement.setTimeIncrement(timeIncrement);
            int count = 0;
            ArrayList<Booking> bookingsInColumn = new ArrayList<>();
            for (Booking booking : bookingsOnDay){
                for (BookingTime bookingTimeIncrement : booking.getBookingTimes()){
                    if (bookingTimeIncrement.getTimeIncrement().compareTo(timeIncrement)==0 && !bookingTimeIncrement.isEndTime()){
                        duplicate = false;
                        for (Booking savedBooking : bookingsInColumn){
                            if (savedBooking.getBookingID()==booking.getBookingID()){
                                duplicate = true;
                            }
                        }
                        if (!duplicate){
                            bookingsInColumn.add(booking);
                        }
                    }
                }
            }
            tempTimeIncrement.setBookings(bookingsInColumn);
            tempTimeIncrement.setCovers();
            bookingFormattedMinuteTimeIncrements.add(tempTimeIncrement);
        }
        ArrayList<Section> allSections = SectionDatabaseInterface.getAllSections();

        //get number of bookings for each section

        for (Section section: allSections){
            int count = 0;
            for(Booking booking: bookingsOnDay){
                if (booking.getSectionID()==section.getSectionID()){
                    count++;
                }
            }
            section.setNumberOfBookingsOnDay(count);
        }
        Table table = new Table(allTables, timeIncrements);
        ArrayList<Section> chosenSections = new ArrayList<>();
        Integer chosenSectionID;
        if (chosenSectionIDStr !=null){
            chosenSectionID = Integer.parseInt(chosenSectionIDStr);
            chosenSections.add(SectionDatabaseInterface.getSectionGivenSectionID(chosenSectionID));
            System.out.println(chosenSections.get(0).getName() + " Found");
        }



        Functions functions = new Functions();
        request.setAttribute("bookingFormattedTimeIncrements",bookingFormattedTimeIncrements);
        request.setAttribute("bookingFormattedMinuteTimeIncrements",bookingFormattedMinuteTimeIncrements);
        request.setAttribute("dayOfWeek",dayOfWeek.toString());
        request.setAttribute("openTime",openTime);
        request.setAttribute("closeTime",closeTime);
        request.setAttribute("allTables",allTables);
        request.setAttribute("todaysBookingsList",bookingsOnDay);
        request.setAttribute("timeIncrements",timeIncrements);
        request.setAttribute("showDate",showDate);
        request.setAttribute("showDateStr",showDateStr);
        request.setAttribute("functions",functions);
        request.setAttribute("table",table);
        request.setAttribute("sections",allSections);
        request.setAttribute("chosenSections",chosenSections);


        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ViewAllBookingsFormatted.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


