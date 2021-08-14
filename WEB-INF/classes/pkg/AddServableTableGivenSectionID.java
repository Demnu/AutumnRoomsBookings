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

@WebServlet("/addServableTableGivenSectionID")
public class AddServableTableGivenSectionID extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        //Received by ServableTable-PickSection.jsp: Section ID
        String sectionIDStr = (request.getParameter("chosenSectionID"));
        Integer sectionID = Integer.parseInt(sectionIDStr);
        String sectionName = SectionDatabaseInterface.getSectionName(sectionID);
        //Get and forward list of tables in chosen section to ShowTablesFromSection.jsp
        ArrayList<ServableTable> servableTableList;
        servableTableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        request.setAttribute("sectionID",sectionID);
        request.setAttribute("sectionName",sectionName);
        request.setAttribute("servableTableList",servableTableList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateTableGivenSectionID.jsp");
        dispatcher.forward(request, response);
        return;
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
        String sectionIDStr = request.getParameter("sectionID");
        String tableNumberStr = request.getParameter("tableNumber");
        String seatsNumberStr = request.getParameter("seatsNumber");
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Integer tableNumber = Integer.parseInt(tableNumberStr);
        Integer seatsNumber = Integer.parseInt(seatsNumberStr);

        //get section name
        String sectionName = SectionDatabaseInterface.getSectionName(sectionID);

        //Authenticate table details and save to database
        if (ServableTableDatabaseInterface.saveServableTable(sectionID,tableNumber,seatsNumber)){

        }
        //Get and forward list of tables in chosen section to ShowTablesFromSection.jsp
        ArrayList<ServableTable> servableTableList;
        servableTableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        request.setAttribute("sectionName",sectionName);
        request.setAttribute("sectionID",sectionID);
        request.setAttribute("servableTableList",servableTableList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateTableGivenSectionID.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


