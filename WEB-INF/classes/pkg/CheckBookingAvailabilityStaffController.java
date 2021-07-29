package pkg;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
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
        LocalDate dateOfBookingLocalDate;
        java.sql.Date dateOfBookingSql = new java.sql.Date(0,0,0);
        try{
            dateOfBookingLocalDate = LocalDate.parse(dateOfBookingStr);
            dateOfBookingSql = java.sql.Date.valueOf(dateOfBookingLocalDate);

        }catch (Exception e){
            System.out.println(e);
        }
        //parse hours and minutes into time
        Integer hours = Integer.parseInt(hoursStr);
        Integer minutes = Integer.parseInt(minutesStr);
        LocalTime timeOfBookingLocalTime = LocalTime.of(hours,minutes);
        Time startTimeOfBookingSql = Time.valueOf(timeOfBookingLocalTime);
        Integer numberOfPeople = Integer.parseInt(numberOfPeopleStr);
        ArrayList<Booking> bookingsOnDay = BookingDatabaseInterface.getAllBookingsInputtedDate(dateOfBookingSql);
        ArrayList<ServableTable> allServableTables = ServableTableDatabaseInterface.getAllServableTabless();
        ArrayList<ServableTable> availableServableTables = allServableTables;

        //getEndTimeOfBooking

        //get

        for (int i = 0 ; i<availableServableTables.size() ; i++){
            for (int j = 0 ; j<bookingsOnDay.size() ; j++){
                for (int k = 0; k<bookingsOnDay.get(j).getAssignedTables().size(); k++){
                    if (availableServableTables.get(i).getTableID() == bookingsOnDay.get(j).getAssignedTables().get(k).getTableID()){
                        //check if desired booking time conflicts with table
                            //TODO Enhancement: Add function to ServableTable class to automatically add maxTimeOfSection and requiredTimeToSetUpAfterBooking
                            //Get LocalTime of maxTimeBooking
                            Time maxTimeOfSectionTime = availableServableTables.get(i).getMaxTimeOfBooking();
                            int maxTimeOfSectionHours = maxTimeOfSectionTime.getHours();
                            int maxTimeOfSectionMinutes = maxTimeOfSectionTime.getMinutes();
                            LocalTime maxTimeOfSectionLocalTime = LocalTime.of(maxTimeOfSectionHours,maxTimeOfSectionMinutes,0);
                            //Get hours and minutes of timeRequiredAfterBookingIsFinished into Duration
                            Time timeRequiredAfterBookingIsFinished = availableServableTables.get(i).getTimeRequiredAfterBookingIsFinished();
                            int timeRequiredAfterBookingIsFinishedHours = timeRequiredAfterBookingIsFinished.getHours();
                            int timeRequiredAfterBookingIsFinishedMinutes = timeRequiredAfterBookingIsFinished.getMinutes();
                            Duration timeRequiredAfterBookingIsFinishedDuration = Duration.ZERO.plusHours(timeRequiredAfterBookingIsFinishedHours);
                            timeRequiredAfterBookingIsFinishedDuration = Duration.ZERO.plusMinutes(timeRequiredAfterBookingIsFinishedMinutes);
                            //Plus timeRequiredAfterBookingDuration and maxTimeOfSection
                            LocalTime totalTimeDurationLocalTime = maxTimeOfSectionLocalTime.plus(timeRequiredAfterBookingIsFinishedDuration);
                            //Get hours and minutes of totalTimeDuration into Duration
                            int totalTimeDurationHours = totalTimeDurationLocalTime.getHour();
                            int totalTimeDurationMinutes = totalTimeDurationLocalTime.getMinute();
                            Duration totalTimeDuration = Duration.ZERO.plusHours(totalTimeDurationHours);
                            totalTimeDuration = Duration.ZERO.plusMinutes(totalTimeDurationMinutes);
                            //Plus duration onto desired booking start time
                            LocalTime totalTime = timeOfBookingLocalTime.plus(totalTimeDuration);
                            //Parse totalTime to Sql Time
                            Time endTimeOfBooking = Time.valueOf(totalTime);

                        //get tempBooking endTimePlus timeRequiredAfterBookingIsFinished
                        Booking tempBooking = bookingsOnDay.get(j);
                        Time tempBookingEndTime = tempBooking.getEndTimeOfBooking();
                        Time tempBookingTimeRequiredAfterBookingIsFinished = tempBooking.getAssignedTables().get(0).getTimeRequiredAfterBookingIsFinished();
                        //Get hours and minutes
                        int tempBookingEndTimeHours = tempBookingEndTime.getHours();
                        int tempBookingEndTimeMinutes = tempBookingEndTime.getMinutes();
                        int tempBookingTimeRequiredAfterBookingIsFinishedHours = tempBookingTimeRequiredAfterBookingIsFinished.getHours();
                        int tempBookingTimeRequiredAfterBookingIsFinishedMinutes = tempBookingTimeRequiredAfterBookingIsFinished.getMinutes();
                        //Get duration of timeRequiredAfterBookingIsFinished
                        Duration tempDuration1 = Duration.ZERO.plusHours(tempBookingTimeRequiredAfterBookingIsFinishedHours);
                        tempDuration1 = Duration.ZERO.plusMinutes(tempBookingTimeRequiredAfterBookingIsFinishedMinutes);
                        //Create LocalTime
                        LocalTime endTimeTempBooking = LocalTime.of(tempBookingEndTimeHours,tempBookingEndTimeMinutes,0);
                        //add timeRequiredAfterBookingIsFinishedHours to endTimeOfBooking
                        LocalTime totalEndTimeTempBookingLocalTime = endTimeTempBooking.plus(tempDuration1);
                        //Parse LocalTime to Sql Time
                        Time totalEndTimeTempBooking = Time.valueOf(totalEndTimeTempBookingLocalTime);


                        if (startTimeOfBookingSql.equals(tempBooking.getStartTimeOfBooking())){
                            availableServableTables.remove(i);
                            System.out.println("Start time of booking is at the same start as another booking");
                        }
                        else if (startTimeOfBookingSql.equals(tempBooking.getEndTimeOfBooking())){
                            availableServableTables.remove(i);
                            System.out.println("Start time of booking is at the end time of another booking");

                        }

                        else if(startTimeOfBookingSql.before(tempBooking.getStartTimeOfBooking())){
                                if(endTimeOfBooking.after(tempBooking.getStartTimeOfBooking())&&endTimeOfBooking.before(totalEndTimeTempBooking)){
                                    System.out.println("End time of booking is during another booking");
                                    availableServableTables.remove(i);
                                }
                        }
                        else if(startTimeOfBookingSql.after(tempBooking.getStartTimeOfBooking()) && startTimeOfBookingSql.before(totalEndTimeTempBooking)){
                            System.out.println("Start time of booking is during another booking");
                            availableServableTables.remove(i);
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


