<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ page import="servletContext.ResultBean" %>
<%@ page import="java.util.List" %>
<jsp:useBean id="hitHistory" class="servletContext.HitHistoryBean" scope="session" />


<html>
<head>
	<meta charset="UTF-8">
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<title>Страница для ввода</title>
  	<link rel="stylesheet" href="css/styles.css">
  	<script src="js/script.js"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/decimal.js/10.4.3/decimal.min.js"></script>
</head>
<body>
<table class="header" width="100%">
<tr>
    <td align="center">
      <h1>Федоров Дмитрий Александрович</h1>
      <p>P3206</p>
      <p>Вариант: 467846</p>
    </td>
</tr>
</table>

<%
    double firstR = 1; 
    List<ResultBean> results1 = hitHistory.getHits();
    if (!results1.isEmpty()) {
        firstR = results1.get(0).getR();
    }
%>

<div data-r="<%= firstR %>"></div>


  <table id="toast-container">
    <tbody></tbody>
  </table>
  
	<c:if test="${not empty error}">
    	<p style="color:red">${error}</p>
	</c:if>

<form id="input-form" name="form" align="center" method="post" action="controller">
<table class="input_table" border="2" cellpadding="5">
    <tr>
      <td colspan="2" align="center" >
        <h1>Форма для ввода</h1>
      </td>
    </tr>
    <tr>
      <td><label for="change_x">X:</label></td>
      <td>
      	<select name="x">
      		<option value=""></option>
  			<option value="-5">-5</option>
  			<option value="-4.5">-4.5</option>
  			<option value="-4">-4</option>
  			<option value="-3.5">-3.5</option>
  			<option value="-3">-3</option>
  			<option value="-2.5">-2.5</option>
  			<option value="-2">-2</option>
  			<option value="-1.5">-1.5</option>
  			<option value="-1">-1</option>
  			<option value="-0.5">-0.5</option>
  			<option value="0">0</option>
  			<option value="0.5">0.5</option>
  			<option value="1">1</option>
  			<option value="1.5">1.5</option>
  			<option value="2">2</option>
  			<option value="2.5">2.5</option>
  			<option value="3">3</option>
		</select>
      </td>
    </tr>
    <tr>
      <td><label for="change_y">Y:</label></td>
      <td><input type="text" id="change_y" name="y" placeholder="от -3 до 5"></td>
    </tr>
    <tr>
      <td><label for="r">R:</label></td>
      <td>
        <button type="button" class="r-btn" data-value="1.0">1</button>
        <button type="button" class="r-btn" data-value="1.5">1.5</button>
        <button type="button" class="r-btn" data-value="2.0">2</button>
        <button type="button" class="r-btn" data-value="2.5">2.5</button>
        <button type="button" class="r-btn" data-value="3.0">3</button>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="submit" value="Отправить">
      </td>
    </tr>
</table>
</form>


<table id="svg_table">
  <tr>
    <td>
      <svg width="550" height="550" xmlns="http://www.w3.org/2000/svg">
        <!-- Центр -->
        <rect x="275" y="275" width="50" height="50" fill="lightblue" />        
        <path d="M275,275 L225,275 A50,50 0 0,0 275,325" fill="lightblue"/>
        <polygon points="275,275 275,250 325,275" fill="lightblue"/>

        <!-- Ось X -->
        <line x1="50" y1="275" x2="500" y2="275" stroke="black" stroke-width="2"/>
        <!-- Черточки X -->
        <line data-value="-1" data-axis="x" x1="225" x2="225" y1="270" y2="280" stroke="black"/>
        <text class="r-neg" data-value="-1" data-axis="x" x="225" y="265" font-size="14">-R</text>

        <line data-value="-0.5" data-axis="x" x1="250" x2="250" y1="270" y2="280" stroke="black"/>
        <text class="r-half-neg" data-value="-0.5" data-axis="x"x="250"  y="265" font-size="14">-R/2</text>

        <line data-value="0.5" data-axis="x" x1="300" x2="300" y1="270" y2="280" stroke="black"/>
        <text class="r-half-pos" data-value="0.5" data-axis="x" x="300" y="265" font-size="14">R/2</text>

        <line data-value="1" data-axis="x" x1="325" x2="325" y1="270" y2="280" stroke="black"/>
        <text class="r-pos" data-value="1" data-axis="x" x="325" y="265" font-size="14">R</text>

        <line x1="492" y1="272" x2="500" y2="275" stroke="black"/>
        <line x1="492" y1="278" x2="500" y2="275" stroke="black"/>
        <text x="510" y="275" font-size="14">x</text>

        <!-- Ось Y -->
        <line x1="275" y1="50" x2="275" y2="500" stroke="black" stroke-width="2"/>
        <!-- Черточки Y -->
        <line data-value="1" data-axis="y" y1="225" y2="225" x1="270" x2="280" stroke="black"/>
        <text class="r-pos" data-value="1" data-axis="y" y="225" x="285" font-size="14">R</text>

        <line data-value="0.5" data-axis="y" y1="250" y2="250" x1="270" x2="280" stroke="black"/>
        <text class="r-half-pos" data-value="0.5" data-axis="y" y="250" x="285" font-size="14">R/2</text>

        <line data-value="-0.5" data-axis="y" y1="300" y2="300" x1="270" x2="280" stroke="black"/>
        <text class="r-half-neg" data-value="-0.5" data-axis="y" y="300" x="285" font-size="14">-R/2</text>

        <line data-value="-1" data-axis="y" y1="325" y2="325"  x1="270" x2="280" stroke="black"/>
        <text class="r-neg" data-value="-1" data-axis="y" y="325" x="285" font-size="14">-R</text>

        <!-- Метки осей -->
        <line x1="275" y1="50" x2="267" y2="58" stroke="black"/>
        <line x1="275" y1="50" x2="283" y2="58" stroke="black"/>
        <text x="285" y="55" font-size="14">y</text>

        <!-- Результаты -->
        <%
        List<ResultBean> results = hitHistory.getHits();
        for(ResultBean result: results){
            double cx = result.getX() * 50 + 275;
            double cy = 275 - result.getY().doubleValue() * 50;
        %>
        <circle data-x="<%= result.getX() %>" data-r="<%= result.getR() %>" data-y="<%= result.getY() %>" cx="<%= cx %>" cy="<%= cy %>" r="5" fill="<%= result.isHit() ? "green" : "red" %>"/>
        <%
        }
        %>
      </svg>
    </td>
  </tr>
</table>



<h2>Результаты</h2>
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