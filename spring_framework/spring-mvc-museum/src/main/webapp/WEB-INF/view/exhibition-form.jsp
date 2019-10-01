<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>

<head>
<title>Exhibition</title>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
	integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="
	crossorigin="anonymous"></script>

<link type="text/css" rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style-exhibition-form.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/datePicker.js"></script>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<c:choose>
				<c:when test="${not empty artists}">
					<h1>Individual Exhibition</h1>
				</c:when>
				<c:otherwise>
					<h1>Themed Exhibition</h1>
				</c:otherwise>
			</c:choose>

		</div>
	</div>

	<div id="container">
		
		<table>
			<tbody>

				<form:form modelAttribute="exhibition" method="POST">
					
					
					
					<form:hidden path="id" />

					<tr>
						<td><label>Title:</label></td>
						<td><form:input path="title" placeholder="Title"></form:input></td>
						<td class="tdError"><form:errors path="title" cssClass="error"></form:errors></td>
					</tr>

					<tr>
						<td><label>Open date:</label></td>
						<td><form:input path="openDate" placeholder="DD-MM-YYYY" id="openDate"></form:input></td>
						<td class="tdError"><form:errors path="openDate" cssClass="error"></form:errors></td>
						
					</tr>

					<tr>
						<td><label>Close date:</label></td>
						<td><form:input path="closeDate" placeholder="DD-MM-YYYY" id="closeDate"></form:input></td>
						<td class="tdError">
							<form:errors path="closeDate" cssClass="error"/>
							<br>
							<form:errors path="" cssClass="error" />
						</td>
					</tr>

					<tr>
						<td><label>Ticket price:</label></td>
						<td><form:input path="ticketPrice" placeholder="0.0"></form:input></td>
						<td class="tdError"><form:errors path="ticketPrice" cssClass="error"></form:errors></td>
					</tr>

					<tr>
						<td><label>Curator:</label></td>
						<td><form:select path="curator" class="comboBox"
								multiple="false">
								<form:options items="${curators}" itemValue="id" />
							</form:select></td>
					</tr>

					<c:choose>
						<c:when test="${not empty artists}">
							<tr>
								<td><label>Artist:</label></td>
								<td><form:select path="artist" class="comboBox"
										multiple="false">
										<form:options items="${artists}" itemValue="id" />
									</form:select></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td><label>Theme:</label></td>
								<td><form:input path="theme" placeholder="Theme"></form:input></td>
								<td class="tdError"><form:errors path="theme" cssClass="error"></form:errors></td>
							</tr>
						</c:otherwise>
					</c:choose>

					<tr>
						<td><label>Exhibits:</label></td>
						<td><input type="submit" value="Edit exhibits"
							class="myButton"
							onclick="this.form.action='showFormForChoosingExhibits';" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save"
							onclick="this.form.action='saveExhibition';" /></td>
					</tr>



				</form:form>

			</tbody>
		</table>



	</div>

</body>

</html>










