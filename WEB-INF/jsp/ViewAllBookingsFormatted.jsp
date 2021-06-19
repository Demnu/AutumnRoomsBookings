<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Booking" %>
<%@ page import="pkg.ServableTable" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Booking> todaysBookingsList = (ArrayList<Booking>) request.getAttribute("todaysBookingsList");
    ArrayList<LocalTime> timeIncrements = (ArrayList<LocalTime>) request.getAttribute("timeIncrements");
    ArrayList<ServableTable> allTables = (ArrayList<ServableTable>) request.getAttribute("allTables");
    ArrayList<Integer> indexsForTablesWithBookings = (ArrayList<Integer>) request.getAttribute("indexsForTablesWithBookings");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <style>
        h1 {
            color: Green;
        }
        div.scroll {
            overflow-x: auto;
            overflow-y: hidden;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<jsp:include page="Navbar.jsp"/>


<h4>All Bookings</h4>

            <div class="scroll">
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm"></th>
                        <% for (int i = 0 ; i < timeIncrements.size(); i++){ %>
                        <th class="th-sm"><%=timeIncrements.get(i)%></th>
                        <%}%>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0 ; i<allTables.size();i++){%>
                        <tr>
                            <td>T - <%=allTables.get(i).getTableNumber()%></td>
                            <% for (int j = 0 ; j<timeIncrements.size();j++){
                                    boolean tdHighlighted = false;
                                    for (int k = 0 ; k<indexsForTablesWithBookings.size(); k++){
                                        if (i == indexsForTablesWithBookings.get(k)){
                                            ServableTable tempServableTable = allTables.get(i);
                                            for (int l = 0 ; l<tempServableTable.getTimeIncrementsBookedOutForDay().size(); l++){
                                                LocalTime timeIncrementForTable = tempServableTable.getTimeIncrementsBookedOutForDay().get(l);
                                                if(timeIncrements.get(j).equals(timeIncrementForTable)){%>
                                                    <td style="background-color: dodgerblue"></td>
                                                    <%tdHighlighted=true;
                                                }
                                            }
                                        }
                                    }

                                %>

                                <% if (tdHighlighted==false){%>
                                    <td></td>

                                <%}%>
                            <%}%>
                        </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>



    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>


</body>
</html>