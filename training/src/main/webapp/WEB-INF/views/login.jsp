<%@ include file="common/header.jspf"%>

<p>
	<font color="red">${errorMessage}</font>
</p>

<div class="container">
	<div class="row center">
		<div class="col-sm-12 col-md-4 col-md-offset-4 col-lg-3 col-lg-offset-4.5">
			<form action="/login" method="POST">
				<div class="form-group">
					<label for="username-id">User:</label>
					<input id="username-id" name="username" type="text" class="form-control"/>
				</div>
				<div class="form-group">
					<label for="password-id">Password:</label>
					<input id="password-id" name="password" type="password" class="form-control"/>
				</div>	
				<button type="submit" class="btn btn-success">Submit</button>
			</form>
		</div>
	</div>	
</div>
<%@ include file="common/footer.jspf"%>