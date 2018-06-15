package minsu.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import minsu.bean.Category;
import minsu.bean.Product;
import minsu.bean.ProductImage;
import minsu.dao.CategoryDAO;
import minsu.dao.ProductDAO;
import minsu.dao.ProductImageDAO;

public class Test {

	public static void main(String[] args) throws IOException {
		List<Category> cs = new CategoryDAO().list();
		for (Category c : cs) {
			List<Product> ps= new ProductDAO().list(c.getId(),0,Short.MAX_VALUE);
			for (Product p : ps) {
				List<ProductImage> pis1=new ProductImageDAO().list(p, ProductImageDAO.type_single);
				for (ProductImage pi : pis1) {

				}
				List<ProductImage> pis2=new ProductImageDAO().list(p, ProductImageDAO.type_detail);
				for (ProductImage pi : pis2) {
					String fileNameFormat = "/home/chanchan/Desktop/airbnb/web/img/productDetail/%d.jpg";
					String srcFileName = String.format(fileNameFormat, pi.getId());
					String destFileName = StringUtils.replace(srcFileName, "minsu_original", "minsu");
					FileUtils.copyFile(new File(srcFileName), new File(destFileName));

				}				

			}
		}
		

	}
}

