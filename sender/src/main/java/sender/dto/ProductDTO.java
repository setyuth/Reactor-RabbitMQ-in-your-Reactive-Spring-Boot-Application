package sender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sender.model.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private String id;
	private Product[] products;
	
}
