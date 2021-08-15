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

@WebServlet("/editTable")
public class EditTableController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }

        //Received by SectionHub.jsp seatNumber tableID
        String sectionIDStr = (String) request.getParameter("sectionID");
        String tableIDStr = (request.getParameter("tableID"));
        String seatsNumberStr = (String) request.getParameter("seatsNumber");
        Integer tableID = Integer.parseInt(tableIDStr);
        Integer seatsNumber = Integer.parseInt(seatsNumberStr);
        Integer sectionID = Integer.parseInt(sectionIDStr);


        ServableTableDatabaseInterface.updateSeatsNumber(tableID,seatsNumber);
        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Tables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


