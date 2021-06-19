<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Booking" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Booking> allBookingsList = (ArrayList<Booking>) request.getAttribute("allBookingsList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Welcome <%=user.getName()%> to the Autumn Rooms Booking System</h2>
        </div>
        <div class="card-body">
            <div class="row">
                <h4>All Bookings</h4>
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">Booking ID</th>
                        <th class="th-sm">Date of Booking</th>
                        <th class="th-sm">Start Time of Booking</th>
                        <th class="th-sm">End Time of Booking</th>
                        <th class="th-sm">Number Of People</th>
                        <th class="th-sm">Confirmed</th>
                        <th class="th-sm">Date Booked</th>
                        <th class="th-sm">Time Booked</th>
                        <th class="th-sm">Creator of Booking</th>
                        <th class="th-sm"></th>
                        <th class="th-sm"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i< allBookingsList.size(); i++){ %>
                    <form action="editSingleSection" method="POST" name="editSectionForm" id="editSectionForm">
                        <tr>
                            <input type="hidden" name="editSingleSectionID" value="<%=allBookingsList.get(i).getBookingID()%>">
                            <td><%=allBookingsList.get(i).getBookingID()%></td>
                            <td><%=allBookingsList.get(i).getDateOfBooking()%></td>
                            <td><%=allBookingsList.get(i).getStartTimeOfBooking()%></td>
                            <td><%=allBookingsList.get(i).getEndTimeOfBooking()%></td>
                            <td><%=allBookingsList.get(i).getNumberOfPeople()%></td>
                            <td><%=allBookingsList.get(i).isConfirmed()%></td>
                            <td><%=allBookingsList.get(i).getDateBooked()%></td>
                            <td><%=allBookingsList.get(i).getTimeBooked()%></td>
                            <td><%=allBookingsList.get(i).getStaffID()%></td>

                            <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Booking</button></td>
                            <td> <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/deleteBooking?deleteBookingID=<%=allBookingsList.get(i).getBookingID()%>">Delete Booking</a></td>
                        </tr>
                    </form>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </div>


    </div>
    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>

</div>
</body>
</html>