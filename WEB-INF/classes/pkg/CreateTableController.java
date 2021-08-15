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

@WebServlet("/createTable")
public class CreateTableController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        ArrayList<String> errors = new ArrayList<>();
        String sectionIDStr = request.getParameter("sectionID");
        String tableNumberStr = request.getParameter("tableNumber");
        String seatsNumberStr = request.getParameter("seatsNumber");
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Integer tableNumber = Integer.parseInt(tableNumberStr);
        Integer seatsNumber = Integer.parseInt(seatsNumberStr);

        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        //check if duplicate
        boolean duplicate = false;
        for (ServableTable servableTable : section.getServableTables()){
            if (servableTable.getTableNumber() == tableNumber){
                duplicate = true;
                errors.add("Duplicate Table");
                request.setAttribute("errors",errors);

            }
        }

        //Authenticate table details and save to database
        if (!duplicate && ServableTableDatabaseInterface.saveServableTable(sectionID,tableNumber,seatsNumber)){

        }
        section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Tables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


