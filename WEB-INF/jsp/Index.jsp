<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js">
</head>
<body>
<jsp:include page="navabar.jsp"/>

<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Welcome to the UON IT Issue Reporting Site</h2>
        </div>
        <div class="card-body">
            <div class="row">

                <div class="col-10">
                    <h4>What is this site?</h4>
                    <p>This site is the new solution in order to allow both users and staff to better manage and keep track of issues they may come across while interacting with the UON IT system.</p>
                    <p>Use the nav bar above to navigate the website.</p>
                </div>
                <div class="col-2">
                    <img src="${pageContext.request.contextPath}/imgs/uon-logo.png" alt="UON Logo" class="float-end w-100">
                </div>

            </div>

        </div>


    </div>
</div>
</body>
</html>