<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Exhibitions</title>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style-list.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/dialog.js"></script>
</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h1>Exhibitions</h1>
		</div>
	</div>

	<div id="container">
		<div id="content">



			<table>
				<tr id=buttonsToolBar>
					<td colspan="6" class=button_cell><input type="button"
						value="Plan Exhibition" class="myButton" id="planExhibitionButton">
					</td>
				</tr>
				<tr>
					<th>Id</th>
					<th>Title</th>
					<th>Opening Date</th>
					<th>Closing Date</th>
					<th>Ticket Price</th>
					<th>Action</th>
				</tr>

				<c:forEach var="exhibition" items="${exhibitions}">

					<c:url var="updateLink" value="/exhibition/showFormForUpdate">
						<c:param name="exhibitionId" value="${exhibition.id }"></c:param>
					</c:url>

					<c:url var="deleteLink" value="/exhibition/deleteExhibition">
						<c:param name="exhibitionId" value="${exhibition.id }"></c:param>
					</c:url>

					<tr>
						<td>${exhibition.id}</td>
						<td>${exhibition.title}</td>
						<td>${exhibition.openDate}</td>
						<td>${exhibition.closeDate}</td>
						<td>${exhibition.ticketPrice}</td>
						<td class="td_last"><a href="${updateLink}">Update</a> | <a
							href="${deleteLink}"
							onclick="if(!(confirm('Are you sure to delete this exhibition'))) return false">Delete</a>
						</td>
					</tr>

				</c:forEach>

			</table>
		</div>
	</div>


	<div id="myDialog" class="dialog">
		<!-- Modal content -->
		<div class="dialog-content">
			<form:form action="showFormForAdd">
				<p>
					<label style="margin-right: 20px">Choose type of exhibition:</label>
					<select id="typeComboBox">
							<option>Individual</option>
							<option>Themed</option>
					</select>
					<br><br>
					
					<input class="myButton" id="cancelDialogButton" type="button" value="Cancel">
					<input class="myButton" id="confirmDialogButton" type="button" value="Confirm">
				</p>
			</form:form>
		</div>

	</div>

</body>
</html>