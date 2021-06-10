<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Section" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Section> sectionList = (ArrayList<Section>) request.getAttribute("sectionList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Table Pick a Section</title>
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
                <h4>Select a Section for the New Table</h4>
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">Section Name</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Amount of Tables</th>
                        <th class="th-sm">Amount of Seats</th>
                        <th class="th-sm">Max Capacity</th>
                        <th class="th-sm">Max Time for Bookings</th>
                        <th class="th-sm"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i< sectionList.size(); i++){ %>
                    <form action="selectSectionAddTable" method="POST" name="selectSectionAddTable" id="selectSectionAddTable">
                        <tr>
                            <input type="hidden" name="chosenSectionID" value="<%=sectionList.get(i).getSectionID()%>">
                            <td><%=sectionList.get(i).getName()%></td>
                            <td><%=sectionList.get(i).getDescription()%></td>
                            <td><%=sectionList.get(i).getServableTables().size()%></td>
                            <td><%=sectionList.get(i).getAmountSeats()%></td>
                            <td><%=sectionList.get(i).getMaxCapacity()%></td>
                            <td><%=sectionList.get(i).getMaxTimeOfBooking()%></td>
                            <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Select Section</button></td>
                        </tr>
                    </form>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>