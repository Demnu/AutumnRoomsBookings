<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.ServableTable" %>
<%@ page import="java.sql.Time"%>
<%@ page import="java.time.LocalTime"%>
<%
    User user = (User) session.getAttribute("user");
    String dateOfBooking = (String) request.getAttribute("dateOfBooking");
    String timeOfBooking = (String) request.getAttribute("timeOfBooking");
    Integer sectionID = (Integer) request.getAttribute("sectionID");
    ArrayList<ServableTable> availableServableTables = (ArrayList<ServableTable>) request.getAttribute("availableServableTables");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Show Available Tables For Booking</title>
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
                <h4>Available Tables on <em><%=dateOfBooking%> at <%=timeOfBooking%></em></h4>
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">Table Number</th>
                        <th class="th-sm">Number of Seats</th>
                        <th class="th-sm"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i< availableServableTables.size(); i++){ %>
                    <form action="editSingleTable" method="POST" name="editSingleTable" id="editSingleTable">
                        <tr>
                            <td><%=availableServableTables.get(i).getTableNumber()%></td>
                            <td>
                                <input type="number" name="seatsNumber" id="seatsNumber" class="form-control" min="1" max="100" value="<%=availableServableTables.get(i).getSeats()%>"/>
                            </td>
                            <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Table</button></td>
                        </tr>
                    </form>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <div class="row">

                <div class="col-10">
                    <h4>Create a Table</h4>
                    <p>Please enter the form below to create a table.</p>
                </div>
                <div class="form-group">
                    <form action="createServableTable" method="POST" name="createTable" id="createTable">
                        <input hidden name="sectionID" id="sectionID" value="<%=sectionID%>">
                        <label for="tableNumber">Table Number:</label>
                        <input required type="number" name="tableNumber" id="tableNumber" class="form-control"/>
                        <br>
                        <label for="seatsNumber" class="mt-2">Number of Seats:</label>
                        <input required type="number" name="seatsNumber" id="seatsNumber" class="form-control" min="1" max="100"/>
                        <br>
                        <input type="submit" class="btn btn-outline-primary" value="Create Table">
                        <input type="reset" class="btn btn-outline-secondary">
                    </form>
                </div>

            </div>
        </div>
    </div>
    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/checkBookingAvailability">Back</a>
</div>
</body>
</html>