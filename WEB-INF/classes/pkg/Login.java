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

@WebServlet("")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
        dispatcher.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        //Recieved by Login.jsp: Username and Password
        String username = (String) request.getParameter("username");
        String passwordStr = (String) request.getParameter("password");
        System.out.println(username + passwordStr);
        //authenticate User and create User object
        User user;
        Integer password = Integer.parseInt(passwordStr);
        if (UserDatabaseInterface.checkUserDetails(username,password)){
            user = new User(username,password);
            session.setAttribute("user",user);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


