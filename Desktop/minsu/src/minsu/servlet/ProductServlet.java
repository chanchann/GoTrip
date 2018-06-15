package minsu.servlet;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minsu.bean.Category;
import minsu.bean.Product;
import minsu.bean.PropertyValue;
import minsu.util.Page;

public class ProductServlet extends BaseBackServlet {

	
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		
		String name= request.getParameter("name");
		String subTitle= request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		Product p = new Product();

		p.setCategory(c);
		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		

		
		productDAO.add(p);
		return "@admin_product_list?cid="+cid;
	}

	
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		productDAO.delete(id);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";		
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		
		propertyValueDAO.init(p);
		
		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
		
		request.setAttribute("pvs", pvs);
		
		return "admin/editProductValue.jsp";		
	}

	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		
		PropertyValue pv =propertyValueDAO.get(pvid);
		pv.setValue(value);
		propertyValueDAO.update(pv);
		return "%success";
	}
	
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);

		int id = Integer.parseInt(request.getParameter("id"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		String subTitle= request.getParameter("subTitle");
		String name= request.getParameter("name");
		
		Product p = new Product();

		p.setName(name);
		p.setSubTitle(subTitle);
		p.setOrignalPrice(orignalPrice);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		p.setId(id);
		p.setCategory(c);		

		productDAO.update(p);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		
		List<Product> ps = productDAO.list(cid, page.getStart(),page.getCount());
		
		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		
		request.setAttribute("ps", ps);
		request.setAttribute("c", c);
		request.setAttribute("page", page);
		
		
		
		return "admin/listProduct.jsp";
	}
}

