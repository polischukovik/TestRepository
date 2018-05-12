<%@ include file="common/header.jspf"%>
<%@ include file="common/navbar.jspf" %>

<div class="container">
	<div class="row center">
		<div class="col-sm-12 col-md-8 col-md-offset-2 col-lg-9 col-lg-offset-1.5">
			<p class="h2">My TODO-List:</p>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Description</th>
						<th>Date</th>
						<th>Active</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${todos}" var="todo">
						<tr>
							<td><a href="/todo?id=${todo.id}">${todo.description}</a></td>
							<td><fmt:formatDate value="${todo.date}" pattern="dd/MM/yyy" /></td>
							<td>
								<c:if test="${todo.isActive == true}">
									<input type="checkbox" checked disabled>
								</c:if>
								<c:if test="${todo.isActive == false}">
									<input type="checkbox" disabled>
								</c:if>
</td>
							<td><a href="/delete-todo?id=${todo.id}" class="btn btn-warning">Delete</a></td>							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<a class="btn btn-success" href="/todo">Add</a>
		</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>