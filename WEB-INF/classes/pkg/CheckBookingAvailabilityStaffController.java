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
        LocalTime timeOfBookingLocalTime = LocalTime.of(hours,minutes);
        Time startTimeOfBookingSql = Time.valueOf(timeOfBookingLocalTime);
        Integer numberOfPeople = Integer.parseInt(numberOfPeopleStr);
        ArrayList<Booking> bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateOfBookingSql);
        ArrayList<ServableTable> allServableTables = ServableTableDatabaseInterface.getAllServableTablesInBooking();
        ArrayList<ServableTable> availableServableTables = allServableTables;

        //getEndTimeOfBooking

        //get

        for (int i = 0 ; i<availableServableTables.size() ; i++){
            for (int j = 0 ; j<bookingsOnDay.size() ; j++){
                for (int k = 0; k<bookingsOnDay.get(j).getAssignedTables().size(); k++){
                    if (availableServableTables.get(i).getTableID() == bookingsOnDay.get(j).getAssignedTables().get(k).getTableID()){
                        System.out.println(availableServableTables.get(i).getTableNumber() + " Conflicts");
                        //check if desired booking time conflicts with table
                            //TODO Enhancement: Add function to ServableTable class to automatically add maxTimeOfSection and requiredTimeToSetUpAfterBooking
                            //Get hours and minutes of maxTimeOfSectionTime
                            Time maxTimeOfSectionTime = availableServableTables.get(i).getMaxTimeOfBooking();
                            int maxTimeOfSectionHours = maxTimeOfSectionTime.getHours();
                            int maxTimeOfSectionMinutes = maxTimeOfSectionTime.getMinutes();
                            //Get hours and minutes of timeRequiredAfterBookingIsFinished
                            Time timeRequiredAfterBookingIsFinished = availableServableTables.get(i).getTimeRequiredAfterBookingIsFinished();
                            int timeRequiredAfterBookingIsFinishedHours = timeRequiredAfterBookingIsFinished.getHours();
                            int timeRequiredAfterBookingIsFinishedMinutes = timeRequiredAfterBookingIsFinished.getMinutes();
                            //Create duration
                            Duration maxTimeOfSectionDuration;
                            maxTimeOfSectionDuration= Duration.ZERO.plusHours(maxTimeOfSectionHours);
                            maxTimeOfSectionDuration = Duration.ZERO.plusMinutes(maxTimeOfSectionMinutes);
                            Duration timeRequiredAfterBookingIsFinishedDuration;
                            timeRequiredAfterBookingIsFinishedDuration = Duration.ZERO.plusHours(timeRequiredAfterBookingIsFinishedHours);
                            timeRequiredAfterBookingIsFinishedDuration = Duration.ZERO.plusMinutes(timeRequiredAfterBookingIsFinishedMinutes);
                            //Plus duration onto desired booking start time
                            Duration totalDuration = timeRequiredAfterBookingIsFinishedDuration.plus(maxTimeOfSectionDuration);
                            LocalTime totalTime = timeOfBookingLocalTime.plus(totalDuration);
                            System.out.println(totalTime);
                        //Parse totalTime to Sql Time
                            Time endTimeOfBooking = Time.valueOf(totalTime);
                        //working on this
                        if (!endTimeOfBooking.before(bookingsOnDay.get(j).getStartTimeOfBooking()) && !endTimeOfBooking.after(bookingsOnDay.get(j).getStartTimeOfBooking())){
                            availableServableTables.remove(i);
                            System.out.println("End time of booking is during another booking \n" + "End Time: " + endTimeOfBooking);

                        }
                        else if (startTimeOfBookingSql.after(bookingsOnDay.get(j).getStartTimeOfBooking()) && startTimeOfBookingSql.before(bookingsOnDay.get(j).getEndTimeOfBooking())){
                            availableServableTables.remove(i);
                            System.out.println("start time is between another booking");

                        }
                    }
                }
            }
        }
        String timeOfBookingStr = (hours + ":" + minutes);
        request.setAttribute("dateOfBooking",dateOfBookingStr);
        request.setAttribute("timeOfBooking",timeOfBookingStr);
        session.setAttribute("timeOfBookingLocalTime", timeOfBookingLocalTime);
        session.setAttribute("dateOfBooking",dateOfBookingSql);
        session.setAttribute("numberOfPeople",numberOfPeople);
        session.setAttribute("timeOfBooking",startTimeOfBookingSql);
        request.setAttribute("availableServableTables",availableServableTables);
        //authenticate User and create User object
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowAvailableServableTables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


