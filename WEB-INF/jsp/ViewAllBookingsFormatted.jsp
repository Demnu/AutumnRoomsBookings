<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Booking" %>
<%@ page import="pkg.ServableTable" %>
<%@ page import="pkg.Functions" %>
<%@ page import="pkg.Table" %>
<%@ page import="pkg.Rows" %>
<%@ page import="pkg.Columns" %>

<%
    int k = 0;
    User user = (User) session.getAttribute("user");
    ArrayList<Booking> todaysBookingsList = (ArrayList<Booking>) request.getAttribute("todaysBookingsList");
    Table table = (Table) request.getAttribute("table");
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
            margin: 0; padding: 0;
            font-family: Arial;
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
            text-align: center !important;

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
            border: none !important;
        }
        table{
            padding: 0px !important ;
            margin: 0px !important;
        }

        td {
            padding: 0px !important ;
            margin: 0px !important;
            /*border-right: solid 0.01em #c6c6c6 !important;*/
            /*border-left: solid 0.01em #c6c6c6 !important;*/
            text-align: center;
            height: 2em;

        }
        .hiddenTimeIncrementStartTime{

            visibility: hidden;
        }
        .hiddenTimeIncrementEndTime{

            visibility: hidden;
        }
        .hiddenTimeIncrement{

            visibility: hidden;
        }
        #currentDate{
            font-size: 32px;
            font-family: "Arial";
        }
        .borderless td {
            border: none;
        }

        div.wholeTable{
            overflow:auto;
            width:100%;
            height:500px;
        }
        td,
        th {
            border: 1px solid #000;
        }
        th {background-color:red;}

        table {
            width:100%;
        }
        td:first-child, th:first-child{
            position:sticky;
            left:0;
            z-index:1;
            background-color:grey;
        }
        thead tr th {
            position:sticky;
            top:0;
        }
        th:first-child {z-index:2;background-color:red;}


        td.timeIncrementBooking{
            /*border:none !important;*/
            color: white !important;
            font-size: 12px;
            text-align: left !important;

        }
        .booking{
            padding: 0px !important;
        }
        .progress{
            border-radius: 0px!important;
        }
        .progress-bar{
            border-radius: 0px !important
            background-color: gold!important;
        }

    </style>

    <script>

        var timeIncrements = new Array();
        var bookingStartTimes = new Array();
        var bookingEndTimes = new Array();
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
                    $("th.header:contains('" + incrementTime +"')").css("background-color", "gold" );
                    $("td.timeIncrement:contains('" + incrementTime +"')").css("background-color", "#fffee0");
                    // $("td.timeIncrementBooking:contains('" + incrementTime +"')").css("background-color", "#1774CF");
                    $("div.currentTimeLine:contains('" + incrementTime +"')").css("width", "0%");
                    $("td.timeIncrementBooking:contains('endTime" + incrementTime +"')").css("background-color", "#fffee0");
                    // $('.bookingProgress').css('width', '100%');

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
                    $("div.currentTimeLine:contains('" + incrementTime +"')").css("width", "5%");
                    // document.getElementById("demo1").innerHTML = incrementTime + currentTime + " " +result;'

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
        function getBookingTimeIncrements(){


            <%
            for (Rows row : table.getRows()){
                    for (Columns column : row.getColumns()){
                        if (column.isStartOfBooking()){%>
                            var startTime = new Date();
                            startTime.setSeconds(0);
                            var endTime = new Date();
                            endTime.setSeconds(0);
                            var hoursStartTime = <%=column.getStartTimeHours()%>;
                            var minutesStartTime = <%=column.getStartTimeMinutes()%>;
                            var hoursEndTime = <%=column.getEndTimeHours()%>;
                            var minutesEndTime = <%=column.getEndTimeMinutes()%>;
                            startTime.setHours(hoursStartTime)
                            startTime.setMinutes(minutesStartTime)
                            endTime.setHours(hoursEndTime)
                            endTime.setMinutes(minutesEndTime)
                            bookingStartTimes.push(startTime);
                            bookingEndTimes.push(endTime);
                        <%}
                    }
            }
            %>

        }
        function updateProgressBars(){
            setInterval(updateProgressBars, 5000);
            let i = 0;
            var currentTime = new Date();
            var timeIncrementDate = new Date(bookingEndTimes[0]);

            var bookingEndTime = new Date(bookingEndTimes[3]);
            var bookingStartTime = new Date(bookingStartTimes[3]);
            var totalMinutesOfBooking = (bookingEndTime.getHours()*60 + bookingEndTime.getMinutes())- (bookingStartTime.getHours()*60+bookingStartTime.getMinutes());
            var totalMinutesCurrentTime = currentTime.getHours()*60 + currentTime.getMinutes();
            var result = (totalMinutesCurrentTime/totalMinutesOfBooking)*100;

            $(".progress-bar").each(function() {
                var currentTime = new Date();
                var bookingEndTime = new Date(bookingEndTimes[i]);
                var bookingStartTime = new Date(bookingStartTimes[i]);
                if (currentTime>bookingEndTime){
                    $(this).css("width","100%")
                    $(this).removeClass("progress-bar-striped progress-bar-animated")
                }
                else if (currentTime<bookingEndTime && currentTime>bookingStartTime){
                    var totalMinutesOfBooking = (bookingEndTime.getHours()*60 + bookingEndTime.getMinutes())-(bookingStartTime.getHours()*60+bookingStartTime.getMinutes());
                    var totalMinutesAtStartOfBooking = (bookingStartTime.getHours()*60 + bookingStartTime.getMinutes());
                    var totalMinutesCurrentTime = currentTime.getHours()*60 + currentTime.getMinutes();
                    var currentTime = totalMinutesCurrentTime - totalMinutesAtStartOfBooking;
                    var result = (currentTime/totalMinutesOfBooking)*100;
                    result +="%"
                    $(this).css("width", result)
                    $(this).addClass("progress-bar-striped progress-bar-animated")

                }
                i = i + 1;
            });

        }

    </script>
</head>
<body onload="getTimeIncrements(),myFunction(),getBookingTimeIncrements(),updateProgressBars()">
<%--<body>--%>

    <jsp:include page="Navbar.jsp"/>
<br>
<p id="demo1"></p>
<table class="table table-sm borderless">
    <tr class="">
        <td class="col-4"></td>
        <td>
            <a class="btn btn-primary bi bi-chevron-left" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateBack%>"></a>
        </td>
        <td>
            <%
                if (!showDate.isEqual(currentDate)){%>
            <a class="btn btn-primary" href="<%=request.getContextPath()%>/viewAllBookingsFormatted">Back to Today</a>
            <%}%></td>

        </td>
        <td>
            <a class="btn btn-primary bi bi-chevron-right" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateForward%>"></a>
        </td>
        <td class="col-4"></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td>
            <div id="currentDate" href="<%=request.getContextPath()%>/checkBookingAvailability"><%=showDateStr%></div>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>
<br>
<table class="timeTable">
    <tbody>

    </tbody>
</table>
<div class="wholeTable" style="display: flex; flex-direction: column">

    <table id="bookingsTable" class="table table-striped table-sm table-bordered" cellspacing="0" width="100%" style='table-layout:fixed'>
        <thead>
        <tr>
            <th style="background-color: white;"></th>
            <% for (int i = 0 ; i < timeIncrements.size(); i++){ %>
            <th class="" style="background-color: white;  text-align: right; left: 20px;">

                <%=timeIncrements.get(i)%>

            </th>
            <%}%>
        </tr>
        </thead>
        <tbody>

        <%
            int i = 0;
            for (Rows row : table.getRows()){%>
        <tr>
            <td><%=row.getTableNumber()%></td>
            <%for (Columns column : row.getColumns()){%>
            <%if (Functions.isOdd(i)){
                if (column.isBooked()){%>
                    <% if (column.isStartOfBooking()){
                        %>
                        <td class = "timeIncrementBooking"  colspan="<%=column.getAmountOfTimeIncrements()%>">
                            <div  class="booking" style="background-color: dodgerblue; height: 70%; display: flex; justify-content: space-between;">
                                <div style="padding-left: 2px">
                                    <%=column.getBookingDetails()%>
                                </div>
                                <div style="padding-right: 2px; display: flex">
                                    <%=todaysBookingsList.get(0).getNumberOfPeople()%>
                                    <div style="font-size: 10px">
                                        seats
                                    </div>
                                </div>
                            </div>
                            <div class="progress" style="height: 10px; background-color: dodgerblue">
                                <div class=" bookingProgress progress-bar bg-warning " role="progressbar" style="width: 0%;">
                                    <div class="hiddenTimeIncrementStartTime">
                                        startTime<%=column.getStartTimeOfBooking()%>
                                    </div>
                                    <div class="hiddenTimeIncrementEndTime">
                                        endTime<%=column.getEndTimeOfBooking()%>
                                    </div>
                                </div>

                            </div>
                        </td>
                    <%}%>
            <%}%>
            <%if (!column.isBooked()){%>
            <td style="font-size: 12px" class="timeIncrement">
                <div class="imageHiddenOdd" style="width: 0%">
                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>
                </div>
                <div class="currentTimeLine" style="width: 0%">
                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>
                </div>
            </td>
            <%}
            }%>
            <%if (!Functions.isOdd(i)){
                if (column.isBooked()){%>
                    <% if (column.isStartOfBooking()){
                        %>
                            <td class = "timeIncrementBooking"  colspan="<%=column.getAmountOfTimeIncrements()%>">
                                <div  class="booking" style="background-color: dodgerblue; height: 70%; display: flex; justify-content: space-between;">
                                    <div style="padding-left: 2px">
                                        <%=column.getBookingDetails()%>
                                    </div>
                                    <div style="padding-right: 2px; display: flex">
                                        <%=todaysBookingsList.get(0).getNumberOfPeople()%>
                                        <div style="font-size: 10px">
                                            seats
                                        </div>
                                    </div>
                                </div>
                                <div class="progress" style="height: 10px; background-color: dodgerblue">
                                    <div class=" bookingProgress progress-bar bg-warning " role="progressbar" style="width: 0%;">
                                        <div class="hiddenTimeIncrementStartTime">
                                            startTime<%=column.getStartTimeOfBooking()%>
                                        </div>
                                        <div class="hiddenTimeIncrementEndTime">
                                            endTime<%=column.getEndTimeOfBooking()%>
                                        </div>
                                    </div>
                                </div>
                            </td>
                    <%}%>
            <%}%>
            <%if (!column.isBooked()){%>
            <td style="font-size: 12px" class="timeIncrement">
                <div class="imageHiddenEven" style="width: 0%">
                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>
                </div>
                <div class="currentTimeLine" style="width: 0%">
                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>
                </div>
            </td>
            <%}
            }%>
            <%}
                i++;
            }
            %>
        </tr>
        </tbody>
    </table>
</div>
    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>
</body>
</html>