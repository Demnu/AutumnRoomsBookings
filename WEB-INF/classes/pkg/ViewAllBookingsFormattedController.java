package pkg;

import java.io.IOException;
import java.sql.Date;
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
        //Get bookings for today
        java.sql.Date dateBooked = new java.sql.Date(System.currentTimeMillis());
        ArrayList<Booking>bookingsOnDay = new ArrayList<Booking>();
        bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateBooked);
        //TODO Development: Add table in database for venue details
        //Get Venues Open time and Close time for today
        LocalTime openTime = LocalTime.of(8,0,0);
        LocalTime closeTime = LocalTime.of(15,0,0);
        VenueDetails venueDetails = new VenueDetails(openTime,closeTime);
        //Get time increments for day
        ArrayList<LocalTime> timeIncrements = venueDetails.getTimeIncrements();

        //Get all tables
        ArrayList<ServableTable> allTables = ServableTableDatabaseInterface.getAllServableTabless();


        //Get all times a table is booked for the day
        ArrayList<Integer> indexsForTablesWithBookings = new ArrayList<Integer>();
        ArrayList<ServableTable> bookedTables;
        for (int i = 0 ; i<bookingsOnDay.size();i++){
            Booking currentBooking = bookingsOnDay.get(i);
            for (int j = 0 ; j<currentBooking.getAssignedTables().size();j++){
                for(int k = 0 ; k<allTables.size();k++){
                    if (allTables.get(k).getTableID()==currentBooking.getAssignedTables().get(j).getTableID()){
                        //Get all time increments for specific booking
                        indexsForTablesWithBookings.add(i);
                        allTables.get(k).setTimeIncrementsBookedOutForDay(currentBooking.getTimeIncrementsForBooking());
                        allTables.get(k).setBookingsOnDay(currentBooking);
                    }
                }
            }
        }
        request.setAttribute("openTime",openTime);
        request.setAttribute("closeTime",closeTime);
        request.setAttribute("allTables",allTables);
        request.setAttribute("indexsForTablesWithBookings",indexsForTablesWithBookings);
        request.setAttribute("todaysBookingsList",bookingsOnDay);
        request.setAttribute("timeIncrements",timeIncrements);


        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ViewAllBookingsFormatted.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


