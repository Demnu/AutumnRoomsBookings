<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Booking" %>
<%@ page import="pkg.ServableTable" %>
<%@ page import="pkg.Functions" %>

<%
    User user = (User) session.getAttribute("user");
    ArrayList<Booking> todaysBookingsList = (ArrayList<Booking>) request.getAttribute("todaysBookingsList");
    ArrayList<LocalTime> timeIncrements = (ArrayList<LocalTime>) request.getAttribute("timeIncrements");
    ArrayList<ServableTable> allTables = (ArrayList<ServableTable>) request.getAttribute("allTables");
    ArrayList<Integer> indexsForTablesWithBookings = (ArrayList<Integer>) request.getAttribute("indexsForTablesWithBookings");
    LocalTime openTime = (LocalTime) request.getAttribute("openTime");
    LocalTime closeTime = (LocalTime) request.getAttribute("closeTime");
    LocalDate showDate = (LocalDate) request.getAttribute("showDate");
    LocalDate currentDate = LocalDate.now();
    String showDateStr = (String) request.getAttribute("showDateStr");
    Functions functions = (Functions) request.getAttribute("functions");
    LocalDate dateBack = showDate.minusDays(1);
    LocalDate dateForward = showDate.plusDays(1);
%>
<!DOCTYPE html>
<html>
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

    <style>
        html, body{
            height: 100%;
            font-family: Arial;
        }

        div.scroll {
            overflow-x: auto;
            overflow-y: hidden;
            white-space: nowrap;
        }
        .darker{
            filter: brightness(50%);
        }
        .imageBookingEven{
            width : 100%;
            height : 2em;
            float:left;
            background-color: #166ec4;
            text-align: center;
        }
        .imageBookingOdd{
            width : 100%;
            height : 2em;
            float:left;
            background-color: #1774cf;
            text-align: center;
        }
        .overlay {
            width:20px;
            height : 100%;
            position : relative
        }

        .image {
            width : 100%;
            height : 100%;
            float:left;
            background-color: gold;
            text-align: center;

        }
        .imageHiddenOdd {
            width : 100%;
            height : 100%;
            float:left;
            background-color: #fffee0;
            text-align: center;

        }
        .imageHiddenEven {
            width : 100%;
            height : 100%;
            float:left;
            background-color: #f2f1d5;
            text-align: center;

        }
        .currentTimeLine {
            width : 100%;
            height : 100%;
            float:left;
            background-color: gold;
            text-align: center;

        }

        .bookingIncrement{
            background-color: dodgerblue
        }

        th{
            padding: 0px !important ;
            margin: 0px !important;
            filter: brightness(100%) !important;
            border-right: solid 1px grey !important;
            border-left: solid 1px grey !important;
            text-align: center;
            height: 1em;

        }
        table{
            border-collapse: collapse !important;

        }
        td {
            padding: 0px !important ;
            margin: 0px !important;
            /*border-right: solid 0.01em #c6c6c6 !important;*/
            /*border-left: solid 0.01em #c6c6c6 !important;*/
            text-align: center;
            height: 1em;

        }
        .hiddenTimeIncrement{

            visibility: hidden;
        }
        #currentDate{
            font-size: 32px;
            font-family: "Arial";
        }
        .borderless td, .borderless th {
            border: none;
        }
    </style>

    <script>
        var timeIncrements = new Array();
        function myFunction() {
            setInterval(myFunction, 5000);

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
                    $("td.timeIncrement:contains('" + incrementTime +"')").css("background-color", "#fffee0");
                    $("td.timeIncrementBooking:contains('" + incrementTime +"')").css("background-color", "#1774CF");
                    $("div.currentTimeLine:contains('" + incrementTime +"')").css("width", "0%");

                    // $("div.imageBooking:contains('" + incrementTime +"')").css("background-color", "black");
                    // $("td:nth-child(5)").addClass("darker");

                }
                else{
                    var result;
                    var resultTemp;
                    if (incrementTimeMinutesTemp == 15){
                        result = (currentDateMinutes/15)*100;
                    }
                    else if (incrementTimeMinutesTemp==30){
                        currentDateMinutes-=15;
                        result = (currentDateMinutes/15)*100;
                    }
                    else if (incrementTimeMinutesTemp==45){
                        currentDateMinutes -=30;
                        result = (currentDateMinutes/15)*100;
                    }
                    else if (incrementTimeMinutesTemp==0){
                        currentDateMinutes-=45;
                        result = (currentDateMinutes/15)*100;
                    }
                    resultTemp = result;
                    resultTemp -=5;
                    resultTemp +="%";
                    result+="%"
                    $("div.image:contains('" + incrementTime +"')").css("width", result);
                    $("div.imageHiddenOdd:contains('" + incrementTime +"')").css("width", resultTemp);
                    $("div.imageHiddenEven:contains('" + incrementTime +"')").css("width", resultTemp);
                    $("div.imageBookingEven:contains('" + incrementTime +"')").css("width", resultTemp);
                    $("div.imageBookingOdd:contains('" + incrementTime +"')").css("width", resultTemp);
                    $("div.currentTimeLine:contains('" + incrementTime +"')").css("width", "5%");

                    // document.getElementById("demo1").innerHTML = incrementTime + currentTime + " " +result;

                    break;
                }
            }
        }
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
<body onload="getTimeIncrements(),myFunction()">
<%--<body>--%>

    <jsp:include page="Navbar.jsp"/>
<br>
test
        <%for (int i = 0 ; i< allTables.size(); i++){%>
            <%for (int j = 0; j<allTables.get(i).getBookingsOnDay().size();j++){%>
                <p><%=allTables.get(i).getBookingsOnDay().get(j).getBookingID()%></p>
            <%}
        }%>


<table class="table table-sm borderless">
    <tr>
        <td></td>
        <td></td>
        <td>
            <%
            if (!showDate.isEqual(currentDate)){%>
            <a class="btn btn-primary" href="<%=request.getContextPath()%>/viewAllBookingsFormatted">Back to Today</a>
            <%}%></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
    <tr class="">
        <td class="col-4"></td>
        <td>
            <a class="btn btn-primary bi bi-chevron-left" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateBack%>"></a>
        </td>
        <td>
            <div id="currentDate" href="<%=request.getContextPath()%>/checkBookingAvailability"><%=showDateStr%></div>
        </td>
        <td>
            <a class="btn btn-primary bi bi-chevron-right" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateForward%>"></a>
        </td>
        <td class="col-4"></td>
    </tr>
</table>
            <div class="scroll">
                <table id="bookingsTable" class="table table-striped table-sm" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm overlay"></th>
                        <% for (int i = 0 ; i < timeIncrements.size(); i++){ %>
                        <th style="font-size: 12px" class="timeIncrement">
                            <div class="timeIncrement">
                                <div class="image" style="width: 0%">
                                    <%=timeIncrements.get(i)%>
                                </>
                            </div>
                        </th>
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
                                                if(timeIncrements.get(j).equals(timeIncrementForTable)){

                                                    if(functions.isOdd(i)){%>
                                                        <td class = "timeIncrementBooking" style="background-color: dodgerblue">
                                                            <div class="imageBookingOdd" style="width: 0%">
                                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                                            </div>
                                                            <div class="currentTimeLine" style="width: 0%">
                                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                                            </div>
                                                        </td>
                                                    <%}
                                                    else if(!functions.isOdd(i)){%>
                                                        <td class = "timeIncrementBooking" style="background-color: dodgerblue">
                                                            <div class="imageBookingEven" style="width: 0%">
                                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                                            </div>
                                                            <div class="currentTimeLine" style="width: 0%">
                                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                                            </div>
                                                        </td>
                                                    <%}
                                                tdHighlighted=true;
                                                }
                                            }
                                        }
                                    }
                                    %>
                                <% if (tdHighlighted==false){%>
                                    <% if (functions.isOdd(i)){%>
                                        <td style="font-size: 12px" class="timeIncrement">
                                            <div class="imageHiddenOdd" style="width: 0%">
                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                            </div>
                                            <div class="currentTimeLine" style="width: 0%">
                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                            </div>
                                        </td>
                                    <%}
                                    else if (!functions.isOdd(i)){%>
                                        <td style="font-size: 12px" class="timeIncrement">
                                            <div class="imageHiddenEven" style="width: 0%">
                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                            </div>
                                            <div class="currentTimeLine" style="width: 0%">
                                                <div class="hiddenTimeIncrement"><%=timeIncrements.get(j)%></div>
                                            </div>
                                        </td>
                                    <%}%>
                                <%}%>
                            <%}%>
                        </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>

    <p id="demo1">test</p>

    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>
</body>
</html>