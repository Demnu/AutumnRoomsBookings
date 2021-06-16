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

@WebServlet("/editSingleSection")
public class EditSingleSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Received by CreateSection.jsp Edit Button
        String sectionIDStr = (request.getParameter("editSingleSectionID"));
        String maxCapacityStr = (String) request.getParameter("maxCapacity");
        String hourMinsStr = (String) request.getParameter("maxTimeOfBooking");
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Integer maxCapacity = Integer.parseInt(maxCapacityStr);
        Integer maxTimeOfBooking = Integer.parseInt(hourMinsStr);
        boolean maxTimeOfBookingChanged = true;

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
        //if maxTimeOfBooking is unchanged
        else{            maxTimeOfBookingChanged = false;
        }

        //if maxTimeOfBooking has been changed update database
        if (maxTimeOfBookingChanged){
            SectionDatabaseInterface.updateMaxTimeOfBooking(sectionID,tempTime);
        }
        SectionDatabaseInterface.updateMaxCapacity(sectionID,maxCapacity);
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList = SectionDatabaseInterface.getAllSections();
        request.setAttribute("sectionList",sectionList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


