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
    LocalTime openTime = (LocalTime) request.getAttribute("openTime");
    LocalTime closeTime = (LocalTime) request.getAttribute("closeTime");

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
        .darker{
            filter: brightness(88%);
        }
    </style>

    <script>
        var timeIncrements = new Array();
        function myFunction() {
            var currentDate = new Date();

            var currentDateHours = currentDate.getHours();
            var currentDateMinutes = currentDate.getMinutes();
            var currentTime = currentDate.getHours() + ":" + currentDate.getMinutes();
            for (let i = 0 ; i<timeIncrements.length ; i++){
                var timeIncrementDate = new Date(timeIncrements[i]);

                var incrementTimeHoursTemp = timeIncrementDate.getHours();
                var incrementTimeHours;
                if (incrementTimeHoursTemp<10){
                    incrementTimeHours = "0"+incrementTimeHoursTemp;
                }
                else{
                    incrementTimeHours = incrementTimeHoursTemp;
                }
                var incrementTimeMinutesTemp = timeIncrementDate.getMinutes();

                var incrementTimeMinutes;
                if (incrementTimeMinutesTemp<10){
                    incrementTimeMinutes = "0"+incrementTimeMinutesTemp;
                }
                else{
                    incrementTimeMinutes = incrementTimeMinutesTemp;
                }
                var incrementTime = incrementTimeHours + ":" + incrementTimeMinutes;
                if(timeIncrementDate<=currentDate){
                    $("th:contains('" + incrementTime +"')").css("background-color", "gold" );
                    $("th:contains('" + incrementTime +"')").addClass("darker");
                    $("td:nth-child(2)").css("background-color", "cornflowerblue");


                }
            }
            // document.getElementById("demo1").innerHTML = incrementTime;


            // $('.a830').css('backgroundColor', 'red')
            // for(var i = 0; i < elements.length; i++) {
            //     elements.style.backgroundColor = "gold";
            // }
            // var createdTime = new Date('December 17, 1995 07:30:00');
            //
            // var dt = new Date($.now());
            // var time = createdTime.getHours() + ":" + createdTime.getMinutes();
            // $('#bookingsTable th').eq(3).html(time);
            // $('#bookingsTable th').eq(2).css("background-color","gold");
            // $('#bookingsTable td:nth-child(3)').css("background-color","gold");
        }

        refreshData(); // execute function
        function getTimeIncrements(){

            var hoursOpenTime = <%=openTime.getHour()%>;
            var minutesOpenTime = <%=openTime.getMinute()%>;
            var hoursCloseTime = <%=closeTime.getHour()%>;
            var minutesCloseTime = <%=closeTime.getMinute()%>;
            var startTime = new Date();
            startTime.setHours(hoursOpenTime);
            startTime.setMinutes(minutesOpenTime);
            startTime.setSeconds(0);
            var endTime = new Date();
            var tempTime = startTime;
            endTime.setHours(hoursCloseTime);
            endTime.setMinutes(minutesCloseTime);
            endTime.setSeconds(0);
            var totalHours = hoursCloseTime-hoursOpenTime;
            var totalMinutes = minutesCloseTime - minutesOpenTime;
            var totalTimeMinutes = (totalHours*60)+totalMinutes;
            var currentMinute = 0;

            while(currentMinute<=totalTimeMinutes){
                timeIncrements.push(tempTime);
                tempTime = new Date(tempTime.getTime() + 15*60000);
                currentMinute+=15;
            }

        }

    </script>
</head>
<body onload="getTimeIncrements(),myFunction(),refreshData()" >
<jsp:include page="Navbar.jsp"/>


<h4>All Bookings</h4>
            <div class="scroll">
                <table id="bookingsTable" class="table table-striped  table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm"></th>
                        <% for (int i = 0 ; i < timeIncrements.size(); i++){ %>
                        <th style="font-size: 12px" class="th-sm"><%=timeIncrements.get(i)%></th>
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
                                                    <td id="booked" style="background-color: dodgerblue"></td>
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

    <p id="demo1"></p>

    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>
</body>
</html>