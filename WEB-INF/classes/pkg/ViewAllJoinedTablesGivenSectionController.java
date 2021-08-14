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

@WebServlet("/viewAllJoinedTablesGivenSection")
public class ViewAllJoinedTablesGivenSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        //Received by ShowTablesFromSection.jsp:
        String sectionIDStr = (request.getParameter("sectionID"));
        String sectionName = (request.getParameter("sectionName"));
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowAllJoinedTablesGivenSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


