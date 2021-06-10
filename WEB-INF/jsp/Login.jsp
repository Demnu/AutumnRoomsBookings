<%@ page contentType="text/html;"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
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
                    <input class="form-control form-control-user" type="number" placeholder="Password" name="password" maxlength="4"/>
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