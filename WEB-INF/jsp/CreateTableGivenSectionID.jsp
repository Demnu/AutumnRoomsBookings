<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.ServableTable" %>
<%
    User user = (User) session.getAttribute("user");
    Integer sectionID = (Integer) request.getAttribute("sectionID");
    String sectionName = (String) request.getAttribute("sectionName");
    ArrayList<ServableTable> tableList = (ArrayList<ServableTable>) request.getAttribute("servableTableList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Show Tables From Section</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <script src="extensions/fixed-columns/bootstrap-table-fixed-columns.js"></script>

</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="container">
    <div class="card w-100">
        <div class="card-header">
            <h2>Add Tables to Section: <%=sectionName%></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-10">
                    <h4>Create a Table</h4>
                    <p>Please enter the form below to create a table.</p>
                </div>
                <div class="form-group">
                    <form action="addServableTableGivenSectionID" method="POST" name="createTable" id="createTable">
                        <input hidden name="sectionID" id="sectionID" value="<%=sectionID%>">
                        <label for="tableNumber">Table Number:</label>
                        <input required type="number" name="tableNumber" id="tableNumber" class="form-control"/>
                        <br>
                        <label for="seatsNumber" class="mt-2">Number of Seats:</label>
                        <input required type="number" name="seatsNumber" id="seatsNumber" class="form-control" min="1" max="100"/>
                        <br>
                        <input type="submit" class="btn btn-outline-primary" value="Create Table">
                        <input type="reset" class="btn btn-outline-secondary">
                    </form>
                </div>

            </div>
        </div>
        <div class="card-body">
            <div class="row">

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1"> <i class="bi bi-search"></i></span>
                    </div>
                    <input type="text" class="form-control" id="myInput" onkeyup="myFunction()" placeholder="Enter details to search" title="Enter details to search">
                </div>

                <form>

                    <table id="myTable"  class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                        <thead>
                        <tr class="header">
                            <th class="th-sm">Table Number</th>
                            <th class="th-sm">Number of Seats</th>
                            <th class="th-sm"></th>
                            <th class="th-sm"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (int i = 0; i< tableList.size(); i++){ %>
                        <form action="editSingleTable" method="POST" name="editSingleTable" id="editSingleTable">
                            <tr>
                                <input type="hidden" name="tableID" value="<%=tableList.get(i).getTableID()%>">
                                <input type="hidden" name="sectionID" value="<%=sectionID%>">
                                <td><%=tableList.get(i).getTableNumber()%></td>
                                <td>
                                    <input type="number" name="seatsNumber" id="seatsNumber" class="form-control" min="1" max="100" value="<%=tableList.get(i).getSeats()%>"/>
                                </td>
                                <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Table</button></td>
                                <td> <a class="btn btn-outline-danger" style="width: 100%" href="<%=request.getContextPath()%>/deleteSingleTable?tableID=<%=tableList.get(i).getTableID()%>&sectionID=<%=tableList.get(i).getSectionID()%>">Delete Table</a></td>
                            </tr>
                        </form>
                        <%}%>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        <br>
        <form action="viewAllTablesInSection" method="POST" name="selectTableToBook" id="selectTableToBook">
            <input type="hidden" name="chosenSectionID" value="<%=sectionID%>">
            <button class="btn btn-outline-secondary" style="width: 100%" type="submit">Back</button>
        </form>


    </div>
</div>
</body>
</html>