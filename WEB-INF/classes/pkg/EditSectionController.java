package pkg;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Date;
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

@WebServlet("/editSection")
public class EditSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Received by Section.jsp Edit Button
        String sectionIDStr = (request.getParameter("sectionID"));
        String maxCoversSectionStr = (String) request.getParameter("maxCoversSection");
        String maxTimeOfBookingStr = (String) request.getParameter("maxTimeOfBooking");
        String timeConstrainedStr = (String) request.getParameter("timeConstrained");
        String timeAllowedToStayAfterSectionIsClosedStr = (String) request.getParameter("timeAllowedToStayAfterSectionIsClosed");
        String timeRequiredAfterBookingIsFinishedStr = (String) request.getParameter("timeRequiredAfterBookingIsFinished");
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Integer maxCoversSection = Integer.parseInt(maxCoversSectionStr);
        boolean timeConstrained = false;
        if (timeConstrainedStr!=null){
            timeConstrained = true;

        }

        LocalTime timeAllowedToStayAfterSectionIsClosed = LocalTime.parse(timeAllowedToStayAfterSectionIsClosedStr);
        SectionDatabaseInterface.updateTimeAllowedToStayAfterSectionIsClosed(sectionID,timeAllowedToStayAfterSectionIsClosed);


        LocalTime maxTimeOfBooking = LocalTime.parse(maxTimeOfBookingStr);
        SectionDatabaseInterface.updateMaxTimeOfBooking(sectionID,Time.valueOf(maxTimeOfBooking));

        LocalTime timeRequiredAfterBooking = LocalTime.parse(timeRequiredAfterBookingIsFinishedStr);
        SectionDatabaseInterface.updateTimeRequiredAfterBookingIsFinished(sectionID,Time.valueOf(timeRequiredAfterBooking));

        SectionDatabaseInterface.updateTimeConstrained(sectionID, timeConstrained);
        SectionDatabaseInterface.updateMaxCoversSection(sectionID,maxCoversSection);

        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Section.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


