package sender.service;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;
import sender.dto.ProductDTO;
import sender.model.ProductOrder;

@Service
public class ProductService {

	final Sender sender;

	public ProductService(Sender sender) {
		this.sender = sender;
	}
	
	/**
	 * Name our queue message in RabbitMQ Broker
	 */
	private static final String queue = "spring-reactive-queue";
	
	/**
	 * slf4j Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	/**
	 * Let's create sender method, in order to send message queue.
	 * 
	 * @param productDto
	 * @return
	 */
	public Mono<ProductOrder> createSender(Mono<ProductDTO> productDto){
		
		return productDto.flatMap(pdto -> {
			
			ProductOrder productOrder = mapperProductDTOtoEntity(pdto);
			ObjectMapper mapper = new ObjectMapper();
			String json;
			try {
				
				// Serialize object to json
				json = mapper.writeValueAsString(productOrder);
				
				// Serialize json to bytes
				byte[] orderSerialized = SerializationUtils.serialize(json);
				
				// Outbound Message that will be sent by the Sender
				Flux<OutboundMessage> outbound = Flux.just(new OutboundMessage("", queue, orderSerialized));
				
				// Declare the queue then send the flux of messages
				sender.declareQueue(QueueSpecification.queue(queue))
					  .thenMany(sender.sendWithPublishConfirms(outbound))
					  .doOnError(e -> logger.error("Send failed", e))
					  .subscribe(m -> {
						  logger.info("Message has been sent...");
					  });
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			// Return posted object to user (Client)
			return Mono.just(productOrder);
			
		});
		
	}

	/**
	 * Mapper Function for ProductDTO to Entity.
	 * It will call calculate total cost of product.
	 * 
	 * @param dto
	 * @return
	 */
	private ProductOrder mapperProductDTOtoEntity(ProductDTO dto) {
		
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProducts(dto.getProducts());
		productOrder.setTotalCost(productOrder.doCalculateCost());
		return productOrder;
	}
}
