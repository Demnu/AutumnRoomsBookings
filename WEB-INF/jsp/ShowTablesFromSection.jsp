<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.ServableTable" %>
<%@ page import="pkg.Section" %>
<%@ page import="pkg.Functions" %>
<%@ page import="pkg.JoinedTables" %>
<%@ page import="java.time.LocalTime" %>
<%
    User user = (User) session.getAttribute("user");
    Section section = (Section) request.getAttribute("section");
    ArrayList<LocalTime> timeIncrements = (ArrayList<LocalTime>) Functions.getTimeIncrementsFrom0To24Hours();

%>
<!DOCTYPE html>
<html>
<head>
    <title>Show Tables From Section</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <script src="extensions/fixed-columns/bootstrap-table-fixed-columns.js"></script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="container">
    <div class="card">
        <div class="card w-100">
            <div class="card-header">
                <h2 style="text-align: center"> <%=section.getName()%> Section</h2>
            </div>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <h4 style="text-align: center">Section Details</h4>
                        <form>
                            <table id="" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <thead>
                                <tr class="header">
                                    <th class="th-sm">Description</th>
                                    <th class="th-sm">Amount of Tables</th>
                                    <th class="th-sm">Amount of Seats</th>
                                    <th class="th-sm">Max Capacity</th>
                                    <th class="th-sm">Max Time for Bookings</th>
                                    <th class="th-sm">Time Required to Reset After Booking is Finished</th>
                                    <th class="th-sm"></th>
                                </tr>
                                </thead>
                                <tbody>

                                <form action="editSingleSection" method="POST" name="editSectionForm" id="editSectionForm">
                                    <tr>
                                        <input type="hidden" name="editSingleSectionID" value="<%=section.getSectionID()%>">
                                        <td><%=section.getDescription()%></td>
                                        <td><%=section.getServableTables().size()%></td>
                                        <td><%=section.getAmountSeats()%></td>
                                        <td>
                                            <input type="number" name="maxCapacity" id="maxCapacity" class="form-control" min="1" max="150" value="<%=section.getMaxCapacity()%>"/>
                                        <td>
                                            <select name="maxTimeOfBooking" id="maxTimeOfBooking" class="form-control">
                                                <option value="<%=section.getMaxTimeOfBooking()%>"><%=section.getMaxTimeOfBooking()%></option>
                                                <% for (LocalTime timeIncrement : timeIncrements){%>
                                                <option value="<%=timeIncrement%>"><%=timeIncrement%></option>
                                                <%}%>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="timeRequiredAfterBookingIsFinished" id="timeRequiredAfterBookingIsFinished" class="form-control">
                                                <option value="0"><%=section.getTimeRequiredAfterBookingIsFinishedTime()%></option>
                                                <% for (LocalTime timeIncrement : timeIncrements){%>
                                                <option value="<%=timeIncrement%>"><%=timeIncrement%></option>
                                                <%}%>
                                            </select>
                                        </td>
                                        <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Section</button></td>
                                    </tr>
                                </form>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <h4 style="text-align: center">Actions</h4>
                        <table id="dtBasicExample" class="table table-borderless  table-sm" cellspacing="0" width="100%" style='table-layout:fixed'>

                            <tbody>
                            <tr>
                                <td>
                                    <a class="btn btn-outline-primary d-block btn-user w-100" href="<%=request.getContextPath()%>/addServableTableGivenSectionID?chosenSectionID=<%=section.getSectionID()%>">Modify Tables for Section</a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-primary d-block btn-user w-100" href="<%=request.getContextPath()%>/viewAllJoinedTablesGivenSection?sectionID=<%=section.getSectionID()%>">Modify Join Tables</a>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">View Tables</a>
                                </td>
                                <td>
                                    <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample2" role="button" aria-expanded="false" aria-controls="multiCollapseExample2">View Join Tables</a>

                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-outline-danger d-block btn-user w-100" href="<%=request.getContextPath()%>/addServableTableGivenSectionID?chosenSectionID=<%=section.getSectionID()%>">Delete Section</a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-outline-secondary" style="width: 100%" href="<%=request.getContextPath()%>/viewAllTablesInSection">Back</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <br>

        <div class="collapse multi-collapse" id="multiCollapseExample1">
            <div class="container">
                <div class="card w-100">
                    <div class="card-body">
                        <div class="row">
                            <h4 style="text-align: center">Tables in <%=section.getName()%> Section</h4>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1"> <i class="bi bi-search"></i></span>
                                </div>
                                <input type="text" class="form-control" id="myInput" onkeyup="myFunction()" placeholder="Enter details to search" title="Enter details to search">
                            </div>

                            <form>

                                <table id="myTable"  class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                    <thead>
                                    <tr class="header">
                                        <th class="th-sm">Table Number</th>
                                        <th class="th-sm">Number of Seats</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% for (ServableTable servableTable : section.getServableTables()){ %>
                                    <tr>
                                        <td>
                                            <%=servableTable.getTableNumber()%>
                                        </td>
                                        <td>
                                            <%=servableTable.getSeats()%>
                                        </td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                    <a class="test btn btn-outline-secondary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Collapse</a>

                </div>
            </div>
        </div>
        <br>
        <div class="collapse multi-collapse" id="multiCollapseExample2">
            <div class="container">
                <div class="card w-100">
                    <div class="card-body">
                        <div class="row">
                            <h4 style="text-align: center"> Joined Tables in <%=section.getName()%> Section</h4>

                            <form>
                                <table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                    <thead>
                                    <tr class="header">
                                        <th class="th-sm">Tables Joined</th>
                                        <th class="th-sm">Number Of Seats</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% for (JoinedTables joinedTable : section.getJoinedTables()){%>
                                    <tr>
                                        <td>
                                            <div style="display: flex; justify-content: center; flex-direction: column">
                                                <%for (ServableTable servableTable : joinedTable.getJoinedTablesList()){%>
                                                <div>Table - <b><%=servableTable.getTableNumber()%></b></div>
                                                <%}%>
                                            </div>

                                        </td>
                                        <td>
                                            <%=joinedTable.getNumberSeats()%>
                                        </td>
                                    </tr>
                                    <%}%>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                    <a class="test btn btn-outline-secondary w-100" data-toggle="collapse" href="#multiCollapseExample2" role="button" aria-expanded="false" aria-controls="multiCollapseExample2">Collapse</a>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>