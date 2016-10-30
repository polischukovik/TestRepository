<%@ include file="common/header.jspf" %>
<%@ include file="common/navbar.jspf" %>

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-8 col-md-offset-2 col-lg-9 col-lg-offset-1.5">
			<h1>Modify TODO</h1>
			<form:form method="POST" commandName="todo">
				<div class="form-group">
					<form:label path="description" for="descr-id">Description</form:label>
					<form:input path="description" id="descr-id" type="text" placeholder="Place description here" /><br>
					<form:errors path="description" cssClass="text-warning" />
				</div>
				<div class="form-group">
					<form:label path="date" for="date-id">Date</form:label>
					<form:input path="date" id="date-id" type="text" placeholder="dd/mm/yyy" /><br>
					<form:errors path="date" cssClass="text-warning" />
				</div>
				<div class="form-group">
					<form:label path="isActive" for="complete-id">Date</form:label>
					<form:checkbox path="isActive" id="complete-id"/><br>
					<form:errors path="isActive" cssClass="text-warning" />
				</div>
				<button type="submit" class="btn btn-success">Save</button>
				<a href="/todo-list" class="btn btn-warning">Back</a>
			</form:form>
		</div>
	</div>
</div>

<script>
	$('#date-id').datepicker({
		format : 'dd/mm/yyyy'
	});
</script>
<%@ include file="common/footer.jspf" %>