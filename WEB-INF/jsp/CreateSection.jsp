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
    <title>Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js">
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
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm">Section Name</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Max Capacity</th>
                        <th class="th-sm">Max Time for Bookings</th>
                        <th class="th-sm"></th>

                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i< sectionList.size(); i++){ %>
                    <tr>
                        <td><%=sectionList.get(i).getName()%></td>
                        <td><%=sectionList.get(i).getDescription()%></td>
                        <td><%=sectionList.get(i).getMaxCapacity()%></td>
                        <td><%=sectionList.get(i).getMaxTimeOfBooking()%></td>
                        <td> <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/deleteSection?deleteSection=<%=sectionList.get(i).getSectionID()%>">Delete Section
                        </a></td>
                    </tr>
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
                        <input required type="number" name="maxCapacity" id="maxCapacity" class="form-control"/>
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
</div>
</body>
</html>