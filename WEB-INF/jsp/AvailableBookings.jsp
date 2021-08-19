<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Section" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="pkg.TimeIncrementBooking" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Section> sectionList = (ArrayList<Section>) request.getAttribute("sectionList");
    ArrayList<TimeIncrementBooking> table = (ArrayList<TimeIncrementBooking>) request.getAttribute("table");
    ArrayList<LocalTime> timeIncrementsForDay = (ArrayList<LocalTime>) request.getAttribute("timeIncrementsForDay");
    Integer numberOfPeople = (Integer) request.getAttribute("numberOfPeople");
    LocalDate dateOfBooking = (LocalDate) request.getAttribute("dateOfBooking");
    String date = dateOfBooking.getDayOfMonth() + "/" + dateOfBooking.getMonthValue() + "/" + dateOfBooking.getYear();
    LocalTime openHour = LocalTime.of(timeIncrementsForDay.get(0).getHour(),0);
    LocalTime closeTime = timeIncrementsForDay.get(timeIncrementsForDay.size()-1);
    LocalTime closeHour = LocalTime.of(closeTime.getHour(),0);
    LocalTime tempTime = openHour;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Available Bookings</title>
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
            <h2 style="text-align: center">Booking Availability</h2>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                            <h4 style="text-align: center"><em><%=date%> : <%=numberOfPeople%> People <%=openHour%> <%=closeTime%></em></h4>
                    </div>
                    <br>
                    <table>
                        <%for (int i = 0 ; i<table.size() ; i++){
                            if (table.get(i).getTimeIncrement().getMinute()==0){%>
                                <tr>
                                    <td><%=table.get(i).getTimeIncrement()%></td>
                                    <%i++;%>
                                    <td><%=table.get(i).getTimeIncrement()%></td>
                                    <%i++;%>
                                    <td><%=table.get(i).getTimeIncrement()%></td>
                                    <%i++;%>
                                    <td><%=table.get(i).getTimeIncrement()%></td>
                                </tr>
                            <%}
                        }
                        %>



                    </table>



                    <div class="row" style="display: flex; flex-wrap: wrap ;padding: 5px !important; justify-content: left">
                        <%  int counter = 0;
                            for (TimeIncrementBooking timeIncrementBooking : table){%>

                                <a style="margin: 5px !important; width: 30%" class="test btn btn-outline-primary" data-toggle="collapse" href="#multiCollapse<%=counter%>" role="button"><%=timeIncrementBooking.getTimeIncrement()%></a>
                                <div class="collapse multi-collapse" id="multiCollapse<%=counter%>">
                                    test
                                </div>
                        <%counter++; }%>
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