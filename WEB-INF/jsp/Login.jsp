<%@ page contentType="text/html;"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js">
</head>
<body>
<div class="grandParentContaniner">
    <div class="parentContainer">

        <div class="p-5 card login-box">
            <div class="text-center">
                <h1>Login</h1>
                <p class="text-muted"> Please enter your login and password!</p>
            </div>
            <form class="user" action="" method="post">
                <div class="mb-3">
                    <input class="form-control form-control-user" type="text" placeholder="Username" name="username" />
                </div>
                <div class="mb-3">
                    <input class="form-control form-control-user" type="password" placeholder="Password" name="password" />
                </div>
                <div class="mb-3">

                </div>
                <button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Login</button>
                <a class="btn btn-outline-secondary d-block btn-user w-100 mt-2" href="${pageContext.request.contextPath}/register">Register an account</a>


            </form>


        </div>

    </div>
</div>
</body>
</html>