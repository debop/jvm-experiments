<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="examples.web.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="info" class="examples.web.Employee"/>
<html>
<%
    Employee emps[] = new Employee[]{
            new Employee("Joel", 10),
            new Employee("Bob", 11),
            new Employee("Mary", 12)};
%>
<head>
    <title></title>
</head>
<body>
<table border="1">
    <c:forEach var="e" items="${emps}">
        <tr>
            <td><c:out value='${e["name"]}'/></td>
            <td><c:out value='${e.empId}'/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>