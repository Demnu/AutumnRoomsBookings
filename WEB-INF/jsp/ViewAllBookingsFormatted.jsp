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
    ArrayList<Section> sections = (ArrayList<Section>) request.getAttribute("sections");


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
        body{
            background-image: url("${pageContext.request.contextPath}/imgs/autumnroomsBackground.jpeg");
            background-size:cover

        }
        .card{
            opacity: 98% !important;

        }
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
            background-color: rgb(248, 248, 248);

        }
        th.timeIncrements1{
            background-color: white ;
        }
        td.true{
            background-color: rgb(243, 243, 243);
        }
        td.false{
            background-color: white ;
        }
        .body {
            /*height: 200px;*/
            overflow: auto;
        }

        /* Hide scrollbar for Chrome, Safari and Opera */
        #containerForTable::-webkit-scrollbar {
            display: none;
        }

        /* Hide scrollbar for IE, Edge and Firefox */
        #containerForTable {
            -ms-overflow-style: none;  /* IE and Edge */
            scrollbar-width: none;  /* Firefox */
        }
        /* Hide scrollbar for Chrome, Safari and Opera */
        html::-webkit-scrollbar {
            display: none;
        }

        /* Hide scrollbar for IE, Edge and Firefox */
        html {
            -ms-overflow-style: none;  /* IE and Edge */
            scrollbar-width: none;  /* Firefox */
        }
        th.minuteTimeIncrement{
            padding: 0 !important;
            padding-bottom: 6px!important;
            height: 14px !important;
            font-size: 12px;

        }
        th {
            word-wrap: break-word;
        }
        th.tableNumber{
            padding: 0 !important;
            padding-top: 5px !important;
            height: 14px !important;

        }
        /* !*Ipad Horizontal < 768 *!*/
        /*@media screen and (max-height: 768px) {*/
        /*    .body {*/
        /*         max-height: 600px;*/
        /*    }*/
        /*}*/

        /*!*!* Left screen medium *!*!*/
        /*@media screen and (min-height: 900px) {*/
        /*    .body {*/
        /*        max-height: 735px;*/
        /*    }*/
        /*}*/

        /*!* Ipad horizontal *!*/
        /*@media screen and (min-height: 937px) {*/
        /*    .body {*/
        /*        max-height: 770px;*/
        /*    }*/
        /*}*/
        /*!* Right screen large *!*/
        /*@media screen and (min-height: 1024px) {*/
        /*    .body {*/
        /*        max-height: 850px;*/
        /*    }*/
        /*}*/



    </style>
    <script>
        var currentScreenHeight = 0;
        var count = 0;
        function setLineTime(){
            setInterval(setLineTime, 1000);
            let dimensions = document.getElementById("blankTh").getBoundingClientRect();
            var tdWidth = dimensions.width;
            dimensions = document.getElementById("containerForTable").getBoundingClientRect();

            var tableHeight = dimensions.height;
            $("#lineTime").css("left",tdWidth);
            $("#lineTime").css("height",tableHeight);
            // if (tdWidth<=29){
            //     $(".minuteTimeIncrement").css("font-size",8);
            // }

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

            $("#lineTime").css("left",tdWidth + left-2.5);
        }
        function startTableSize(){
            let screenHeightPx = $(window).height();
            let dimensions = document.getElementById("tableBody").getBoundingClientRect();
            let preTableHeightPx = dimensions.height;
            $("#containerForTable").css("height",0);
            dimensions = document.getElementById("bodySizeBeforeTable").getBoundingClientRect();
            let bodyHeight = dimensions.height;
            var tableHeight = screenHeightPx-bodyHeight-15;

            // if (preTableHeightPx<tableHeight){
            //     $("#containerForTable").css("height",preTableHeightPx);
            // }
            // else{
            //
            // }
            $("#containerForTable").css("height",tableHeight-tableHeight*0.01);
        }
        function setTableSize(){
                count ++;
                let screenHeightPx = $(window).height();
                let dimensions = document.getElementById("tableBody").getBoundingClientRect();
                let preTableHeightPx = dimensions.height;
                $("#containerForTable").css("height",0);
                dimensions = document.getElementById("bodySizeBeforeTable").getBoundingClientRect();
                let bodyHeight = dimensions.height;
                var tableHeight = screenHeightPx-bodyHeight-15;

                if (preTableHeightPx<tableHeight){
                    $("#containerForTable").css("height",preTableHeightPx);
                }
                else{
                    $("#containerForTable").css("height",tableHeight-tableHeight*0.01);

                }

            // }
        }
        window.addEventListener("resize", setTableSize);


    </script>
</head>
<body  onload="setTableSize(), setLineTime()">
<div id="bodySizeBeforeTable">
    <div id="openTimeMinutes" style="visibility: hidden; position: absolute"><%=openTimeMinutes%></div>
    <div id="closeTimeMinutes" style="visibility: hidden;position: absolute"><%=closeTimeMinute%></div>
    <div class="card-header bg-dark text-white">
        <h5 style="text-align: center"><%=dayOfWeek%></h5>
        <h6 style="text-align: center"><%=showDateStr%></h6>
    </div>



    <div class="card"; style="width: 98% ;margin: auto">
        <p id="demo">
        </p>
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
                <th class="hourTimeIncrement timeIncrements timeIncrements<%=o%>" style="text-align: center; font-size: 14px" colspan="<%=timeIncrement1.getColspan()%>"> <%=timeIncrement1.getTimeIncrement().getHour()%></th>
                <%}%>

            </tr>
            <tr>
                <th id="blankTh"></th>
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
                        }%>
                        <th id="" class="minuteTimeIncrement timeIncrements timeIncrements<%=o%>" style="text-align: center"><%=timeIncrement.getMinute()%></th>
                    <%}%>
            </tr>
            </thead>
        </table>
        <div id="containerForTable" class="table-responsive body" style="">
            <table id="tableBody" class="table table-striped table-bordered" style="table-layout: fixed; position: relative; max-height: 1px">
                <div class="lineTime" id="lineTime" style="height: <%=18.81*table.getRows().size()%>px; width: 5px; background-color: gold; position: absolute;  z-index: 2; margin: 0px; padding: 0px" ></div>
                <tbody>
                <%for (Section section : sections){%>
                    <tr>
                        <th style="text-align: left;" colspan="5>"><%=section.getName()%></th>
                        <% for (int i = 4 ; i<timeIncrements.size();i++){%>
                            <th></th>
                        <%}%>
                    </tr>
                    <%for (Rows row : table.getRows()){%>
                        <% if (row.getSectionID()==section.getSectionID()){%>
                        <tr style="">
                            <!-- TODO Development: Make th  for table number have col span of 2 -->
                            <th class="tableNumber" style="font-size: 12px">
                                <%=row.getTableNumber()%>
                            </th>
                            <% for (int i = 0; i <row.getColumns().size(); i++){
                                Columns column = row.getColumns().get(i);
                            %>
                            <% if (column.isStartOfBooking()){%>
                            <td class="<%=column.isEven()%>" style="background-color: rgb(14,133,255);" colspan="<%=column.getAmountOfTimeIncrements()%>-1">
                                <div style="z-index: 4; color: white; font-size: 12px">
                                    <%=column.getBookingDetails()%>
                                </div>
                            </td>
                            <%i+=column.getAmountOfTimeIncrements()-1;}
                            else{%>
                            <td class="<%=column.isEven()%>">

                            </td>
                            <%}
                            }%>
                        </tr>
                        <%}
                    }
                }%>

                </tbody>


            </table>
        </div>
    </div>
</div>

</body>
</html>

