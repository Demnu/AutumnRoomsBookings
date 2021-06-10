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
                <h4>All Sections</h4>
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
                        <th class="th-sm"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i< sectionList.size(); i++){ %>
                    <form action="editSingleSection" method="POST" name="editSectionForm" id="editSectionForm">
                    <tr>
                        <input type="hidden" name="editSingleSectionID" value="<%=sectionList.get(i).getSectionID()%>">
                        <td><%=sectionList.get(i).getName()%></td>
                        <td><%=sectionList.get(i).getDescription()%></td>
                        <td><%=sectionList.get(i).getServableTables().size()%></td>
                        <td><%=sectionList.get(i).getAmountSeats()%></td>
                        <td>
                            <input type="number" name="maxCapacity" id="maxCapacity" class="form-control" min="1" max="150" value="<%=sectionList.get(i).getMaxCapacity()%>"/>
                        <td>
                            <select name="maxTimeOfBooking" id="maxTimeOfBooking" class="form-control">
                                <option value="0"><%=sectionList.get(i).getMaxTimeOfBooking()%></option>
                                <option value="1">0:15</option>
                                <option value="2">0:30</option>
                                <option value="3">0:45</option>
                                <option value="4">1:00</option>
                                <option value="5">1:15</option>
                                <option value="6">1:30</option>
                                <option value="7">1:45</option>
                                <option value="8">2:00</option>
                            </select>
                        </td>
                        <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Section</button></td>
                        <td> <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/deleteSection?deleteSection=<%=sectionList.get(i).getSectionID()%>">Delete Section</a></td>
                    </tr>
                    </form>
                    <%}%>
                    </tbody>
                </table>
            </div>
            <div class="row">

                <div class="col-10">
                    <h4>Create a Section</h4>
                    <p>Please enter the form below to create a section.</p>
                </div>
                <div class="form-group">
                    <form action="createSection" method="POST" name="ReportForm" id="ReportForm">

                        <label for="sectionName">Section Name:</label>
                        <input required type="text" name="sectionName" id="sectionName" class="form-control" placeholder="Section Name"/>
                        <br>
                        <label for="sectionDesc" class="mt-2">Description of Section</label>
                        <textarea required id="sectionDesc" name="sectionDesc" form="ReportForm" class="form-control " placeholder="Enter a brief description for the section"></textarea><br>
                        <label for="maxCapacity">Maximum People allowed in the Section:</label>
                        <input required type="number" name="maxCapacity" id="maxCapacity" class="form-control" min="1" max="150"/>
                        <br>
                        <label for="maxTimeOfBooking">Maximum time HH/MM allowed for a booking:</label>
                        <select name="maxTimeOfBooking" id="maxTimeOfBooking" class="form-control">
                            <option value="1">0:15</option>
                            <option value="2">0:30</option>
                            <option value="3">0:45</option>
                            <option value="4">1:00</option>
                            <option value="5">1:15</option>
                            <option value="6">1:30</option>
                            <option value="7">1:45</option>
                            <option value="8">2:00</option>
                        </select>                        <br>
                        <input type="submit" class="btn btn-outline-primary" value="Create Section">
                        <input type="reset" class="btn btn-outline-secondary">
                    </form>


                </div>


            </div>

        </div>


    </div>
    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>

</div>
</body>
</html>