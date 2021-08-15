package pkg;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/createJoinedTable")
public class CreateJoinedTable extends HttpServlet {
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

        Enumeration<String> checkedTables = request.getParameterNames();
        String sectionIDStr = request.getParameter("sectionID");
        String numberSeatsStr = request.getParameter("numberSeats");

        Integer sectionID = Integer.parseInt(sectionIDStr);
        Section section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);

        Integer numberSeats = Integer.parseInt(numberSeatsStr);
        checkedTables.nextElement();
        checkedTables.nextElement();

        ArrayList<Integer> servableTableIDs = new ArrayList<>();
        while(checkedTables.hasMoreElements()){
            String servableTableIDStr = checkedTables.nextElement();
            servableTableIDs.add(Integer.parseInt(servableTableIDStr));
        }
        if (servableTableIDs.size()<2){
            errors.add("There must be more than one table selected");
            request.setAttribute("errors",errors);
            section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
            request.setAttribute("section",section);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/JoinedTables.jsp");
            dispatcher.forward(request, response);
            return;

        }
        Collections.sort(servableTableIDs);
        Boolean duplicate = false;
        if (section.getJoinedTables().size()!=0){
            for (JoinedTables joinedTables : section.getJoinedTables()){
                int counter = 0;
                ArrayList joinedTablesServableTableIDs = new ArrayList();
                for (ServableTable joinedTable : joinedTables.getJoinedTablesList()){
                    joinedTablesServableTableIDs.add(joinedTable.getTableID());
                }
                if(joinedTablesServableTableIDs.size() == servableTableIDs.size()){
                    for(int i = 0 ; i < joinedTablesServableTableIDs.size();i++){
                        if (joinedTablesServableTableIDs.get(i) == servableTableIDs.get(i)){
                            counter+=1;
                        }
                    }
                    if (counter == joinedTablesServableTableIDs.size()){
                        duplicate=true;
                        errors.add("Duplicate Joined Table");
                        request.setAttribute("errors",errors);

                    }
                }
            }
        }
        //check if not a duplicate


        if (!duplicate){
            JoinedTablesDatabaseInterface.createJoinedTable(sectionID,numberSeats,servableTableIDs);
        }

        section = SectionDatabaseInterface.getSectionGivenSectionID(sectionID);
        request.setAttribute("section",section);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/JoinedTables.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


