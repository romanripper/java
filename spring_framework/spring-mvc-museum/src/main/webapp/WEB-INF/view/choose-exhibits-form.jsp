<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Choose Exhibits</title>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style-list.css">

</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h1>Available Exhibits</h1>
		</div>
	</div>

	<div id="container">
		<div id="content">

			<c:choose>
				<c:when test="${not empty exhibits}">

					<form:form action="chooseExhibits"
						modelAttribute="exhibit_idsFormWrapper" method="POST">

						<table class="list">
							<tr id=buttonsToolBar>
								<td colspan="4" class=button_cell><input type="submit"
									value="Confirm" class="myButton" id="buttonConfirm" /></td>
							</tr>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Description</th>
								<th>Select</th>
							</tr>

							<c:forEach var="exhibit" items="${exhibits}">

								<tr>
									<td>${exhibit.id}</td>
									<td>${exhibit.name}</td>
									<td>${exhibit.description}</td>
									<td class="td_last"><form:checkbox path="exhibit_ids"
											value="${exhibit.id}" /></td>
								</tr>

							</c:forEach>

						</table>

					</form:form>
				</c:when>

				<c:otherwise>
					<br/>
					<br/>
					<h3 style="text-align: center; color: #c91010">Sorry there are no available
						exhibits for the time you planned new exhibition</h3>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

</body>
</html>