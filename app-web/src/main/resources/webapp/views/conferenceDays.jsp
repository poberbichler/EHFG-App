<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/include/header.jsp" />

<sf:form method="post" modelAttribute="dayForm" class="form form-horizontal">
	<ul id="dayList">
		<c:forEach items="${dayForm.days}" var="day" varStatus="status">
			<li>
				<input name="days[${status.index}].deleted" value="false" hidden />
				<input id="day-${status.index}" name="days[${status.index}].day" value="${day.day}" /> - 
				<input name="days[${status.index}].description" value="${day.description}"/>
				<a href="#" onclick="removeDay(this)">remove</a>
			</li>
		</c:forEach>
	</ul>
	<input value="add" class="btn btn-default" id="addRow" />
	<input type="submit" value="save" class="btn btn-primary"/>
</sf:form>
<jsp:include page="/include/footer.jsp" />

<script>
	$("input[id^='day']").datepicker({dateFormat: 'yy-mm-dd'});
	$("#addRow").click(function() {
		var index = $('#dayList li').length;
		
		var newRow = '<li>';
		newRow += '<input name="days[' + index + '].id" value="false" hidden/>';
		newRow += '<input id="day" name="days[' + index + '].day" value="" /> - ';
		newRow += '<input name="days[' + index + '].description" value="" /> ';
		newRow += '<a href="#" onclick="removeDay(this)">remove</a>';
		newRow += '</li>';
		
		$('#dayList').append(newRow);
		$("input[id^='day']").datepicker({dateFormat: 'yy-mm-dd'});
	});
	
	function removeDay(element) {
		var parent = $(element).parent();
		parent.find('input[name$="deleted"]').attr('value', true);
		parent.hide();
	}
</script>