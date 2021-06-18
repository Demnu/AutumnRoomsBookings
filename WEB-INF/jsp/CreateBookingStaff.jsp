<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Section" %>
<%
    User user = (User) session.getAttribute("user");
    ArrayList<Section> sectionList = (ArrayList<Section>) request.getAttribute("sectionList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Booking Staff</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


    <script>

        $(function () {
            $('#datepicker').datepicker({ dateFormat: 'dd-mm-yy' });
        });
            $(function(){
                $('input.timepicker').timepicker({});
            });
    </script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Welcome <%=user.getName()%> to the Autumn Rooms Booking System</h2>
        </div>
        <div class="card-body">
            <div class="row">

                <div class="col-10">
                    <h4>Create a Booking</h4>
                    <h5>Search for Availability</h5>
                    <p>Please enter the details below to check availability.</p>
                </div>
                <div class="form-group">
                    <form action="checkBookingAvailability" method="POST" name="ReportForm" id="BookingForm">
                        <input type="hidden" name="staffID" id="staffID" class="form-control" value="<%=user.getStaffID()%>">
                        <label for="datepicker">Date of Booking:</label>
                        <input required name="dateOfBooking" autocomplete="off" class="form-control" type="text" id="datepicker">
                        <p>Hour:</p>
                        <select class="form-control" name="hours" id="hours"></select>
                        <p>Minute:</p>
                        <select class="form-control" name="minutes" id="minutes"></select>
                        <label for="numberOfPeople">Number of people:</label>
                        <input required type="number" name="numberOfPeople" id="numberOfPeople" class="form-control" min="1" max="150"/>
                        <br>
                        <input type="submit" class="btn btn-primary" value="Search for Availability">
                        <input type="reset" class="btn btn-outline-secondary">
                    </form>


                </div>


            </div>

        </div>


    </div>
    <br>
    <a class="btn btn-primary" href="<%=request.getContextPath()%>/">Back</a>

</div>
<script>

    function createOption(value, text) {
        var option = document.createElement('option');
        option.text = text;
        option.value = value;
        return option;
    }

    var hourSelect = document.getElementById('hours');
    for(var i = 8; i <= 15; i++){
        hourSelect.add(createOption(i, i));
    }

    var minutesSelect = document.getElementById('minutes');
    for(var i = 0; i < 60; i += 15) {
        minutesSelect.add(createOption(i, i));
    }
</script>
</body>
</html>