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
												<label for="input-${prop.name}"><spring:message code="property.name.${prop.name}"/></label>
											</td>
											<td>
												<input id="input-${prop.name}" type="text" class="form-control" value="${prop.value}"/>
											</td>
										</tr>
									</c:when>
									<c:when test="${prop.type == 'BOOLEAN'}">
										<tr>
											<td>
												<label for="checkbox-${prop.name}"><spring:message code="property.name.${prop.name}"/></label>
											</td>
											<td>
												<input id="checkbox-${prop.name}" type="checkbox" class="" value="${prop.value}"/>
											</td>
										</tr>
									</c:when>
									<c:when test="${prop.type == 'SELECT_NT'}">
										<tr>
											<td>
												<label for="select-${prop.name}"><spring:message code="property.name.${prop.name}"/></label>
											</td>
											<td>
												<select class="form-control" id="select-${prop.name}">
													<c:forTokens items="${prop.selectValues}" delims="," var="value">
														<option>${value}</option>
													</c:forTokens>
												</select>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td>
												<label for="input-${prop.name}"><spring:message code="property.name.${prop.name}"/></label>
											</td>
											<td>
												<input id="input-${prop.name}" type="text" class="form-control" value="${prop.value}"/>
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
				<div class="row">
					<div class="col-sm-2 col-sm-offset-5">
						<a href="/create" class="btn btn-default text-center" role="button">Create document</a>
					</div>
				</div>
			<c:if test="${result ne null}">
				<div class="row">
					<div class="col-sm-5 col-sm-offset-2">
						<label>${result}</label>
					</div>
				</div>
			</c:if>				
			</div>	
		</div>	
	</div>
<%@include file="common/footer.jspf" %>