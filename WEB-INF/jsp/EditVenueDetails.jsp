<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.VenueDetails" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>

<%
    User user = (User) session.getAttribute("user");
    VenueDetails venueDetails = (VenueDetails) request.getAttribute("venueDetails");
    ArrayList<LocalTime>timeIncrementsList = Functions.getTimeIncrementsFrom0To24Hours();
    ArrayList<String> dayNames = Functions.getDayNames();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Sections</title>
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
                        <th class="th-sm">Venue Name</th>
                        <th class="th-sm">Maximum Covers</th>

                    </tr>
                    </thead>
                    <tbody>

                    <form action="editSingleSection" method="POST" name="editSectionForm" id="editSectionForm">
                        <tr>
                            <td>
                                <%=venueDetails.getVenueName()%>
                            </td>

                            <td>
                                <%=venueDetails.getMaxCovers()%>
                            </td>

                        </tr>
                    </form>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <h4>All Bookings</h4>
                <table id="openAndCloseTimes" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">Day</th>
                        <th class="th-sm">Open Time</th>
                        <th class="th-sm">Close Time</th>
                        <th></th>

                    </tr>
                    </thead>
                    <tbody>
                        <% for (int j = 0 ; j<dayNames.size(); j ++){%>
                        <tr>
                            <form action="editTimeGivenDay" method="POST" name="editSingleTable" id="editSingleTable">

                                <div class="form-group">
                                    <input type="hidden" name="day" value="<%=dayNames.size()-1%>">
                                    <td>
                                        <%=dayNames.get(j)%>
                                    </td>

                                    <td>
                                        <select class="form-control" name="openTime">
                                            <option value="<%=venueDetails.getOpenTimes().get(j)%>"><%=venueDetails.getOpenTimes().get(j)%></option>
                                            <%
                                                for (LocalTime localTime : timeIncrementsList){%>
                                                    <option value="<%=localTime%>"><%=localTime%></option>
                                            <%  }
                                            %>
                                        </select>
                                    </td>

                                    <td>
                                        <select class="form-control" name="closeTIme">
                                            <option value="<%=venueDetails.getCloseTimes().get(j)%>"><%=venueDetails.getCloseTimes().get(j)%></option>
                                            <%
                                                for (LocalTime localTime : timeIncrementsList){%>
                                            <option value="<%=localTime%>"><%=localTime%></option>
                                            <%  }
                                            %>
                                        </select>
                                    </td>
                                    <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit <%=dayNames.get(j)%></button></td>
                                </div>
                            </form>


                        </tr>
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