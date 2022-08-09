package receiver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;

@Configuration
public class RabbitMQReceiverConfiguration {

	@Bean
	Mono<Connection> connectionMono(){
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.useNio();
		return Mono.fromCallable(() -> connectionFactory.newConnection("receiver-queue-sms-rabbitmq"));
	}
	
	@Bean
	public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
		
		return new ReceiverOptions().connectionMono(connectionMono);
		
	}
	
	@Bean
	Receiver receiver(ReceiverOptions receiverOptions) {
		return RabbitFlux.createReceiver(receiverOptions);
	}
}
