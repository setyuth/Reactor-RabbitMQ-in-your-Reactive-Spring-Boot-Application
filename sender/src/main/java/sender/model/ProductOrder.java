package sender.model;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

	private String id;
	private Product[] products;
	private double totalCost;
	
	
	public ProductOrder(Product[] products) {
		this.products = products;
		this.totalCost = this.doCalculateCost();
	}


	public double doCalculateCost() {
		
		// Result
		double result = 0;
		
		// Sum all product
		List<Product> productsList = Arrays.asList(products);
		for(Product pro : productsList) {
			result += pro.getPrice();
		}
		
		return result;
	}
	
	
}
