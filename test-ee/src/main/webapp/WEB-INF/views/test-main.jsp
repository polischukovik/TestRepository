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
									<c:when test="${prop.type == 'FILE'}">
										<tr>
											<td>
												<label for="select-${prop.name}"><spring:message code="property.name.${prop.name}"/></label>
											</td>
											<td class="input-group">
								                <input id="file-data-${prop.name}" type="text" class="form-control" value="${prop.value}" readonly>
								                <label class="input-group-btn">
								                    <span class="btn btn-primary">
								                    	&hellip; 
								                    	<input id="file-${prop.name}" type="file" style="display: none;">
								                    </span>
								                </label>
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
					<div class="col-sm-8 col-sm-offset-2">						  
						<button onclick="createClick();" class="btn btn-default text-center" role="button">Create document</button>
					</div>
				</div>
			<c:if test="${result ne null}">
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2">
						<c:if test="${result.status == 1}">
							<label class="text-danger">${result.message}</label>
						</c:if>
						<c:if test="${result.status == 0}">
							<label class="text-success">${result.message}</label>
						</c:if>
					</div>
				</div>
			</c:if>				
			</div>	
		</div>	
	</div>
<%@include file="common/footer.jspf" %>