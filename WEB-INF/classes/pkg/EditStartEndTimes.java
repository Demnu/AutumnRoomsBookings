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

@WebServlet("/editStartEndTimes")
public class EditStartEndTimes extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        String sectionIDStr = (request.getParameter("sectionID"));
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        Venue venue = VenueDetailsDatabaseInterface.getOpenCloseTimes(1);
        ArrayList<LocalTime> openTimes = venue.getOpenTimes();
        ArrayList<LocalTime> closeTimes = venue.getCloseTimes();
        ArrayList<String> days = Functions.getDayNames();

        ArrayList<String> errors = new ArrayList<>();
        //Received by SectionHub.jsp seatNumber tableID
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
        int counter = 0;
        ArrayList<LocalTime> startTimes = new ArrayList<>();
        ArrayList<LocalTime> endTimes = new ArrayList<>();

        LocalTime mondayOpenTime = LocalTime.parse(mondayOpenTimeStr);
        LocalTime mondayCloseTime = LocalTime.parse(mondayCloseTimeStr);
        startTimes.add(mondayOpenTime);
        endTimes.add(mondayCloseTime);

        LocalTime tuesdayOpenTime = LocalTime.parse(tuesdayOpenTimeStr);
        LocalTime tuesdayCloseTime = LocalTime.parse(tuesdayCloseTimeStr);
        startTimes.add(tuesdayOpenTime);
        endTimes.add(tuesdayCloseTime);

        LocalTime wednesdayOpenTime = LocalTime.parse(wednesdayOpenTimeStr);
        LocalTime wednesdayCloseTime = LocalTime.parse(wednesdayCloseTimeStr);
        startTimes.add(wednesdayOpenTime);
        endTimes.add(wednesdayCloseTime);

        LocalTime thursdayOpenTime = LocalTime.parse(thursdayOpenTimeStr);
        LocalTime thursdayCloseTime = LocalTime.parse(thursdayCloseTimeStr);
        startTimes.add(thursdayOpenTime);
        endTimes.add(thursdayCloseTime);

        LocalTime fridayOpenTime = LocalTime.parse(fridayOpenTimeStr);
        LocalTime fridayCloseTime = LocalTime.parse(fridayCloseTimeStr);
        startTimes.add(fridayOpenTime);
        endTimes.add(fridayCloseTime);

        LocalTime saturdayOpenTime = LocalTime.parse(saturdayOpenTimeStr);
        LocalTime saturdayCloseTime = LocalTime.parse(saturdayCloseTimeStr);
        startTimes.add(saturdayOpenTime);
        endTimes.add(saturdayCloseTime);

        LocalTime sundayOpenTime = LocalTime.parse(sundayOpenTimeStr);
        LocalTime sundayCloseTime = LocalTime.parse(sundayCloseTimeStr);
        startTimes.add(sundayOpenTime);
        endTimes.add(sundayCloseTime);
        int j = 0;

        ArrayList<Time> startTimesTime = new ArrayList<>();
        ArrayList<Time> endTimesTime = new ArrayList<>();


        for (int i = 0 ; i <startTimes.size(); i++){
            System.out.println("started");
            if (startTimes.get(i).equals(LocalTime.parse("00:01")) ){
                LocalTime tempTime = LocalTime.parse("00:01");
                startTimesTime.add(Time.valueOf(tempTime));
                endTimesTime.add(Time.valueOf(tempTime));
            }
            else{
                if(startTimes.get(i).isBefore(openTimes.get(i))){
                    errors.add(days.get(j) +" - Start time must be after the open time");
                }
                if (startTimes.get(i).isAfter(closeTimes.get(i))){
                    errors.add(days.get(j) +" - Start time must be before the close time");
                }
                if(startTimes.get(i).isAfter(endTimes.get(i))){
                    errors.add(days.get(j) +" - Start time must be before the end time  of the section");
                }
                if(endTimes.get(i).isBefore(startTimes.get(i))){
                    errors.add(days.get(j) +" - End time must be after the start time  of the section");
                }
                if (endTimes.get(i).isBefore(openTimes.get(i))){
                    errors.add(days.get(j) +" - End time must be after the open time");
                }
                if (endTimes.get(i).isAfter(closeTimes.get(i))){
                    errors.add(days.get(j) +" - End time must be before the close time");
                }

                startTimesTime.add(Time.valueOf(startTimes.get(i)));
                endTimesTime.add(Time.valueOf(endTimes.get(i)));
            }
            j++;
        }

        if(errors.isEmpty()){
            SectionDatabaseInterface.editStartEndTimesUsedByEditOpenCloseTimes(sectionID, startTimesTime, endTimesTime);

        }
        else{
            request.setAttribute("errors",errors);
        }

        section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Section.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


