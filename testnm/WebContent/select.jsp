<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 3983929</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).ready(function() {
                $('#country').change(function() { fillOptions(this, 'city'); });
            });
            function fillOptions(element, dropdownId) {           
                var dropdown = $('#' + dropdownId);
                
                $.getJSON('options?type=' + dropdownId + '&value=' + $(element).find(":selected").val(), function(opts) {
                    $('>option', dropdown).remove(); // Clean old options first.
                    if (opts) {

                        $.each(opts, function(key, value) {
                            dropdown.append($('<option/>').val(key).text(value));
                        });
                    } else {
                        dropdown.append($('<option/>').text('Please select ' + dropdownId));
                    }
                });
            }
        </script>
    </head>
    <body>
        <form>
            <select id="country" name="country" value="ALL">
                <c:forEach items="${country}" var="country">
                    <option value="${country.key}" ${countrySelected == country.key ? 'selected' : ''}>${country.value}</option>
                </c:forEach>
            </select>
            <select id="city" name="city">
                <option>Please select city</option>
            </select>
        </form>
    </body>
</html>