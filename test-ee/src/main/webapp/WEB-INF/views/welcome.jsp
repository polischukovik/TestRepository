<%@include file="common/header.jspf" %>
	<div class="container">
		<div class="row">
			<%@include file="common/navbar.jspf" %>
			<div id="left-side-bar" class="col-sm-4">
				<c:forEach items="${interfaceList}" var="item" >
					<a href="/test-face?set=${item.key}" class="btn btn-default"><spring:message code="ui.set.name.${item.key}"/></a>
					<label>${item.value.description}</label>
					<br>
					<br>
				</c:forEach>
			</div>			
		</div>
	</div>
<%@include file="common/footer.jspf" %>