package pkg;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/selectSectionAddTable")
public class CreateTablePickSection extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }
        //Forward the Section List to ServableTable-PickSection.jsp
        ArrayList<Section> sectionList = new ArrayList<Section>();
        sectionList = SectionDatabaseInterface.getAllSections();
        request.setAttribute("sectionList",sectionList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ServableTable-PickSection.jsp");
        dispatcher.forward(request, response);
        return;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Received by ServableTabe-PickSecion.jsp: Section ID
        String sectionIDStr = (request.getParameter("chosenSectionID"));
        Integer sectionID = Integer.parseInt(sectionIDStr);
        String sectionName = SectionDatabaseInterface.getSectionName(sectionID);

        //Get and forward list of tables in chosen section to ShowTablesFromSection.jsp
        ArrayList<ServableTable> servableTableList;
        servableTableList = ServableTableDatabaseInterface.getAllServeableTables(sectionID);
        request.setAttribute("sectionID",sectionID);
        request.setAttribute("sectionName",sectionName);
        request.setAttribute("servableTableList",servableTableList);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ShowTablesFromSection.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


