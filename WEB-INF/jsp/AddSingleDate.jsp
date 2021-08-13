<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Venue" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>

<%
    User user = (User) session.getAttribute("user");
    Venue venueDetails = (Venue) request.getAttribute("openCloseTimes");
    ArrayList<LocalTime>timeIncrementsList = Functions.getTimeIncrementsFrom0To24Hours();
    ArrayList<String> dayNames = Functions.getDayNames();
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Sections</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script>
        window.onload = function() {
            var src = document.getElementById("startDate"),
                dst = document.getElementById("endDate");
            src.addEventListener('input', function() {
                dst.value = src.value;
            });
        };
    </script>

</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Add A New Date</h2>
        </div>
        <div class="card-body">

            <div class="row">
                <h4>Please enter all of the details</h4>
                <form action="editSingleDate" method="POST" name="editSingleTable" id="editSingleTable">

                <table id="openAndCloseTimes" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <%
                        if (errors != null){%>
                    <tr class="bg-danger">
                        <td colspan="7"><%=errors%></td>
                    </tr>
                    <%}%>
                    <thead>
                    <tr>
                        <th class="th-sm">Date</th>
                        <th class="th-sm">End Date</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Open Time</th>
                        <th class="th-sm">Close Time</th>
                    </tr>
                    </thead>
                    <tbody>
                            <div class="form-group">
                                <tr>
                                    <td>
                                        <div>
                                            <input id="startDate" required name="startDate" autocomplete="off" class="form-control" type="date">
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <input id="endDate" required name="endDate" autocomplete="off" class="form-control" type="date">
                                        </div>
                                    </td>
                                    <td>
                                        <input required name="description" class="form-control">
                                    </td>
                                    <td>
                                        <select class="form-control" name="openTime" required>
                                            <%
                                                for (LocalTime localTime : timeIncrementsList){%>
                                            <option value="<%=localTime%>"><%=localTime%></option>
                                            <%  }
                                            %>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-control" name="closeTime" required>
                                            <%
                                                for (LocalTime localTime : timeIncrementsList){%>
                                            <option value="<%=localTime%>"><%=localTime%></option>
                                            <%  }
                                            %>
                                        </select>
                                    </td>
                                </tr>
                            </div>
                    </tbody>
                </table>
                    <button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Confirm</button>

                </form>

            </div>

        </div>
        <!--//TODO Enchancement: Make an undo button -->
        <a class="btn btn-outline-secondary" href="<%=request.getContextPath()%>/viewAllChangedDates">Back</a>

    </div>
    <br>

</div>
</body>
</html>