package pkg;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/editVenueDetails")
public class EditVenueDetailsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }

        Venue venueDetails = VenueDetailsDatabaseInterface.getVenueDetails(user.getVenueID());
        request.setAttribute("venueDetails",venueDetails);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/EditVenueDetails.jsp");
        dispatcher.forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        String maxCoversStr = (request.getParameter("maxCovers"));
        Integer maxCovers = Integer.parseInt(maxCoversStr);
        VenueDetailsDatabaseInterface.editMaxCovers(1,maxCovers);
        Venue venueDetails = VenueDetailsDatabaseInterface.getVenueDetails(user.getVenueID());
        venueDetails.setChangedDateTimes();
        request.setAttribute("venueDetails",venueDetails);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/EditOpenCloseTimes.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


