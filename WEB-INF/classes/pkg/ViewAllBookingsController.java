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

@WebServlet("/viewAllBookings")
public class ViewAllBookingsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Index.jsp");
            dispatcher.forward(request, response);
        }
        ArrayList<Booking>allBookingsList = new ArrayList<Booking>();
        allBookingsList = BookingDatabaseInterface.getAllBookings();
        request.setAttribute("allBookingsList",allBookingsList);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/ViewAllBookings.jsp");
        dispatcher.forward(request, response);
        return;
    }
}


