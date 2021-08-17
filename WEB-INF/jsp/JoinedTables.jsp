<%@ page contentType="text/html;"%>
<%@ page import="java.util.*" %>
<%@ page import="pkg.User" %>
<%@ page import="pkg.ServableTable" %>
<%@ page import="pkg.Section" %>
<%@ page import="pkg.JoinedTables" %>
<%
    User user = (User) session.getAttribute("user");
    Section section = (Section) request.getAttribute("section");
    ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors");

%>
<!DOCTYPE html>
<html>
<head>
    <title>Joined Tables From <%=section.getName()%>></title>
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
    <div class="card">
        <div class="card w-100">
            <div class="card-header">
                <h2 style="text-align: center">Joined Tables in "<%=section.getName()%>"</h2>
            </div>
            <div class="card-body">
                <em><h4 style="text-align: center"><%=section.getJoinedTables().size()%> Joined Table/s</h4></em>
            </div>
        </div>
        <br>
        <%if (errors!=null){%>
            <%if (errors.size()>0){%>
                <div class="container">
                    <b>
                    <div class="card-body bg-danger" style="text-align: center; display: flex; flex-direction: column">
                        <div>
                            Error!
                        </div>
                        <div>
                            Joined Table was not created
                        </div>
                        <div>
                            Reason: <%=errors.get(0)%>
                        </div>
                    </div>
                    </b>
                </div>
                <br>
            <%}%>
        <%}%>

        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <table id="dtBasicExample" class="table table-borderless  table-sm" cellspacing="0" width="100%" style='table-layout:fixed'>
                        <tbody>
                        <tr>
                            <td>
                                <a class="test btn btn-outline-primary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Create Joined Table</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form action="viewSection" method="GET" name="selectTableToBook" id="selectTableToBook">
                                    <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                                    <button class="btn btn-outline-secondary" style="width: 100%" type="submit">Back</button>
                                </form>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="collapse multi-collapse" id="multiCollapseExample1">
            <br>
            <div class="container">
                <div class="card w-100">
                    <div class="card-body">
                        <div class="row">
                            <form action="createJoinedTable" method="POST" name="createJoinedTable" id="createJoinedTable">
                                <h4 style="text-align: center">Create Joined Table</h4>
                                <label for="numberSeats">Number of Seats:</label>
                                <input required type="number" name="numberSeats" id="numberSeats" class="form-control"/>
                                <br>
                                <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text" id="basic-addon1"> <i class="bi bi-search"></i></span>
                                    </div>
                                    <input type="text" class="form-control" id="myInput" onkeyup="myFunction()" placeholder="Enter details to search" title="Enter details to search">
                                </div>
                                <div class="input-group">
                                    <table id="myTable"  class="table table-striped table-bordered table-sm" cellspacing="0" width="100%" >
                                        <thead>
                                        <tr class="header">
                                            <th></th>
                                            <th class="th-sm">Table Number</th>
                                            <th class="th-sm">Number of Seats</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <% for (ServableTable servableTable : section.getServableTables()){ %>
                                            <tr>
                                                <td>
                                                    <input class="form-check" style="margin: auto" type="checkbox" name="<%=servableTable.getTableID()%>" value="<%=servableTable.getTableID()%>">
                                                </td>
                                                <td>
                                                    <%=servableTable.getTableNumber()%>
                                                </td>
                                                <td>
                                                    <%=servableTable.getSeats()%>
                                                </td>
                                            </tr>
                                        <%}%>
                                        </tbody>
                                    </table>
                                </div>
                                <input type="submit" class="btn btn-primary w-100" value="Create Joined Table">
                            </form>
                        </div>
                    </div>
                    <a class="test btn btn-outline-secondary w-100" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Collapse</a>

                </div>
            </div>
        </div>
        <br>
        <div class="container">
            <div class="card w-100">
                <div class="card-body">
                    <div class="row">
                        <div class="input-group">
                            <table id=""  class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
                                <thead>
                                <tr class="header">
                                    <th class="th-sm">Tables Joined</th>
                                    <th class="th-sm">Number Of Seats</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <% for (JoinedTables joinedTable : section.getJoinedTables()){%>
                                    <form action="editJoinedTable" method="POST" name="editJoinedTable" id="editJoinedTable">
                                        <tr>
                                            <input type="hidden" name="joinedTablesID" value="<%=joinedTable.getJoinedTablesID()%>">
                                            <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
                                            <td>
                                                <div style="display: flex; justify-content: center; flex-direction: column">
                                                    <%for (ServableTable servableTable : joinedTable.getJoinedTablesList()){%>
                                                    <div>Table - <b><%=servableTable.getTableNumber()%></b></div>
                                                    <%}%>
                                                </div>
                                            </td>
                                            <td>
                                                <input type="number" name="seatsNumber" id="seatsNumber" class="form-control" min="1" max="100" value="<%=joinedTable.getNumberSeats()%>"/>
                                            </td>
                                            <td>
                                            <td><button class="btn btn-outline-primary d-block btn-user w-100" type="submit">Edit Table</button></td>
                                            </td>
                                            <td style=" height: 100%">
                                                <div>
                                                    <a class="btn btn-outline-danger form-check" style="width: 100%;" href="<%=request.getContextPath()%>/deleteJoinedTable?joinedTableID=<%=joinedTable.getJoinedTablesID()%>&sectionID=<%=section.getSectionID()%>">Delete Joined Table</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </form>
                                <%}%>
                                </tbody>
                            </table>
                            </di>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <form action="viewSection" method="GET" name="selectTableToBook" id="selectTableToBook">
            <input type="hidden" name="sectionID" value="<%=section.getSectionID()%>">
            <button class="btn btn-outline-secondary" style="width: 100%" type="submit">Back</button>
        </form>
    </div>
</div>
</div>
<script>
    function myFunction() {
        // Declare variables
        var input, filter, table, tr, td, i, occurrence;

        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        // Loop through all table rows, and hide those who don't match the search query
        for (i = 0; i < tr.length; i++) {
            occurrence = false; // Only reset to false once per row.
            td = tr[i].getElementsByTagName("td");
            for(var j=0; j< td.length; j++){
                currentTd = td[j];
                if (currentTd ) {
                    if (currentTd.innerHTML.toUpperCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
                        occurrence = true;
                    }
                }
            }
            if(!occurrence){
                tr[i].style.display = "none";
            }
        }
    }

</script>
</body>
</html>
