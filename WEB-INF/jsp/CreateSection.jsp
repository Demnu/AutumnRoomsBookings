<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%User user = (User) session.getAttribute("user");%>
<!DOCTYPE html>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js">
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
                    <h4>Create a Section</h4>
                    <p>Please enter the form below to create a section.</p>
                </div>
                <form action="createSection" method="POST" name="ReportForm" id="ReportForm">

                    <label for="sectionName">Section Name:</label>
                    <input required type="text" name="sectionName" id="sectionName" class="form-control" placeholder="Section Name"/>
                    <br>
                    <label for="sectionDesc" class="mt-2">Description of Section</label>
                    <textarea required id="sectionDesc" name="sectionDesc" form="ReportForm" class="form-control " placeholder="Enter a brief description for the section"></textarea><br>
                    <br>
                    <input type="submit" class="btn btn-outline-primary" value="Create Section">
                    <input type="reset" class="btn btn-outline-secondary">
                </form>

            </div>

        </div>


    </div>
</div>
</body>
</html>