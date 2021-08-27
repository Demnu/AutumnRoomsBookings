<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="pkg.*" %>

<%
    int k = 0;
    User user = (User) session.getAttribute("user");
    ArrayList<Booking> todaysBookingsList = (ArrayList<Booking>) request.getAttribute("todaysBookingsList");
    Table table = (Table) request.getAttribute("table");
    ArrayList<LocalTime> timeIncrements = (ArrayList<LocalTime>) request.getAttribute("timeIncrements");
    ArrayList<ServableTable> allTables = (ArrayList<ServableTable>) request.getAttribute("allTables");
    ArrayList<Integer> indexsForTablesWithBookings = (ArrayList<Integer>) request.getAttribute("indexsForTablesWithBookings");
    LocalTime openTime = (LocalTime) request.getAttribute("openTime");
    String openTimeStr = openTime.toString();
    LocalTime closeTime = (LocalTime) request.getAttribute("closeTime");
    String closeTimeStr = closeTime.toString();
    LocalDate showDate = (LocalDate) request.getAttribute("showDate");
    LocalDate currentDate = LocalDate.now();
    String showDateStr = (String) request.getAttribute("showDateStr");
    Functions functions = (Functions) request.getAttribute("functions");
    LocalDate dateBack = showDate.minusDays(1);
    LocalDate dateForward = showDate.plusDays(1);
    String dayOfWeek = (String) request.getAttribute("dayOfWeek");
    int openTimeMinutes = openTime.getMinute() + openTime.getHour()*60;
    int closeTimeMinute = closeTime.getHour()*60 + closeTime.getMinute();

    ArrayList<BookingFormattedTimeIncrements> bookingFormattedTimeIncrements = (ArrayList<BookingFormattedTimeIncrements>) request.getAttribute("bookingFormattedTimeIncrements");


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
        tr {
            line-height: 2px;
            min-height: 2px;
            height: 2px;
        }
        th.timeIncrements{
            font-size: 10px;
        }
        body{
            height: 100%;
            min-height: 100%;
        }
        footer {
            clear: both;
            position: relative;
            height: 200px;
            margin-top: -200px;
        }
        th.timeIncrements0{
            background-color: rgb(239, 239, 239) !important;
        }
        th.timeIncrements1{
            background-color: white !important;
        }
        /*!* xs < 768 *!*/
        /*@media screen and (max-width: 768px) {*/
        /*    th.timeIncrements {*/
        /*        font-size: 6px;*/
        /*    }*/
        /*}*/

        /*!* sm *!*/
        /*@media screen and (min-width: 1300px) {*/
        /*    th.timeIncrements {*/
        /*        font-size: 10px;*/
        /*    }*/
        /*}*/

        /*!* md *!*/
        /*@media screen and (min-width: 1460px) {*/
        /*    th.timeIncrements {*/
        /*        font-size: 11px;*/
        /*    }*/
        /*}*/

        /*!* lg *!*/
        /*@media screen and (min-width: 1540px) {*/
        /*    th.timeIncrements {*/
        /*        font-size: 12px;*/
        /*    }*/
        /*}*/
    </style>
    <script>
        function setLineTime(){
            setInterval(setLineTime, 1000);
            let tdWidth =  parseFloat(document.getElementById("blankTh").offsetWidth);
            $("#lineTime").css("left",tdWidth);
            moveLineTime();
        }
        function moveLineTime(){
            let dimensions = document.getElementById("blankTh").getBoundingClientRect();
            var tdWidth = dimensions.width;
            dimensions = document.getElementById("timeIncrementsTable").getBoundingClientRect();
            let tableWidth = dimensions.width - tdWidth;

            let currentTime = new Date();
            let openTimeMinutes = document.getElementById("openTimeMinutes").innerHTML;
            let closeTimeMinutes = document.getElementById("closeTimeMinutes").innerHTML;

            let totalMinutes = parseInt(closeTimeMinutes) - parseInt(openTimeMinutes) + parseInt(15);
            let minutePixel = tableWidth/totalMinutes;

            let totalSeconds = totalMinutes*60;
            let secondPixel = tableWidth/totalSeconds;

            let currentTimeMinusOpenTime = new Date(currentTime.getTime() - openTimeMinutes*60000);

            let currentMinutes = currentTimeMinusOpenTime.getMinutes() + currentTimeMinusOpenTime.getHours()*60;
            let currentSeconds = currentTimeMinusOpenTime.getMinutes()*60 + currentTimeMinusOpenTime.getHours()*60*60 + currentTimeMinusOpenTime.getSeconds();

            let left = parseFloat(currentSeconds * secondPixel);
            // document.getElementById("demo").innerHTML=left;

            $("#lineTime").css("left",left+tdWidth-2.5);
        }
    </script>
</head>
<body onload="setLineTime()">
<div id="openTimeMinutes" style="visibility: hidden; position: absolute"><%=openTimeMinutes%></div>
<div id="closeTimeMinutes" style="visibility: hidden;position: absolute"><%=closeTimeMinute%></div>
<div class="card-header bg-dark text-white">
    <h5 style="text-align: center"><%=dayOfWeek%></h5>
    <h6 style="text-align: center"><%=showDateStr%></h6>
</div>
<div style="display: flex; justify-content: center;padding: 5px">
    <div style=" margin: 2px ; margin-right: 50px; left: 10px">
        <a style="" class=" btn btn-sm btn-primary " href="<%=request.getContextPath()%>/checkAvailability">Create a Booking</a>
    </div>
    <div style="margin: 2px">
        <a style="" class="btn btn-sm btn-outline-primary bi bi-chevron-left" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateBack%>"></a>
    </div>
    <div style="margin: 2px">
        <a class="btn btn-outline-primary btn-sm" style="width: 150px " href="<%=request.getContextPath()%>/viewAllBookingsFormatted">Back to Today</a>
    </div>
    <div style="margin: 2px">
        <a  class="btn  btn-outline-primary bi bi-chevron-right btn-sm" href="<%=request.getContextPath()%>/viewAllBookingsFormatted?inputtedDate=<%=dateForward%>"></a>
    </div>
    <div style="width: 124px; margin-left: 50px">

    </div>
</div>





<div class="card"; style="width: 95% ;margin: auto">
    <table id="timeIncrementsTable" class="table table-striped table-bordered" style="table-layout: fixed; margin: 0px">
        <thead>
        <tr>
            <th></th>
            <%  int tempHour =timeIncrements.get(0).getHour();
                int o = 1;
                for(BookingFormattedTimeIncrements timeIncrement1 : bookingFormattedTimeIncrements){
                    if(timeIncrement1.getTimeIncrement().getMinute()==0){
                        if(o==1){
                            o=0;
                        }
                        else{
                            o = 1;
                        }
                    }%>
                   <th class="timeIncrements timeIncrements<%=o%>" style="text-align: center; font-size: 14px" colspan="<%=timeIncrement1.getColspan()%>"> <%=timeIncrement1.getTimeIncrement().getHour()%></th>
            <%}%>

        </tr>
        <tr>
            <th id="blankTh" style="font-size:  10px">
                Table
            </th>
            <%
            o=1;
            for (LocalTime timeIncrement : timeIncrements){
                if (timeIncrement.getMinute()==0){
                    if(o==1){
                        o=0;
                    }
                    else{
                        o = 1;
                    }
                }
                if(timeIncrement.compareTo(timeIncrements.get(timeIncrements.size()-1))==0){%>
                <th id="lastTimeIncrement" class="timeIncrements timeIncrements<%=o%>" style="text-align: center"><%=timeIncrement.getMinute()%></th>
                <%}else{%>
                <th class="timeIncrements timeIncrements<%=o%>" style="text-align: center;"><%=timeIncrement.getMinute()%></th>

                <%}
            }%>
        </tr>
        </thead>
    </table>
    <div class="table-responsive" style="overflow-scrolling: auto">
        <table class="table table-striped table-bordered" style="table-layout: fixed; position: absolute; max-height: 100px">
            <div class="lineTime" id="lineTime" style="height: <%=18.81*table.getRows().size()%>px; width: 5px; background-color: gold; position: absolute;  z-index: 2; margin: 0px; padding: 0px" ></div>
            <tbody>
            <%for (Rows row : table.getRows()){%>
            <tr style="">
                <th>
                    <%=row.getTableNumber()%>
                </th>
                <% for (int i = 0; i <row.getColumns().size(); i++){
                    Columns column = row.getColumns().get(i);
                %>
                <% if (column.isStartOfBooking()){%>
                <td class="" style="background-color: rgb(14,133,255);" colspan="<%=column.getAmountOfTimeIncrements()%>-1">
                    <div style="z-index: 4; color: white; font-size: 8px">
                        <%=column.getBookingDetails()%>
                    </div>
                </td>
                <%i+=column.getAmountOfTimeIncrements()-1;}
                else{%>
                <td>

                </td>
                <%}
                }%>
            </tr>
            <%}%>

            </tbody>


        </table>
    </div>
</div>
</body>
</html>


<%--<div class="wholeTable" style="display: flex; flex-direction: column">--%>

<%--    <table id="bookingsTable" class="table table-striped table-sm table-bordered" cellspacing="0" width="100%" style='table-layout:fixed'>--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th style="background-color: white;"></th>--%>
<%--            <% for (int i = 0 ; i < timeIncrements.size(); i++){ %>--%>
<%--            <th class="" style="background-color: white;  text-align: right; left: 20px;">--%>

<%--                <%=timeIncrements.get(i)%>--%>

<%--            </th>--%>
<%--            <%}%>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>

<%--        <%--%>
<%--            int i = 0;--%>
<%--            for (Rows row : table.getRows()){%>--%>
<%--        <tr>--%>
<%--            <td><%=row.getTableNumber()%></td>--%>
<%--            <%for (Columns column : row.getColumns()){%>--%>
<%--            <%if (Functions.isOdd(i)){--%>
<%--                if (column.isBooked()){%>--%>
<%--            <% if (column.isStartOfBooking()){--%>
<%--            %>--%>
<%--            <td class = "timeIncrementBooking"  colspan="<%=column.getAmountOfTimeIncrements()%>">--%>
<%--                <div  class="booking" style="background-color: dodgerblue; height: 70%; display: flex; justify-content: space-between;">--%>
<%--                    <div style="padding-left: 2px">--%>
<%--                        <%=column.getBookingDetails()%>--%>
<%--                    </div>--%>
<%--                    <div style="padding-right: 2px; display: flex">--%>
<%--                        <%=todaysBookingsList.get(0).getNumberOfPeople()%>--%>
<%--                        <div style="font-size: 10px">--%>
<%--                            seats--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="progress" style="height: 10px; background-color: dodgerblue">--%>
<%--                    <div class=" bookingProgress progress-bar bg-warning " role="progressbar" style="width: 0%;">--%>
<%--                        <div class="hiddenTimeIncrementStartTime">--%>
<%--                            startTime<%=column.getStartTimeOfBooking()%>--%>
<%--                        </div>--%>
<%--                        <div class="hiddenTimeIncrementEndTime">--%>
<%--                            endTime<%=column.getEndTimeOfBooking()%>--%>
<%--                        </div>--%>
<%--                    </div>--%>

<%--                </div>--%>
<%--            </td>--%>
<%--            <%}%>--%>
<%--            <%}%>--%>
<%--            <%if (!column.isBooked()){%>--%>
<%--            <td style="font-size: 12px" class="timeIncrement">--%>
<%--                <div class="imageHiddenOdd" style="width: 0%">--%>
<%--                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>--%>
<%--                </div>--%>
<%--                <div class="currentTimeLine" style="width: 0%">--%>
<%--                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>--%>
<%--                </div>--%>
<%--            </td>--%>
<%--            <%}--%>
<%--            }%>--%>
<%--            <%if (!Functions.isOdd(i)){--%>
<%--                if (column.isBooked()){%>--%>
<%--            <% if (column.isStartOfBooking()){--%>
<%--            %>--%>
<%--            <td class = "timeIncrementBooking"  colspan="<%=column.getAmountOfTimeIncrements()%>">--%>
<%--                <div  class="booking" style="background-color: dodgerblue; height: 70%; display: flex; justify-content: space-between;">--%>
<%--                    <div style="padding-left: 2px">--%>
<%--                        <%=column.getBookingDetails()%>--%>
<%--                    </div>--%>
<%--                    <div style="padding-right: 2px; display: flex">--%>
<%--                        <%=todaysBookingsList.get(0).getNumberOfPeople()%>--%>
<%--                        <div style="font-size: 10px">--%>
<%--                            seats--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="progress" style="height: 10px; background-color: dodgerblue">--%>
<%--                    <div class=" bookingProgress progress-bar bg-warning " role="progressbar" style="width: 0%;">--%>
<%--                        <div class="hiddenTimeIncrementStartTime">--%>
<%--                            startTime<%=column.getStartTimeOfBooking()%>--%>
<%--                        </div>--%>
<%--                        <div class="hiddenTimeIncrementEndTime">--%>
<%--                            endTime<%=column.getEndTimeOfBooking()%>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </td>--%>
<%--            <%}%>--%>
<%--            <%}%>--%>
<%--            <%if (!column.isBooked()){%>--%>
<%--            <td style="font-size: 12px" class="timeIncrement">--%>
<%--                <div class="imageHiddenEven" style="width: 0%">--%>
<%--                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>--%>
<%--                </div>--%>
<%--                <div class="currentTimeLine" style="width: 0%">--%>
<%--                    <div class="hiddenTimeIncrement"><%=column.getTimeIncrement()%></div>--%>
<%--                </div>--%>
<%--            </td>--%>
<%--            <%}--%>
<%--            }%>--%>
<%--            <%}--%>
<%--                i++;--%>
<%--            }--%>
<%--            %>--%>
<%--        </tr>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--</div>--%>