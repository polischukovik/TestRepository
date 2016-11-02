<%@include file="common/header.jspf" %>
	<div class="container">
		<div class="row">
			<%@include file="common/navbar.jspf" %>
			
			<div id="left-side-bar" class="col-sm-4">
				<c:forEach items="${categories}" var="category">
					<table class="table">
						<thead>
							<tr>
								<th colspan="2">
									<spring:message code="property.category.${category.key}"/>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${category.value}" var="prop">				
								<c:choose >
									<c:when test="${prop.type == 'STRING'}">	
										<tr>
											<td>
												<label for="input-string-${prop.name}">${prop.name}</label>
											</td>
											<td>
												<input id="input-string-${prop.name}" type="text" class="form-control" value="${prop.value}"/>
											</td>
										</tr>
									</c:when>
									<c:when test="${prop.type == 'BOOLEAN'}">
										<tr>
											<td>
												<label for="input-string-${prop.name}">${prop.name}</label>
											</td>
											<td>
												<input id="input-string-${prop.name}" type="checkbox" class="" value="${prop.value}"/>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td>
												<label for="input-string-${prop.name}">${prop.name}</label>
											</td>
											<td>
												<input id="input-string-${prop.name}" type="text" class="form-control" value="${prop.value}"/>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>									
			</div>
			<div id="main-content" class="col-sm-8">
				
			</div>
		</div>	
	</div>
<%@include file="common/footer.jspf" %>