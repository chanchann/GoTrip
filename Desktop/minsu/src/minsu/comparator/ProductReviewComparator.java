package minsu.comparator;

import java.util.Comparator;

import minsu.bean.Product;

public class ProductReviewComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount()-p1.getReviewCount();
	}

}
