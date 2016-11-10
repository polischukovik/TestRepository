<%@include file="common/header.jspf" %>
	<div class="container">
		<div class="row">
			<%@include file="common/navbar.jspf" %>
			<div id="left-side-bar" class="col-sm-4">
				<c:forEach items="${userInterfaceContainer.userInterfaceSets}" var="item" >
					<a href="/test-face?set=${item.name}" class="btn btn-default"><spring:message code="ui.set.name.${item.name}"/></a>
					<label>${item.description}</label>
					<br>
					<br>
				</c:forEach>
			</div>			
		</div>
	</div>
<%@include file="common/footer.jspf" %>