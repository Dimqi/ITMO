<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="servletContext.ResultBean" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="hitHistory" class="servletContext.HitHistoryBean" scope="session" />

<%
    String x = String.valueOf(request.getAttribute("x"));
    String y = String.valueOf(request.getAttribute("y"));
    String r = String.valueOf(request.getAttribute("r"));
    Boolean result = (Boolean) request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Результат проверки</title>
    <link rel="stylesheet" href="css/resultStyles.css">
</head>
<body>

    <h1>Результат проверки точки</h1>

    <table>
        <tr><th>X</th><th>Y</th><th>R</th><th>Результат</th></tr>
        <tr>
            <td><%= x %></td>
            <td><%= y %></td>
            <td><%= r %></td>
            <td>
                <% if (result != null && result) { %>
                    <span class="hit">Попадание</span>
                <% } else { %>
                    <span class="miss">Мимо</span>
                <% } %>
            </td>
        </tr>
    </table>
    
	<div id="hrefIndex">
    	<a href="index.jsp">Вернуться к форме</a>
	</div>
	
	
	
	
<h2>Все результаты</h2>
<table id="results" border="1" cellpadding="5">
  <thead>
    <tr>
      <th>X</th>
      <th>Y</th>
      <th>R</th>
      <th>Попадание</th>
    </tr>

    
  </thead>
  <tbody>
  
  	<%
  		List<ResultBean> results= hitHistory.getHits();
        for (ResultBean res : results) {
    %>
        <tr>
            <td><%= res.getX() %></td>
            <td><%= res.getY() %></td>
            <td><%= res.getR() %></td>
            <td>
            	<% if (res.isHit()) { %>
                    <span class="hit">Попадание</span>
                <% } else { %>
                    <span class="miss">Мимо</span>
                <% } %>
            </td>
        </tr>
    <%
        }
    %>
  </tbody>
</table>
</body>
</html>
