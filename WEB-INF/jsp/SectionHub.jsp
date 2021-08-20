<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Section" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Section> sectionList = (ArrayList<Section>) request.getAttribute("sectionList");
    ArrayList<LocalTime> timeIncrements = Functions.getTimeIncrementsFrom0To24Hours();
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
                            <form action="viewSection" method="GET" name="selectSectionAddTable" id="selectSectionAddTable">
                                <tr>
                                    <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                                    <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit"><%=section.getName()%></button></td>
                                </tr>
                            </form>
                            <%}%>
                                <tr>
                                    <td>
                                        <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button">Create New Section</a>
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
                                                            <label for="maxCoversSection">Maximum People allowed in the Section:</label>
                                                            <input required type="number" name="maxCoversSection" id="maxCoversSection" class="form-control" min="1" max="150"/>
                                                            <br>
                                                            <label for="maxTimeOfBooking">Maximum time HH/MM allowed for a booking:</label>
                                                            <select name="maxTimeOfBooking" id="maxTimeOfBooking" class="form-control">
                                                                    <%for (LocalTime localTime : timeIncrements){%>
                                                                <option value="<%=localTime%>"><%=localTime%></option>
                                                                <% }
                                                                %>
                                                            </select>
                                                            <br>
                                                            <label for="timeRequiredAfterBookingIsFinished">Time Required (HH/MM) to Reset After Booking is Finished:</label>
                                                            <select name="timeRequiredAfterBookingIsFinished" id="timeRequiredAfterBookingIsFinished" class="form-control">
                                                                <%for (LocalTime localTime : timeIncrements){%>
                                                                <option value="<%=localTime%>"><%=localTime%></option>
                                                                <% }
                                                                %>
                                                            </select>
                                                            <br>
                                                            <label for="timeConstrained">Time Allowed to Stay After Section Is Closed</label>
                                                            <select name="timeAllowedToStayAfterSectionIsClosed" id="timeAllowedToStayAfterSectionIsClosed" class="form-control">
                                                                <% for (LocalTime timeIncrement : timeIncrements){%>
                                                                <option value="<%=timeIncrement%>"><%=timeIncrement%></option>
                                                                <%}%>
                                                            </select>
                                                            <br>
                                                            <label for="timeConstrained">Check the box if the section is time constrained</label>
                                                            <input class="form-check" style=" width: 50px; height: 50px" type="checkbox" id="timeConstrained" name="timeConstrained" value="timeConstrained">
                                                            <br>
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