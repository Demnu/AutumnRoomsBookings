package pkg;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
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

@WebServlet("/selectTableToBook")
public class SaveBookingController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //tableID Array
        ArrayList tableIDsBooking = new ArrayList();
        //Session attribute set in CheckBookingAvaibilityController: "dateOfBooking", "timeOfBooking","numberOfPeople"
        String tableIDStr = (String) request.getParameter("tableID");
        String sectionIDStr = (String) request.getParameter("sectionID");
        Time startTimeOfBooking = (Time) session.getAttribute("timeOfBooking");
        java.sql.Date dateOfBooking = (java.sql.Date) session.getAttribute("dateOfBooking");
        int numberOfPeople = (int) session.getAttribute("numberOfPeople");
        User signedInUser = (User) session.getAttribute("user");

        //Received by ShowAvailableServableTables.jsp: tableID, sectionID
        Integer tableID = Integer.parseInt(tableIDStr);
        Integer sectionID = Integer.parseInt(sectionIDStr);
        tableIDsBooking.add(tableID);

        //get current date
        java.sql.Date dateBooked = new java.sql.Date(System.currentTimeMillis());

        //get timeOfBooking
        LocalTime timeBookedLocalTime = LocalTime.now();
        Time timeBooked = Time.valueOf(timeBookedLocalTime);
        //get endTimeOfBooking
        LocalTime timeOfBookingLocalTime = (LocalTime) session.getAttribute("timeOfBookingLocalTime");
        //Get maximumTimeOfSection from database
        Time maxTimeOfSectionTime = SectionDatabaseInterface.getMaxTimeOfSection(sectionID);
        //get hours
        long hours = maxTimeOfSectionTime.getHours();
        long minutes = maxTimeOfSectionTime.getMinutes();
        //get duration
        Duration maxTimeOfSectionDuration = Duration.ZERO.plusHours(hours);
        maxTimeOfSectionDuration = Duration.ZERO.plusMinutes(minutes);
        //plus duration to start of booking time
        LocalTime endTimeOfBookingLocalTime = timeOfBookingLocalTime.plus(maxTimeOfSectionDuration);
        //parse end time of booking to Sql Time
        Time endTimeOfBooking = Time.valueOf(endTimeOfBookingLocalTime);
        //TODO Development: Double check that the booking is possible

        //TODO Development: Have bookings with multiple tables
        //TODO Devlopment: Messages for success/fail of saving of booking to database
        //Save booking to database
        if (BookingDatabaseInterface.saveBooking(signedInUser.getStaffID(),dateBooked,timeBooked,dateOfBooking,startTimeOfBooking,endTimeOfBooking,numberOfPeople,true , tableIDsBooking)){
        }
        //remove session attributes: dateOfBooking, timeOfBooking, numberOfPeople
        session.removeAttribute("dateOfBooking");
        session.removeAttribute("timeOfBooking");
        session.removeAttribute("numberOfPeople");

        //authenticate User and create User object
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateBookingStaff.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


