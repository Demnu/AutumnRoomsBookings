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
        System.out.println("test");

        //Received by Section.jsp Edit Button
        String sectionIDStr = (request.getParameter("sectionID"));
        String maxCoversSectionStr = (String) request.getParameter("maxCoversSection");
        String maxTimeOfBookingStr = (String) request.getParameter("maxTimeOfBooking");
        String timeRequiredAfterBookingIsFinishedStr = (String) request.getParameter("timeRequiredAfterBookingIsFinished");
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Integer maxCoversSection = Integer.parseInt(maxCoversSectionStr);
        boolean maxTimeOfBookingChanged = true;

        LocalTime maxTimeOfBooking = LocalTime.parse(maxTimeOfBookingStr);
        SectionDatabaseInterface.updateMaxTimeOfBooking(sectionID,Time.valueOf(maxTimeOfBooking));

        LocalTime timeRequiredAfterBooking = LocalTime.parse(timeRequiredAfterBookingIsFinishedStr);
        SectionDatabaseInterface.updateTimeRequiredAfterBookingIsFinished(sectionID,Time.valueOf(timeRequiredAfterBooking));

        SectionDatabaseInterface.updateMaxCoversSection(sectionID,maxCoversSection);

        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Section.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


