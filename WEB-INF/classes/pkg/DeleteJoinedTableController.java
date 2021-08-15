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

@WebServlet("/deleteJoinedTable")
public class DeleteJoinedTableController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        String joinedTableIDStr = (request.getParameter("joinedTableID"));
        String sectionIDStr = (request.getParameter("sectionID"));
        Integer sectionID = Integer.parseInt(sectionIDStr);
        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        if (joinedTableIDStr!=null){
            Integer joinedTableID = Integer.parseInt(joinedTableIDStr);
            for (JoinedTables joinedTables : section.getJoinedTables()){
                if (joinedTables.getJoinedTablesID() == joinedTableID){
                    JoinedTablesDatabaseInterface.deleteJoinedTable(joinedTableID,joinedTables.getJoinedTablesList());

                }
            }
        }
        section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/JoinedTables.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


