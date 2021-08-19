package pkg;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/editSingleDate")
public class EditSingleDateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user==null){
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
        ArrayList<ChangedDateTimes> changedDateTimes = ChangedDateDatabaseInterface.getChangedDateTimes(user.getVenueID());
        request.setAttribute("changedDateTimes",changedDateTimes);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/AddSingleDate.jsp");
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
        ArrayList<String> errors = new ArrayList<>();
        //Received by AddSingleDate.jsp
        String startDateStr = (request.getParameter("startDate"));
        String endDateStr = (request.getParameter("endDate"));
        String openTimeStr = (request.getParameter("openTime"));
        String closeTimeStr = (request.getParameter("closeTime"));
        String description = (request.getParameter("description"));
        LocalDate startDateLocalDate = LocalDate.parse(startDateStr);
        LocalDate endDateLocalDate = LocalDate.parse(endDateStr);
        LocalTime openTime = LocalTime.parse(openTimeStr);
        LocalTime endTime = LocalTime.parse(closeTimeStr);
        LocalDateTime startDate = LocalDateTime.of(startDateLocalDate, LocalTime.now());
        LocalDateTime endDate = LocalDateTime.of(endDateLocalDate, LocalTime.now());

        ChangedDateDatabaseInterface.saveChangedDate(startDateLocalDate,openTime,endTime,description,user.getVenueID());
        long daysBetween = Duration.between(startDate, endDate).toDays();

        for (int i = 0 ; i<daysBetween; i++){
            startDateLocalDate = startDateLocalDate.plusDays(1);
            ChangedDateDatabaseInterface.saveChangedDate(startDateLocalDate,openTime,endTime,description,user.getVenueID());
        }

        ArrayList<ChangedDateTimes> changedDateTimes = ChangedDateDatabaseInterface.getChangedDateTimes(user.getVenueID());
        request.setAttribute("changedDateTimes",changedDateTimes);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/AddSingleDate.jsp");
        dispatcher.forward(request, response);
        return;

    }
}


