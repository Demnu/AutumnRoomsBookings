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

@WebServlet("/createServableTable")
public class CreateServableTableController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Received by CreateSection.jsp: Section Name and Brief Description
        String sectionIDStr = (String) request.getParameter("sectionID");
        String tableNumberStr = (String) request.getParameter("tableNumber");
        String seatsNumberStr = (String) request.getParameter("seatsNumber");
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
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowTablesFromSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


