package receiver.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;
import receiver.model.ProductOrder;

@Service
public class ProderOrderService {
	/**
	 * Name our receiver queue message in RabbitMQ Broker
	 */
	private static final String receive_queue = "spring-reactive-queue";
	
	// Connection to RabbitMQ
	@Autowired
	private Mono<Connection> connMono;
	
	final Receiver receiver;

	public ProderOrderService(Receiver receiver) {
		this.receiver = receiver;
	}
	
	// Listen to RabbitMQ as soon as this service is up
	@PostConstruct
	private void init() {
		consume();
	}
	
	// Make sure to close the connection before the program is finished
	@PreDestroy
	public void close() throws IOException {
		connMono.block().close();
	}
	
	// Consume message from the sender queue

	private Disposable consume() {
		
		return receiver.consumeAutoAck(receive_queue)
				.subscribe(m -> {
					
					//1. Deserialize byte to json
					String json = SerializationUtils
							.deserialize(m.getBody());
					ObjectMapper mapper = new ObjectMapper();
					
					ProductOrder productOrder;
					
					// 2. map json to order object
					try {
						
						productOrder = mapper.readValue(json, ProductOrder.class);
						System.out.println(json.toString());
						System.out.println(productOrder.getTotalCost());
						
						/**
						 * Do your business logic here...
						 */
						
					} catch (Exception e) {

						e.printStackTrace();
					}
					
				});
		
	}
	
	
}
