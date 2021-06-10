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

@WebServlet("/editSingleTable")
public class EditSingleTableController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Received by ShowTablesFromSection.jsp seatNumber tableID
        String sectionIDStr = (String) request.getParameter("sectionID");
        String tableIDStr = (request.getParameter("tableID"));
        String seatsNumberStr = (String) request.getParameter("seatsNumber");
        Integer tableID = Integer.parseInt(tableIDStr);
        Integer seatsNumber = Integer.parseInt(seatsNumberStr);
        Integer sectionID = Integer.parseInt(sectionIDStr);


        ServableTableDatabaseInterface.updateSeatsNumber(tableID,seatsNumber);
        String sectionName = SectionDatabaseInterface.getSectionName(sectionID);
        ArrayList<ServableTable> servableTableList;
        servableTableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        request.setAttribute("sectionName",sectionName);
        request.setAttribute("sectionID",sectionID);
        request.setAttribute("servableTableList",servableTableList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowTablesFromSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


