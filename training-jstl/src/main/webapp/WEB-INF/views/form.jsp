<%@include file="common/header.jspf" %>
<div id="left-side-bar" class="col-sm-4">
				<form:form id="propForm" commandName="propertyComponent" method="POST">
				<fieldset>
					<legend>Property List</legend>
					<form:errors path="*" element="div"/>
						
					<form:label path="name">Property name</form:label>
					<form:input path="name" value="${propertyContainer.name}"  cssErrorClass="input-text-error" />
					<br>										
					<form:label path="value">Property value</form:label>
					<form:input path="value" value="${propertyContainer.value}" cssErrorClass="input-text-error" />
					<br>
					<form:label path="type">Property type</form:label>
					<form:input path="type" value="${propertyContainer.type}"  cssErrorClass="input-text-error" />
					<br>
					<form:label path="selectValues">Select values: </form:label>
					<form:input path="selectValues" value="${propertyContainer.selectValues}" cssErrorClass="input-text-error" />
					<br>
				</fieldset>
				<input type="submit" class="button primary" form="propForm" value="Save" />
				</form:form>	
			</div>
<%@include file="common/footer.jspf" %>