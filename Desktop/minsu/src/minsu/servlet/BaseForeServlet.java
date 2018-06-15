package minsu.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import minsu.dao.CategoryDAO;
import minsu.dao.OrderDAO;
import minsu.dao.OrderItemDAO;
import minsu.dao.ProductDAO;
import minsu.dao.ProductImageDAO;
import minsu.dao.PropertyDAO;
import minsu.dao.PropertyValueDAO;
import minsu.dao.ReviewDAO;
import minsu.dao.UserDAO;
import minsu.util.Page;

public class BaseForeServlet extends HttpServlet{

	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			int start= 0;
			int count = 10;
			try {
				start = Integer.parseInt(request.getParameter("page.start"));
			} catch (Exception e) {
				
			}
			
			try {
				count = Integer.parseInt(request.getParameter("page.count"));
			} catch (Exception e) {
			}
			
			Page page = new Page(start,count);
			
			String method = (String) request.getAttribute("method");

			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
					javax.servlet.http.HttpServletResponse.class,Page.class);
			
			String redirect = m.invoke(this,request, response,page).toString();
			
			if(redirect.startsWith("@"))
				response.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
				response.getWriter().print(redirect.substring(1));
			else
				request.getRequestDispatcher(redirect).forward(request, response);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
