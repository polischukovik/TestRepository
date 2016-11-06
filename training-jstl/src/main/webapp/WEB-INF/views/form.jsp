<%@include file="common/header.jspf" %>
<div id="left-side-bar" class="col-sm-4">
				<form:form id="propForm" commandName="propertyContainer" method="POST">
				<fieldset>
					<legend>Property List</legend>
					<form:hidden path="propertyMap"  value="${propertyContainer.propertyMap}" />
					
					<c:forEach items="${propertyContainer.propertyMap}" var="mapEntry" varStatus="mapStatus">
						<form:hidden path="propertyMap[${mapEntry.key}]"  value="${mapEntry.value}" />						
						<form:label path="propertyMap[${mapEntry.key}]">${mapEntry.key}</form:label>
						<br>
						<c:forEach items="${mapEntry.value}" var="listEntry" varStatus="listStatus">
							<form:hidden path="propertyMap[${mapEntry.key}][${listStatus.index}].name" value="" />							
							<form:label path="propertyMap[${mapEntry.key}][${listStatus.index}].name" value="${listEntry.name}" >${listEntry.name}</form:label>
							<form:input path="propertyMap[${mapEntry.key}][${listStatus.index}].value" value="${listEntry.value}" />
							<br>
						</c:forEach>
						<hr>
					</c:forEach>
					
				</fieldset>
				<input type="submit" class="button primary" form="propForm" value="Save" />
				</form:form>	
			</div>
<%@include file="common/footer.jspf" %>