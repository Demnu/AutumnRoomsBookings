package pkg;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/editOpenCloseTimes")
public class EditOpenCloseTimesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }

        Venue openCloseTimes = VenueDetailsDatabaseInterface.getOpenCloseTimes(user.getVenueID());
        openCloseTimes.setChangedDateTimes();
        request.setAttribute("openCloseTimes",openCloseTimes);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/EditOpenCloseTimes.jsp");
        dispatcher.forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        ArrayList<String> errors = new ArrayList<>();
        //Received by ShowTablesFromSection.jsp seatNumber tableID
        String mondayOpenTimeStr = (request.getParameter("MondayOpenTime"));
        String mondayCloseTimeStr = (request.getParameter("MondayCloseTime"));
        String tuesdayOpenTimeStr = (request.getParameter("TuesdayOpenTime"));
        String tuesdayCloseTimeStr = (request.getParameter("TuesdayCloseTime"));
        String wednesdayOpenTimeStr = (request.getParameter("WednesdayOpenTime"));
        String wednesdayCloseTimeStr = (request.getParameter("WednesdayCloseTime"));
        String thursdayOpenTimeStr = (request.getParameter("ThursdayOpenTime"));
        String thursdayCloseTimeStr = (request.getParameter("ThursdayCloseTime"));
        String fridayOpenTimeStr = (request.getParameter("FridayOpenTime"));
        String fridayCloseTimeStr = (request.getParameter("FridayCloseTime"));
        String saturdayOpenTimeStr = (request.getParameter("SaturdayOpenTime"));
        String saturdayCloseTimeStr = (request.getParameter("SaturdayCloseTime"));
        String sundayOpenTimeStr = (request.getParameter("SundayOpenTime"));
        String sundayCloseTimeStr = (request.getParameter("SundayCloseTime"));

            Time mondayOpenTime = Time.valueOf( LocalTime.parse(mondayOpenTimeStr));
            Time mondayCloseTime = Time.valueOf( LocalTime.parse(mondayCloseTimeStr));
            if(mondayCloseTime.before(mondayOpenTime)){
                errors.add("Monday - Close time must be after the open time");
            }
            Time tuesdayOpenTime = Time.valueOf( LocalTime.parse(tuesdayOpenTimeStr));
            Time tuesdayCloseTime = Time.valueOf( LocalTime.parse(tuesdayCloseTimeStr));
            if(tuesdayCloseTime.before(tuesdayOpenTime)){
                errors.add("Tuesday - Close time must be after the open time");
            }
            Time wednesdayOpenTime = Time.valueOf( LocalTime.parse(wednesdayOpenTimeStr));
            Time wednesdayCloseTime = Time.valueOf( LocalTime.parse(wednesdayCloseTimeStr));
            if(wednesdayCloseTime.before(wednesdayOpenTime)){
                errors.add("Wednesday - Close time must be after the open time");
            }
            Time thursdayOpenTime = Time.valueOf( LocalTime.parse(thursdayOpenTimeStr));
            Time thursdayCloseTime = Time.valueOf( LocalTime.parse(thursdayCloseTimeStr));
            if(thursdayCloseTime.before(thursdayOpenTime)){
                errors.add("Thursday - Close time must be after the open time");
            }
            Time fridayOpenTime = Time.valueOf( LocalTime.parse(fridayOpenTimeStr));
            Time fridayCloseTime = Time.valueOf( LocalTime.parse(fridayCloseTimeStr));
            if(fridayCloseTime.before(fridayOpenTime)){
                errors.add("Friday - Close time must be after the open time");
            }
            Time saturdayOpenTime = Time.valueOf( LocalTime.parse(saturdayOpenTimeStr));
            Time saturdayCloseTime = Time.valueOf( LocalTime.parse(saturdayCloseTimeStr));
            if(saturdayCloseTime.before(saturdayOpenTime)){
                errors.add("Saturday - Close time must be after the open time");
            }
            Time sundayOpenTime = Time.valueOf( LocalTime.parse(sundayOpenTimeStr));
            Time sundayCloseTime = Time.valueOf( LocalTime.parse(sundayCloseTimeStr));
            if(sundayCloseTime.before(sundayOpenTime)){
                errors.add("Sunday - Close time must be after the open time");
            }

            if(errors.isEmpty()){
                VenueDetailsDatabaseInterface.editVenueTimes(1,mondayOpenTime,mondayCloseTime,tuesdayOpenTime,tuesdayCloseTime,wednesdayOpenTime,wednesdayCloseTime,thursdayOpenTime,thursdayCloseTime,fridayOpenTime,fridayCloseTime,saturdayOpenTime,saturdayCloseTime,sundayOpenTime,sundayCloseTime);

            }
            else{
                request.setAttribute("errors",errors);
            }


        Venue openCloseTimes = VenueDetailsDatabaseInterface.getOpenCloseTimes(user.getVenueID());
        openCloseTimes.setChangedDateTimes();
        request.setAttribute("openCloseTimes",openCloseTimes);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/EditOpenCloseTimes.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


