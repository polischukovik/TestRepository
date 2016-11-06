<%@include file="common/header.jspf" %>
	<div class="container">
		<div class="row">
			<%@include file="common/navbar.jspf" %>
			<div id="left-side-bar" class="col-sm-4">
				<form:form id="propForm" commandName="propertyContainer" method="POST">
				<form:errors path="*">
					<div class="msg error">
						<h4>ATTENTION!</h4>
						<p>Please make the following correction(s) before proceeding.</p>
					</div>
				</form:errors>
				<fieldset>
					<legend>Property List</legend>

					<c:forEach items="${propertyContainer.propertyMap}" var="mapEntry" varStatus="mapStatus">
						<form:hidden path="propertyMap[${mapEntry.key}]"  value="${mapEntry.key}" />
						<table class="table">
							<thead>
								<tr>
									<th colspan="2">${mapEntry.key}</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${mapEntry.value}" var="propertyComp" varStatus="propertyStatus">
								<form:hidden path="propertyComp"  value="${mapEntry.key}" />
								<tr>
									<td>
										<form:label path="propertyMap[${mapEntry.key}]"	value="${mapEntry.key}" />
										<label for="propertyMap${mapEntry.key}">
											<spring:message code="property.name.${propertyComp.name}" />
										</label>
									</td>
									<td>
									<c:choose>
									<c:when test="${prop.type == 'STRING'}">									
										<form:input path="propertyMap[${mapEntry.key}]" value="${propertyComp.value}" cssClass="form-control" /> 
									</c:when>
									<c:when test="${prop.type == 'BOOLEAN'}">
										<form:input path="propertyMap[${mapEntry.key}]" type="checkbox" value="${propertyComp.value}" cssClass="form-control" />
									</c:when>
									<c:when test="${prop.type == 'SELECT_NT'}">
										<select class="form-control" id="propertyMap[${mapEntry.key}]">
										<c:forTokens items="${propertyComp.selectValues}" delims="," var="value">
											<option>${value}</option>
										</c:forTokens>
										</select>
									</c:when>
									<c:when test="${prop.type == 'FILE'}">
									</c:when>
									<c:otherwise>										
										<form:input path="propertyMap[${mapEntry.key}]" value="${propertyComp.value}" cssClass="form-control" />
									</c:otherwise>
									</c:choose>
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
						<div class="field">
							<div class="input">
								<input type="submit" class="button primary" form="propForm" value="Save" />
							</div>
						</div>					  
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