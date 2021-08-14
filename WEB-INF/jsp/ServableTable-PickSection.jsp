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
    <title>Section Hub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <style>
        a.btn:focus {
            outline: none;
            box-shadow: none;
        }
    </style>
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="container">
    <div class="card">

        <div class="card-header">
            <h2 style="text-align: center">Section Hub</h2>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <h4 style="text-align: center">Select a Section</h4>
                        <table id="dtBasicExample" class="table table-borderless  table-sm" cellspacing="0" width="100%">

                            <tbody>
                            <% for (Section section : sectionList){ %>
                            <form action="viewAllTablesInSection" method="POST" name="selectSectionAddTable" id="selectSectionAddTable">
                                <tr>
                                    <input type="hidden" name="chosenSectionID" value="<%=section.getSectionID()%>">
                                    <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit"><%=section.getName()%></button></td>
                                </tr>
                            </form>
                            <%}%>
                                <tr>
                                    <td>
                                        <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Create New Section</a>
                                        <div class="collapse multi-collapse" id="multiCollapseExample1">
                                            <div class="card w-100">
                                                <div class="card-body">
                                                    <div class="row">
                                                        <form action="createSection" method="POST" name="ReportForm" id="ReportForm">
                                                            <h4>Create New Section</h4>
                                                            <label for="sectionName">Section Name:</label>
                                                            <input required type="text" name="sectionName" id="sectionName" class="form-control" placeholder="Section Name"/>
                                                            <br>
                                                            <label for="sectionDesc" class="mt-2">Description of Section:</label>
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
                                                                <option value="9">0:00</option>
                                                            </select>
                                                            <br>
                                                            <label for="timeRequiredAfterBookingIsFinished">Time Required (HH/MM) to Reset After Booking is Finished</label>
                                                            <select name="timeRequiredAfterBookingIsFinished" id="timeRequiredAfterBookingIsFinished" class="form-control">
                                                                <option value="1">0:00</option>
                                                                <option value="1">0:15</option>
                                                                <option value="2">0:30</option>
                                                                <option value="3">0:45</option>
                                                                <option value="4">1:00</option>
                                                                <option value="5">1:15</option>
                                                                <option value="6">1:30</option>
                                                                <option value="7">1:45</option>
                                                                <option value="8">2:00</option>
                                                            </select><br>
                                                            <input type="submit" class="btn btn-primary w-100" value="Create Section">
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a style="width: 100%" class="btn btn-outline-secondary" href="<%=request.getContextPath()%>/">Back</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>