package pkg;

import java.io.IOException;
import java.sql.Date;
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
        java.sql.Date dateForBookings;
        LocalDate showDate;
        //Check if user has selected a date
        String inputtedDateStr = request.getParameter("inputtedDate");
        if (inputtedDateStr!=null){
            LocalDate inputtedDate = LocalDate.parse(inputtedDateStr);

            int inputDay = inputtedDate.getDayOfMonth();
            int inputMonth = inputtedDate.getMonthValue();
            int inputYear = inputtedDate.getYear();
            dateForBookings = Date.valueOf(inputtedDate);
            showDate = LocalDate.of(inputYear,inputMonth,inputDay);
        }
        else{
            dateForBookings = new java.sql.Date(System.currentTimeMillis());
            showDate = LocalDate.now();

        }
        //Convert showDate to DD/MM/YYYY
        String showDateStr = showDate.getDayOfMonth() +"/"+showDate.getMonthValue() + "/" + showDate.getYear();
        ArrayList<Booking>bookingsOnDay = new ArrayList<Booking>();
        bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateForBookings);
        //TODO Development: Add table in database for venue details
        //Get Venues Open time and Close time for today
        LocalTime openTime = LocalTime.of(8,0,0);
        LocalTime closeTime = LocalTime.of(15,0,0);
        Venue venueDetails = new Venue(openTime,closeTime);
        //Get time increments for day
        ArrayList<LocalTime> timeIncrements = venueDetails.getTimeIncrements();

        //Get all tables
        ArrayList<ServableTable> allTables = ServableTableDatabaseInterface.getAllServableTabless();
        ArrayList<Section> allSections = SectionDatabaseInterface.getAllSections();


        //Get all times a table is booked for the day
        ArrayList<Integer> indexsForTablesWithBookings = new ArrayList<Integer>();
        ArrayList<ServableTable> bookedTables;
        for (int i = 0 ; i<bookingsOnDay.size();i++){
            Booking currentBooking = bookingsOnDay.get(i);
            for (int j = 0 ; j<currentBooking.getAssignedTables().size();j++){
                for(int k = 0 ; k<allTables.size();k++){
                    if (allTables.get(k).getTableID()==currentBooking.getAssignedTables().get(j).getTableID()){
                        //Get all time increments for specific booking
                        indexsForTablesWithBookings.add(k);
                        allTables.get(k).setTimeIncrementsBookedOutForDay(currentBooking.getTimeIncrementsForBooking());
                        allTables.get(k).setBookingsOnDay(currentBooking);
                    }
                }
            }
        }
        //Create table for jsp
        Table table = new Table(allTables, timeIncrements);

        Functions functions = new Functions();
        request.setAttribute("openTime",openTime);
        request.setAttribute("closeTime",closeTime);
        request.setAttribute("allTables",allTables);
        request.setAttribute("indexsForTablesWithBookings",indexsForTablesWithBookings);
        request.setAttribute("todaysBookingsList",bookingsOnDay);
        request.setAttribute("timeIncrements",timeIncrements);
        request.setAttribute("showDate",showDate);
        request.setAttribute("showDateStr",showDateStr);
        request.setAttribute("functions",functions);
        request.setAttribute("table",table);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ViewAllBookingsFormatted.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


