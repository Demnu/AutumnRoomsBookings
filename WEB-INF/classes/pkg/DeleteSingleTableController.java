package pkg;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/deleteSingleTable")
public class DeleteSingleTableController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        String tableIDStr = (request.getParameter("tableID"));
        String sectionIDStr = (request.getParameter("sectionID"));
        Integer sectionID = Integer.parseInt(sectionIDStr);

        if (tableIDStr!=null){
            Integer tableID = Integer.parseInt(tableIDStr);
            ServableTableDatabaseInterface.deleteServableTable(tableID);
        }


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


