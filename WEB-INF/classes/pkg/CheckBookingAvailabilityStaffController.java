package pkg;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

@WebServlet("/checkBookingAvailability")
public class CheckBookingAvailabilityStaffController extends HttpServlet {
    @Override
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


        //Received by CreateBookingStaff.jsp: dateOfBooking, hours, minutes, numberOfPeople
        String staffIDstr = (String) request.getParameter("staffID");
        String dateOfBookingStr = (String) request.getParameter("dateOfBooking");
        String hoursStr = (String) request.getParameter("hours");
        String minutesStr = (String) request.getParameter("minutes");
        String numberOfPeopleStr = (String) request.getParameter("numberOfPeople");
        Integer staffID = Integer.parseInt(staffIDstr);
        //parse dateString into util.Date then into sql.Date
        Date dateOfBookingJava = new Date();
        try {
            final String OLD_FORMAT = "dd-MM-yyyy";
            final String NEW_FORMAT = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            dateOfBookingJava = sdf.parse(dateOfBookingStr);
        }catch (Exception e){System.out.println(e);};
        java.sql.Date dateOfBookingSql = new java.sql.Date(dateOfBookingJava.getTime());

        //parse hours and minutes into time
        Integer hours = Integer.parseInt(hoursStr);
        Integer minutes = Integer.parseInt(minutesStr);
        LocalTime timeOfBookingJava = LocalTime.of(hours,minutes);
        Time timeOfBookingSql = Time.valueOf(timeOfBookingJava);
        Integer numberOfPeople = Integer.parseInt(numberOfPeopleStr);

        ArrayList<Booking> bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateOfBookingSql);
        //TODO Enhancement: getAllServableTablesInBookingOnDateSupplied to have faster retrieval
        //TODO Bug: Once Searched for availability you cannot search for another
        //TODO Bug: Error produced when searching for availability when there is another booking on the same date
        ArrayList<ServableTable> allServableTables = ServableTableDatabaseInterface.getAllServableTablesInBooking();
        ArrayList<ServableTable> availableServableTables = allServableTables;
        for (int i = 0 ; i<availableServableTables.size() ; i++){
            for (int j = 0 ; j<bookingsOnDay.size() ; j++){
                for (int k = 0; k<bookingsOnDay.get(j).getAssignedTables().size(); k++){
                    if (availableServableTables.get(i).getTableID() == bookingsOnDay.get(j).getAssignedTables().get(k).getTableID()){
                        availableServableTables.remove(i);
                    }
                }
            }
        }
        String timeOfBookingStr = (hours + ":" + minutes);
        request.setAttribute("dateOfBooking",dateOfBookingStr);
        request.setAttribute("timeOfBooking",timeOfBookingStr);
        session.setAttribute("timeOfBookingLocalTime", timeOfBookingJava);
        session.setAttribute("dateOfBooking",dateOfBookingSql);
        session.setAttribute("numberOfPeople",numberOfPeople);
        session.setAttribute("timeOfBooking",timeOfBookingSql);
        request.setAttribute("availableServableTables",availableServableTables);
        //authenticate User and create User object
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowAvailableServableTables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


