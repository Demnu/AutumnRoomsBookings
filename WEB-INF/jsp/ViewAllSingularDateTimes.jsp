<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.ChangedDateTimes" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>

<%
    User user = (User) session.getAttribute("user");
    ArrayList<ChangedDateTimes> changedDateTimes = (ArrayList<ChangedDateTimes>) request.getAttribute("changedDateTimes");
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html onload="myFunction()">
<head>
    <title>Bookings</title>
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
    <div class="card w-100">
        <div class="card-header">
            <h2>Open and Close Times of Changed Dates</h2>
        </div>
        <div class="card-body">

            <div class="row">
                <a class="btn btn-outline-primary" href="<%=request.getContextPath()%>/editSingleDate">Add New Date Open/Close Time</a>
            </div>
            <br>
            <div class="row">
                <h4>Open and Close Times</h4>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1"> <i class="bi bi-search"></i></span>
                    </div>
                    <input type="text" class="form-control" id="myInput" onkeyup="myFunction()" placeholder="Enter details to search" title="Enter details to search">
                </div>

                <form>

                <table id="myTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr class="header">
                        <th class="th-sm">Date</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Open Time</th>
                        <th class="th-sm">Close Time</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                        <% for (ChangedDateTimes changedDate : changedDateTimes){%>
                        <tr>
                                <td>
                                    <%=changedDate.getChangedDate()%>
                                </td>
                                <td>
                                    <%=changedDate.getDescription()%>
                                </td>
                                <td>
                                    <%=changedDate.getChangedOpenTime()%>
                                </td>
                                <td>
                                    <%=changedDate.getChangedCloseTime()%>
                                </td>
                            <td> <a class="btn btn-outline-danger form-check" style="width: 100%" href="<%=request.getContextPath()%>/deleteChangedDate?changedDateID=<%=changedDate.getChangedDateID()%>">Delete Date</a></td>

                                    <%}%>
                        </tr>
                    </tbody>
                </table>
                </form>
            </div>

        </div>
        <!--//TODO Enchancement: Make an undo button -->
        <br>
        <a class="btn btn-outline-secondary" href="<%=request.getContextPath()%>/editOpenCloseTimes">Back</a>

    </div>
    <br>

</div>
</table>

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