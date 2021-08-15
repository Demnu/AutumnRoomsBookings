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

@WebServlet("/editJoinedTable")
public class EditJoinedTableController extends HttpServlet {
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
        String joinedTablesIDStr = (String) request.getParameter("joinedTablesID");
        String sectionIDStr = (String) request.getParameter("sectionID");
        String seatsNumberStr = (request.getParameter("seatsNumber"));
        int joinedTablesID = Integer.parseInt(joinedTablesIDStr);
        int sectionID = Integer.parseInt(sectionIDStr);
        int seatsNumber = Integer.parseInt(seatsNumberStr);
        JoinedTablesDatabaseInterface.updateSeatsNumber(joinedTablesID,seatsNumber);

        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/JoinedTables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


