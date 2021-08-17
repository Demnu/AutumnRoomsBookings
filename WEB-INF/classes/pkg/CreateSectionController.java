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

@WebServlet("/createSection")
public class CreateSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            System.out.println("not logged in");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }

        //Forward the Section List to CreateSection.jsp
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList = SectionDatabaseInterface.getAllSections();
        request.setAttribute("sectionList",sectionList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateSection.jsp");
        dispatcher.forward(request, response);
        return;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Received by CreateSection.jsp: Section Name and Brief Description
        String sectionName = (String) request.getParameter("sectionName");
        String sectionDesc = (String) request.getParameter("sectionDesc");
        String timeConstrainedStr = (String) request.getParameter("timeConstrained");
        String maxCoversSectionStr = (String) request.getParameter("maxCoversSection");
        String hourMinsStr = (String) request.getParameter("maxTimeOfBooking");
        String timeRequiredAfterBookingIsFinishedStr = (String) request.getParameter("timeRequiredAfterBookingIsFinished");
        Integer maxCoversSection = Integer.parseInt(maxCoversSectionStr);
        LocalTime maxTimeOfBooking = LocalTime.parse(hourMinsStr);
        LocalTime timeRequiredAfterBookingIsFinished = LocalTime.parse(timeRequiredAfterBookingIsFinishedStr);
        boolean timeConstrained = false;
        if (timeConstrainedStr!=null){
            timeConstrained = true;
        }



        //Authenticate section details and save to database
        if (SectionDatabaseInterface.saveSection(sectionName,sectionDesc,maxCoversSection,maxTimeOfBooking, timeRequiredAfterBookingIsFinished,timeConstrained)){

        }
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList = SectionDatabaseInterface.getAllSections();
        request.setAttribute("sectionList",sectionList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/SectionHub.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


