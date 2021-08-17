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

@WebServlet("/resetStartAndEndTimes")
public class ResetStartAndEndTimesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        SectionDatabaseInterface.editStartEndTimes(sectionID,openTimes,closeTimes);



        section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Section.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


