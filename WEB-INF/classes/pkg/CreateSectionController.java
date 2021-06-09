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

@WebServlet("/createSection")
public class CreateSectionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            System.out.println("not logged in");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(req, resp);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateSection.jsp");
        dispatcher.forward(req, resp);
        return;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        //Received by CreateSection.jsp: Section Name and Brief Description
        String sectionName = (String) request.getParameter("sectionName");
        String sectionDesc = (String) request.getParameter("sectionDesc");

        //Authenticate section details and save to database
        if (SectionDatabaseInterface.saveSection(sectionName,sectionDesc)){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
            return;
        }
        else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/CreateSection.jsp");
            dispatcher.forward(request, response);
        }

        return;

    }
}


