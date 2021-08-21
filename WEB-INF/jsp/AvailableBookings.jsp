<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="pkg.*" %>
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
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    </script>

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
                            <h3 style="text-align: center"><em><%=date%> : <%=numberOfPeople%> People</em></h3>
                    </div>
                    <br>
                    <div class="collapse <%for (int i = 0 ; i<table.size(); i++){%>multi-collapse<%=i%> <%}%> show" id="multiCollapseExample1">
                        <table class="table table-borderless">
                            <% for (int i = 0; i < table.size(); i++){
                                if (table.get(i).getTimeIncrement().getMinute()==0){%>
                                    <tr>
                                        <% for (int j = 0; j < 4 ; j++){%>
                                        <td>
                                            <%if(table.get(i+j).isClosed()){%>
                                                <button class="btn btn-outline-danger d-block btn-user w-100" type="submit">Closed</button>
                                            <%}%>
                                            <%if(table.get(i+j).isEmpty() && !table.get(i+j).isClosed()){%>
                                                <button class="btn btn-outline-danger d-block btn-user w-100" type="submit"><del><%=table.get(i+j).getTimeIncrement()%></del></button>
                                            <%}%>
                                            <%if(!table.get(i+j).isEmpty() && !table.get(i+j).isClosed()){%>
                                                <%if (table.get(i+j).reachedMax()){%>
                                                    <button class="btn btn-outline-danger d-block btn-user w-100" type="submit" data-toggle="tooltip" data-html="true" data-placement="top" title="<div style='background-color: #0dcaf0'>test</div>">
                                                        <div style="display: flex; justify-content: space-between">
                                                            <div style="width: 33%">

                                                            </div>
                                                            <div style="width: 33%;">
                                                                <%=table.get(i+j).getTimeIncrement()%>

                                                            </div>
                                                            <div style=" font-size: 12px; width: 33%">

                                                                <%=table.get(i+j).getAmountCovers()%>/<%=table.get(i+j).getVenueCovers()%> Covers
                                                            </div>
                                                        </div>
                                                    </button>
                                                <%}
                                                else if(table.get(i+j).willGoPastMaxCovers(numberOfPeople)){%>
                                                    <button class="btn btn-outline-danger d-block btn-user w-100" type="submit">
                                                        <div style="display: flex; justify-content: space-between">
                                                            <div style="width: 33%">

                                                            </div>
                                                            <div style="width: 33%;">
                                                                <%=table.get(i+j).getTimeIncrement()%>

                                                            </div>
                                                            <div style=" font-size: 12px; width: 33%">

                                                                <%=table.get(i+j).getAmountCovers()%>/<%=table.get(i+j).getVenueCovers()%> Covers
                                                            </div>
                                                        </div>
                                                    </button>
                                                <%}
                                                else{%>
                                                    <button class="btn btn-outline-primary w-100" type="button" data-toggle="collapse" data-target=".multi-collapse<%=i+j%>" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample<%=i+j%>">
                                                        <div style="display: flex; justify-content: space-between">
                                                            <div style="width: 33%">

                                                            </div>
                                                            <div style="width: 33%;">
                                                                <%=table.get(i+j).getTimeIncrement()%>

                                                            </div>
                                                            <div style=" font-size: 12px; width: 33%">

                                                                <%=table.get(i+j).getAmountCovers()%>/<%=table.get(i+j).getVenueCovers()%> Covers
                                                            </div>
                                                        </div>
                                                    </button>
                                                <%}%>

                                            <%}%>
                                        </td>
                                        <%}%>
                                    </tr>
                                <%}
                            }%>

                        </table>
                    </div>
                    <%for (int i = 0 ; i <table.size() ; i++){%>
                    <div class="collapse multi-collapse<%=i%>" id="multiCollapseExample<%=i%>">
                        <h3 style="text-align: center"><%=table.get(i).getTimeIncrement()%></h3>
                        <div style="display: flex; padding: 2px">
                            <%for (TimeIncrementSection timeIncrementSection : table.get(i).getSections()){
                                if (timeIncrementSection.getBookingsInSectionTimeIncrement().size()>0){%>
                                <% if (timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                <div class="card w-100 border-danger" style="margin: 2px">
                                <%}else if (timeIncrementSection.isRecommended()){%>
                                <div class="card w-100 border-success" style="margin: 2px">
                                <%}else{%>
                                    <div class="card w-100 border-primary " style="margin: 2px">
                                <%}%>

                                        <% if (timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                        <h5 class="bg-danger" style="text-align: center">Booking Will Exceed Max Covers</h5>
                                        <%} else if (timeIncrementSection.isRecommended()){%>
                                        <h5 class="bg-success" style="text-align: center">Recommended</h5>
                                        <%}else{%>
                                        <h5 class="bg-primary" style="text-align: center">Optional</h5>
                                        <%}%>
                                        <h4 style="text-align: center">
                                            <%=timeIncrementSection.getSectionName()%>
                                        </h4>

                                        <h5 style="text-align: center">
                                            <%=timeIncrementSection.getTimeIncrement()%> - <%=timeIncrementSection.getEndTime()%>
                                        </h5>
                                        <% if (timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                            <h6 style="text-align: center; color: red">
                                                <%=timeIncrementSection.getAmountCovers()%>/<%=timeIncrementSection.getMaxCovers()%> Covers
                                            </h6>
                                        <%}else{%>
                                            <h6 style="text-align: center;">
                                                <%=timeIncrementSection.getAmountCovers()%>/<%=timeIncrementSection.getMaxCovers()%> Covers
                                            </h6>
                                        <%}%>
                                            <div class="card-body">
                                            <%for (Booking booking : timeIncrementSection.getBookingsInSectionTimeIncrement()){%>
                                                <div class="row" style="margin-top: 5px">
                                                    <div class="col">
                                                        <%if (booking.isRecommended() && !timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                                        <button class="btn btn-outline-success  row w-100" style="display: flex;margin: auto;" type="button">
                                                        <%}else if (!timeIncrementSection.isRecommended() && !timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                                        <button class="btn btn-outline-primary  row w-100" style="display: flex;margin: auto;" type="button">
                                                        <%}else if (timeIncrementSection.isRecommended() && !timeIncrementSection.moreThanCovers(numberOfPeople)){%>
                                                            <button class="btn btn-outline-primary  row w-100" style="display: flex;margin: auto;" type="button">
                                                        <%}else{%>
                                                            <button class="btn btn-outline-danger  row w-100" style="display: flex;margin: auto;" type="button">
                                                        <%}%>
                                                                <% if (booking.isHasSingleTable()){%>
                                                                    <div class="col">
                                                                        Table:
                                                                        <%=booking.getTableNumber()%>
                                                                    </div>
                                                                <%}
                                                                else{%>
                                                                <div class="col">
                                                                    Tables:
                                                                    <%=booking.getTableNumber()%>
                                                                </div>
                                                                <%}%>
                                                                <div class="col">
                                                                    Seats:
                                                                    <%=numberOfPeople%>/<%=booking.getNumberOfSeats()%>
                                                                </div>

                                                        </button>
                                                    </div>
                                                </div>
    <%--                                        <tr>--%>
    <%--                                            <td style="text-align: center">--%>
    <%--                                                <% if (booking.isHasSingleTable()){%>--%>
    <%--                                                <%=booking.getTableNumber()%>--%>
    <%--                                                <%}--%>
    <%--                                                else{%>--%>
    <%--                                                <%=booking.getTableNumber()%>--%>
    <%--                                                <%}%>--%>
    <%--                                            </td>--%>
    <%--                                            <td style="text-align: center">--%>
    <%--                                                <%=booking.getNumberOfSeats()%>--%>
    <%--                                            </td>--%>
    <%--                                            <td style="text-align: center">--%>
    <%--                                                <%=booking.getEndTimeOfBookingLocalTime()%>--%>
    <%--                                            </td>--%>
    <%--                                        </tr>--%>
                                            <%}%>
                                            </div>
                                    </div>
                                <%}
                            }%>
                        </div>
                        <br>
                        <button class="btn btn-outline-secondary w-100" type="button" data-toggle="collapse" data-target=".multi-collapse<%=i%>" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample<%=i%>">
                            Back
                        </button>
                    </div>

                    <%}%>
                </div>
            </div>
        </div>
        <br>
    </div>
</div>
<button type="button" class="btn btn-secondary" data-toggle="tooltip" data-html="true" title="<em>Tooltip</em> <u>with</u> <b>HTML</b>">
    Tooltip with HTML
</button>

</body>
</html>