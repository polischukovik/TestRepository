<%@include file="common/header.jspf" %>
	<div class="container">
		<div class="row">
			<%@include file="common/navbar.jspf" %>
			<div id="left-side-bar" class="col-sm-4">
				<form:form id="propForm" commandName="propertyContainer" method="POST">
				<fieldset class="form-group">
					<legend class="h4">Property List</legend>
					<form:hidden path="propertyMap"/>
					
					<c:forEach items="${propertyContainer.propertyMap}" var="mapEntry" varStatus="mapStatus">
						<table class="table table-condensed">
						<thead>
							<tr>
								<th colspan="2">
									<form:hidden path="propertyMap[${mapEntry.key}]"  />						
									<form:label path="propertyMap[${mapEntry.key}]" cssClass="form-control-label"><spring:message code="property.category.${mapEntry.key}"/></form:label>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mapEntry.value}" var="listEntry" varStatus="listStatus">
							<tr>
								<td>
									<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].name"/>
									<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].group"/>
									<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].type"/>
									<form:label path="propertyMap[${mapEntry.key}][${listStatus.index}].name" cssClass="text-muted"><spring:message code="property.name.${listEntry.name}"/></form:label>
								</td>
								<td>
									<c:choose>
									<c:when test="${listEntry.type == 'BOOLEAN'}">
										<form:checkbox path="propertyMap[${mapEntry.key}][${listStatus.index}].bool" value="${listEntry.bool}" cssErrorClass="has-danger" cssClass="form-control" />
									</c:when>
									<c:when test="${listEntry.type == 'SELECT_NT'}">
										<form:select path="propertyMap[${mapEntry.key}][${listStatus.index}].value" items="${listEntry.selectValues}" cssErrorClass="has-danger" class="form-control" >
											<form:option value="NONE"> --SELECT--</form:option>
    										<form:options items="${listEntry.selectValues}"></form:options>
										</form:select>	
										<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].bool"/>
										<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].selectValues"/>																			
									</c:when>
									<c:otherwise>
										<form:input path="propertyMap[${mapEntry.key}][${listStatus.index}].value" value="${listEntry.value}" cssErrorClass="has-danger" cssClass="form-control"/>
										<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].bool"/>
										<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].selectValues"/>
									</c:otherwise>
									</c:choose>									
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<form:errors path="propertyMap[${mapEntry.key}][${listStatus.index}].value" cssClass="has-warning form-control"/>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</table>
					</c:forEach>
				</fieldset>
				</form:form>	
			</div>
			<div id="main-content" class="col-sm-8">
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2">
						<input type="submit" class="btn btn-default" form="propForm" value="Save" />  
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