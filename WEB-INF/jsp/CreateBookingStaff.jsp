<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="pkg.*" %>
<%
    User user = (User) session.getAttribute("user");
    Section section = (Section) request.getAttribute("section");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Create a Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <script src="extensions/fixed-columns/bootstrap-table-fixed-columns.js"></script>
    <style>
        /*div.test {*/
        /*    width: 50%;*/
        /*    height: 50%;*/
        /*    position: absolute;*/
        /*    top:0;*/
        /*    bottom: 0;*/
        /*    left: 0;*/
        /*    right: 0;*/
        /*    margin: auto;*/
        /*}*/
    </style>
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="container test" style="">
    <div class="card">
        <div class="card w-100">
            <div class="card-header">
                <h2 style="text-align: center">Make a Booking</h2>
            </div>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <div class="col-10">
                            <h4>Search for Availability</h4>
                            <p>Please enter the details below to check availability.</p>
                        </div>
                        <div class="form-group">
                            <form action="checkAvailability" method="POST" name="ReportForm" id="BookingForm">
                                <input type="hidden" name="staffID" id="staffID" class="form-control" value="<%=user.getStaffID()%>">
                                <label for="dateOfBooking">Date of Booking:</label>
                                <input id="dateOfBooking" required name="dateOfBooking" autocomplete="off" class="form-control" type="date">
                                <br>
                                <label for="numberOfPeople">Number of people:</label>
                                <input required type="number" name="numberOfPeople" id="numberOfPeople" class="form-control" min="1" max="150"/>
                                <br>
                                <input type="submit" class="btn btn-primary w-100" value="Search for Availability">
                            </form>
                        </div>
                    </div>

                </div>
            </div>
            <br>
            <a class="btn btn-outline-secondary" style="width: 100%" href="<%=request.getContextPath()%>/">Back</a>
        </div>
    </div>

</div>
</body>
</html>


