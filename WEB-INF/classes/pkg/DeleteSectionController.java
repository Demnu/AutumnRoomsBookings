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

@WebServlet("/deleteSection")
public class DeleteSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        String sectionIDStr = (request.getParameter("deleteSection"));

        if (sectionIDStr!=null){
            Integer sectionID = Integer.parseInt(sectionIDStr);
            SectionDatabaseInterface.deleteSection(sectionID);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


