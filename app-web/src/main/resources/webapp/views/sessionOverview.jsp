<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<jsp:include page="/include/header.jsp" />

<table class="table table-striped table-bordered">
	<thead>
		<th>name</th>
		<th>start</th>
		<th>end</th>
	</thead>

	<tbody>
		<c:forEach items="${sessions}" var="session">
			<tr>
				<td>${session.sessionCode} - ${session.name}</td>
				<td><joda:format value="${session.startTime}" style="SM"/></td>
				<td><joda:format value="${session.endTime}" style="SM"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/include/footer.jsp" />