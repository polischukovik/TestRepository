package testnm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PageServlet extends HttpServlet {
	private OptionDAO optionDAO = new OptionDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("country", optionDAO.find("country", "ALL"));
		req.setAttribute("countrySelected", "GB");
		req.getRequestDispatcher("/select.jsp").forward(req, resp);
	}

}
