package testnm;


import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class ServletDemo1 extends HttpServlet {
	private OptionDAO optionDAO = new OptionDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String type = request.getParameter("type"); // Returns "country" or "state".
	    String value = request.getParameter("value"); // Value of selected country or state.
	    Map<String, String> options = optionDAO.find(type, value); // Do your thing to obtain them from DB. Map key is option value and map value is option label.
	    String json = new Gson().toJson(options); // Convert Java object to JSON string.

	    response.setContentType("application/json; charset=UTF-8"); // Inform client that you're returning JSON.
	    //setCharacterEncoding("UTF-8"); // Important if you want world domination.
	    response.getWriter().write(json); // Write JSON string to response.
	}
}
