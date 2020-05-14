+<%--
  Created by IntelliJ IDEA.
  User: rothe
  Date: 2020-04-17
  Time: 09:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Application object in JSP</title>
</head>

<body>

<form action="Hej">
  <%--@declare id="fname"--%><label for="fname">First name:</label><br>
  <input type = "text" id="fname" name="fname" value = "Thomas"></br>
    <%--@declare id="lname"--%><label for="lname">Last name:</label><br>
    <input type = "text" id="lname" name="lname" value = "Thomas"></br>
  <input type="submit" value="Submit">
</form>

<%
  Integer hitsCount = (Integer)application.getAttribute("hitCounter");
  if( hitsCount ==null || hitsCount == 0 ) {
    /* First visit */
    out.println("Welcome to my website!");
    hitsCount = 1;
  } else {
    /* return visit */
    out.println("Welcome back to my website!");
    hitsCount += 1;
  }
  application.setAttribute("hitCounter", hitsCount);
%>

  <p>Total number of visits: <%= hitsCount%></p>


</body>
</html>
