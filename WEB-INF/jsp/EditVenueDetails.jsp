<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.Venue" %>
<%@ page import="pkg.Functions" %>
<%@ page import="java.time.LocalTime" %>

<%
    User user = (User) session.getAttribute("user");
    Venue venueDetails = (Venue) request.getAttribute("venueDetails");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Sections</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Edit Venue Details</h2>
        </div>
        <div class="card-body">
            <div class="row">

                <h4>Details</h4>
                <table id="dtBasicExample" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">

                    <tbody>
                    <tr>
                        <form action="selectEditVenueDetails" method="POST" name="editVenueName" id="editVenueName">
                            <td>
                                Venue Name
                            </td>
                            <td>
                                <input name="maxCovers" class="form-control" type="text" value="<%=venueDetails.getVenueName()%>">
                            </td>
                            <td>
                                <button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Venue Name</button>
                            </td>
                        </form>
                    </tr>

                    <tr>
                        <td>
                            Maximum Covers
                        </td>
                        <form action="selectEditVenueDetails" method="POST" name="editMaxCovers" id="editMaxCovers">
                        <td>
                            <div class="form-group">
                                    <input name="maxCovers" class="form-control" type="number" value="<%=venueDetails.getMaxCovers()%>">
                            </div>
                        </td>
                        <td>
                            <button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Max Covers</button>
                        </td>
                        </form>

                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    <a class="btn btn-outline-primary" href="<%=request.getContextPath()%>/">Back</a>
    </div>
    </div>
    <br>

</div>
</body>
</html>