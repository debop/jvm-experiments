<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    String sharedString = "Hi Mom!";
    int thisPageCount = 0;

    public String whatDinner() {
        return "Chicken soup";
    }
%>
<html>
<head>
    <title></title>
</head>
<body>
<%= sharedString %><br/>
What's for dinner?<br/>
<%= whatDinner() %><br/>
<strong>You asked <%= (thisPageCount++) %>
</strong> times<br/>
</body>
</html>