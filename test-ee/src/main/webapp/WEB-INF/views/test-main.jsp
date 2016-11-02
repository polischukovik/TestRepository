<%@include file="common/header.jspf" %>
<%@include file="common/navbar.jspf" %>
<div id="property-block">
	<c:forEach items="${categories}" var="category">
		<div class="category">
			<c:forEach items="${category.value}" var="prop">						
						<c:choose >
						<c:when test="${prop.type == 'STRING'}">
			<form class="form-horizontal" role="form">
				<div class="h2">
					<label>${category.key}</label>
				</div>
				<div class="form-group">
					
				</div>
			</form>
			</c:forEach>
<!-- 			<table class="table"> -->
<!-- 				<thead> -->
<!-- 					<tr> -->
<%-- 						<th colspan="2">${category.key}</th> --%>
<!-- 					</tr>					 -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
<%-- 					<c:forEach items="${category.value}" var="prop">						 --%>
<%-- 						<c:choose > --%>
<%-- 							<c:when test="${prop.type == 'STRING'}"> --%>
<!-- 								<tr class="form-group"> -->
<!-- 									<td> -->
<%-- 										<label for="input-string-${prop.name}">${prop.name}</label> --%>
<!-- 									</td> -->
<!-- 									<td> -->
<%-- 										<input id="input-string-${prop.name}" type="text" class="form-control" value="${prop.value}"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:when> --%>
<%-- 							<c:when test="${prop.type == 'BOOLEAN'}"> --%>
<!-- 								<tr> -->
<!-- 									<td> -->
<%-- 										<label for="input-string-${prop.name}">${prop.name}</label> --%>
<!-- 									</td> -->
<!-- 									<td> -->
<!-- 										<div class="checkbox"> -->
<%-- 											<input id="input-string-${prop.name}" type="checkbox" value="${prop.value}"/> --%>
<!-- 										</div> -->
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<!-- 								<tr class="form-group"> -->
<!-- 									<td> -->
<%-- 										<label for="input-string-${prop.name}">${prop.name}</label> --%>
<!-- 									</td> -->
<!-- 									<td> -->
<%-- 										<input id="input-string-${prop.name}" type="text" class="form-control" value="${prop.value}"/> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:otherwise> --%>
<%-- 						</c:choose> --%>
<%-- 					</c:forEach> --%>
<!-- 				</tbody> -->
<!-- 			</table>			 -->
		</div>
	</c:forEach>
</div>

<%@include file="common/footer.jspf" %>