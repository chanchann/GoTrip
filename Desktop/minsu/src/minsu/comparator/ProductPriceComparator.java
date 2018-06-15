package minsu.comparator;

import java.util.Comparator;

import minsu.bean.Product;

public class ProductPriceComparator implements Comparator<Product>{

	@Override
	public int compare(Product p1, Product p2) {
		return (int) (p1.getPromotePrice()-p2.getPromotePrice());
	}

}

