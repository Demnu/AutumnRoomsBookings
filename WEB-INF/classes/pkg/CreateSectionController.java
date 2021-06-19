package pkg;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Time;
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
        String maxCapacityStr = (String) request.getParameter("maxCapacity");
        String hourMinsStr = (String) request.getParameter("maxTimeOfBooking");
        String timeRequiredAfterBookingIsFinishedStr = (String) request.getParameter("timeRequiredAfterBookingIsFinished");
        Integer maxCapacity = Integer.parseInt(maxCapacityStr);
        Integer maxTimeOfBooking = Integer.parseInt(hourMinsStr);
        Integer timeRequiredAfterBookingIsFinished = Integer.parseInt(timeRequiredAfterBookingIsFinishedStr);
        Time timeRequiredAfterBookingIsFinishedTime = new Time(0,0,0);
        Time tempTime = new Time(0,0,0);
        if(maxTimeOfBooking==1){
            tempTime = new Time(0,15,0);
        }
        else if (maxTimeOfBooking==2){
            tempTime = new Time(0,30,0);
        }
        else if (maxTimeOfBooking==3){
            tempTime = new Time(0,45,0);
        }

        else if (maxTimeOfBooking==4){
            tempTime = new Time(1,0,0);
        }
        else if (maxTimeOfBooking==5){
            tempTime = new Time(1,15,0);
        }
        else if (maxTimeOfBooking==6){
            tempTime = new Time(1,30,0);
        }
        else if (maxTimeOfBooking==7){
            tempTime = new Time(1,45,0);
        }
        else if (maxTimeOfBooking==8){
            tempTime = new Time(2,0,0);
        }

        if(timeRequiredAfterBookingIsFinished==1){
            timeRequiredAfterBookingIsFinishedTime = new Time(0,15,0);
        }
        else if (timeRequiredAfterBookingIsFinished==2){
            timeRequiredAfterBookingIsFinishedTime = new Time(0,30,0);
        }
        else if (timeRequiredAfterBookingIsFinished==3){
            timeRequiredAfterBookingIsFinishedTime = new Time(0,45,0);
        }

        else if (timeRequiredAfterBookingIsFinished==4){
            timeRequiredAfterBookingIsFinishedTime = new Time(1,0,0);
        }
        else if (timeRequiredAfterBookingIsFinished==5){
            timeRequiredAfterBookingIsFinishedTime = new Time(1,15,0);
        }
        else if (timeRequiredAfterBookingIsFinished==6){
            timeRequiredAfterBookingIsFinishedTime = new Time(1,30,0);
        }
        else if (timeRequiredAfterBookingIsFinished==7){
            timeRequiredAfterBookingIsFinishedTime = new Time(1,45,0);
        }
        else if (timeRequiredAfterBookingIsFinished==8){
            timeRequiredAfterBookingIsFinishedTime = new Time(2,0,0);
        }
        else if (timeRequiredAfterBookingIsFinished==9){
            timeRequiredAfterBookingIsFinishedTime = new Time(0,0,0);
        }
        //Authenticate section details and save to database
        if (SectionDatabaseInterface.saveSection(sectionName,sectionDesc,maxCapacity,tempTime, timeRequiredAfterBookingIsFinishedTime)){

        }
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList = SectionDatabaseInterface.getAllSections();
        request.setAttribute("sectionList",sectionList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


