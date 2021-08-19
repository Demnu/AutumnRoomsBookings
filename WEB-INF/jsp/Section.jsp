<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="pkg.*" %>
<%
    User user = (User) session.getAttribute("user");
    Section section = (Section) request.getAttribute("section");
    ArrayList<LocalTime> timeIncrements = (ArrayList<LocalTime>) Functions.getTimeIncrementsFrom0To24Hours();
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
    ArrayList<String> dayNames = Functions.getDayNames();
    ArrayList<LocalTime> timeIncrementsList = Functions.getTimeIncrementsFrom0To24Hours();
    Venue venueDetails = VenueDetailsDatabaseInterface.getOpenCloseTimes(1);

%>
<!DOCTYPE html>
<html>
<head>
    <title><%=section.getName()%> Section</title>
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
                        <h4 style="text-align: center">Actions</h4>
                        <table id="dtBasicExample" class="table table-borderless  table-sm" cellspacing="0" width="100%" style='table-layout:fixed'>
                            <tbody>
                            <tr>
                                <td>
                                    <a class="btn btn-outline-primary d-block btn-user w-100" href="<%=request.getContextPath()%>/viewTables?sectionID=<%=section.getSectionID()%>">Tables</a>
                                </td>
                                <td>
                                    <a class="btn btn-outline-primary d-block btn-user w-100" href="<%=request.getContextPath()%>/viewJoinedTables?sectionID=<%=section.getSectionID()%>">Joined Tables</a>
                                </td>

                            </tr>
<%--                            <tr>--%>
<%--                                <td>--%>
<%--                                    <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">View Tables</a>--%>
<%--                                </td>--%>
<%--                                <td>--%>
<%--                                    <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample2" role="button" aria-expanded="false" aria-controls="multiCollapseExample2">View Join Tables</a>--%>

<%--                                </td>--%>
<%--                            </tr>--%>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-outline-danger d-block btn-user w-100" href="<%=request.getContextPath()%>/deleteSection?sectionID=<%=section.getSectionID()%>">Delete Section</a>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-outline-secondary" style="width: 100%" href="<%=request.getContextPath()%>/sectionHub">Back</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <h4 style="text-align: center">Section Details</h4>
                        <div class="input-group">
                            <table id="" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <thead>
                                <tr class="header">
                                    <th class="th-sm">Description</th>
                                    <th class="th-sm">Amount of Tables</th>
                                    <th class="th-sm">Amount of Seats</th>
                                    <th class="th-sm">Amount of Joined Tables</th>
                                    <th class="th-sm">Max Covers</th>
                                    <th class="th-sm">Max Time for Bookings HH/MM</th>
                                    <th class="th-sm">Time Required to Reset After Booking is Finished HH/MM</th>
                                    <th class="th-sm">Time Constrained</th>
                                </tr>
                                </thead>
                                <tbody>

                                <form action="editSection" method="POST" name="editSectionForm" id="editSectionForm">
                                    <tr>
                                        <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                                        <td><%=section.getDescription()%></td>
                                        <td><%=section.getServableTables().size()%></td>
                                        <td><%=section.getAmountSeats()%></td>
                                        <td><%=section.getJoinedTables().size()%></td>
                                        <td>
                                            <input type="number" name="maxCoversSection" id="maxCoversSection" class="form-control" min="1" max="150" value="<%=section.getMaxCoversSection()%>"/>
                                        <td>
                                            <select name="maxTimeOfBooking" id="maxTimeOfBooking" class="form-control">
                                                <option value="<%=section.getMaxTimeOfBooking()%>"><%=section.getMaxTimeOfBookingLocalTime()%></option>
                                                <% for (LocalTime timeIncrement : timeIncrements){%>
                                                <option value="<%=timeIncrement%>"><%=timeIncrement%></option>
                                                <%}%>
                                            </select>
                                        </td>
                                        <td>
                                            <select name="timeRequiredAfterBookingIsFinished" id="timeRequiredAfterBookingIsFinished" class="form-control">
                                                <option value="<%=section.getTimeRequiredAfterBookingIsFinishedTime()%>"><%=section.getTimeRequiredAfterBookingIsFinishedLocalTime()%></option>
                                                <% for (LocalTime timeIncrement : timeIncrements){%>
                                                <option value="<%=timeIncrement%>"><%=timeIncrement%></option>
                                                <%}%>
                                            </select>
                                        </td>
                                        <td>
                                            <% if (section.isTimeConstrained()){%>
                                            <input class="form-check" style="margin: auto; width: 25px; height: 25px" type="checkbox" name="timeConstrained" value="timeConstrained" checked>
                                            <%}%>

                                            <% if (!section.isTimeConstrained()){%>
                                            <input class="form-check" style="margin: auto; width: 25px; height: 25px" type="checkbox" name="timeConstrained" value="timeConstrained">
                                            <%}%>
                                        </td>
                                        <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Section</button></td>
                                    </tr>
                                </form>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <% if (section.isTimeConstrained()){%>
                    <div class="card-body">
                        <div class="row">
                            <h4>Start and End Times of Section</h4>
                            <table id="openAndCloseTimes" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <%
                                    if (errors != null){%>
                                <tr class="bg-danger">
                                    <td colspan="7"><%=errors%></td>
                                </tr>
                                <%}%>
                                <thead>
                                <tr>
                                    <th style="width: 60px" class="th-sm">Day</th>
                                    <th style="width: 100px">Open Time</th>
                                    <th style="width: 100px;">Close Time</th>
                                    <th class="th-sm">Start Time</th>
                                    <th class="th-sm">End Time</th>

                                </tr>
                                </thead>
                                <tbody>
                                <% for (int j = 0 ; j<dayNames.size(); j ++){%>
                                <tr>
                                    <form action="editStartEndTimes" method="POST" name="editSingleTable" id="editSingleTable">

                                        <div class="form-group">
                                            <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">

                                            <input type="hidden" name="day" value="<%=dayNames.size()-1%>">
                                            <th>
                                                <%=dayNames.get(j)%>
                                            </th>
                                            <td>
                                                <%=venueDetails.getOpenTimes().get(j)%>
                                            </td>

                                            <td>
                                                <%=venueDetails.getCloseTimes().get(j)%>
                                            </td>

                                            <td>
                                                <select class="form-control" name="<%=dayNames.get(j)%>OpenTime">
                                                    <% if (section.getStartTimes().get(j).equals(LocalTime.parse("00:01"))){%>
                                                    <option value="00:01">N/A</option>
                                                    <%}%>
                                                    <% if (!section.getStartTimes().get(j).equals(LocalTime.parse("00:01"))){%>
                                                    <option value="<%=section.getStartTimes().get(j)%>"><%=section.getStartTimes().get(j)%></option>
                                                    <option value="00:01">N/A</option>
                                                    <%}%>
                                                    <%
                                                        for (LocalTime localTime : timeIncrementsList){%>
                                                    <option value="<%=localTime%>"><%=localTime%></option>
                                                    <%  }
                                                    %>
                                                </select>
                                            </td>

                                            <td>
                                                <select class="form-control" name="<%=dayNames.get(j)%>CloseTime">
                                                    <% if (section.getEndTimes().get(j).equals(LocalTime.parse("00:01"))){%>
                                                    <option value="00:01">N/A</option>
                                                    <%}%>
                                                    <% if (!section.getEndTimes().get(j).equals(LocalTime.parse("00:01"))){%>
                                                    <option value="<%=section.getEndTimes().get(j)%>"><%=section.getEndTimes().get(j)%></option>
                                                    <option value="00:01">N/A</option>
                                                    <%}%>
                                                    <%
                                                        for (LocalTime localTime : timeIncrementsList){%>
                                                    <option value="<%=localTime%>"><%=localTime%></option>
                                                    <%  }
                                                    %>
                                                </select>
                                            </td>
                                        </div>

                                            <%}%>
                                </tbody>
                            </table>
                        </div>
                        <input class="form-control" type="reset" value="Reset Changes">
                        <br>
                        <button class="btn btn-primary d-block btn-user w-100" type="submit">Edit Times</button>
                        </form>
                        <br>
                        <a class="btn btn-outline-danger d-block btn-user w-100" href="<%=request.getContextPath()%>/resetStartAndEndTimes?sectionID=<%=section.getSectionID()%>">Reset Times to Open and Close Times</a>

                    </div>
                </div>
            </div>
        </div>
        <%}%>
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
    <a class="btn btn-outline-secondary" style="width: 100%" href="<%=request.getContextPath()%>/sectionHub">Back</a>

</div>
<script>
    function myFunction() {
        // Declare variables
        var input, filter, table, tr, td, i, occurrence;

        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        // Loop through all table rows, and hide those who don't match the search query
        for (i = 0; i < tr.length; i++) {
            occurrence = false; // Only reset to false once per row.
            td = tr[i].getElementsByTagName("td");
            for(var j=0; j< td.length; j++){
                currentTd = td[j];
                if (currentTd ) {
                    if (currentTd.innerHTML.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                        occurrence = true;
                    }
                }
            }
            if(!occurrence){
                tr[i].style.display = "none";
            }
        }
    }

</script>
</body>
</html>