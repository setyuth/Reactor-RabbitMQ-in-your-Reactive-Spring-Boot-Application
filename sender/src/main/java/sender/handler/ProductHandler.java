package sender.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import sender.dto.ProductDTO;
import sender.model.ProductOrder;
import sender.service.ProductService;

@Service
public class ProductHandler {

	private final ProductService productService;

	public ProductHandler(ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * To receive POST request from the router to save an Order into db...
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> sendOrder(ServerRequest request){
		
		// Extract JSON object from Server Request.
		Mono<ProductDTO> dto = request.bodyToMono(ProductDTO.class);
		
		// Send request to service.
		Mono<ProductOrder> rs = productService.createSender(dto);
		
		// Return server response and the product order object in the body.
		return ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(rs, ProductOrder.class);
		
		
	}
	
	
	
}
