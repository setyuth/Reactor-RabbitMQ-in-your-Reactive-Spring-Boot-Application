package sender.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
public class RabbitMqSenderConfiguration {

	
	@Bean
	Mono<Connection> connectionMono(){
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.useNio();
		return Mono.fromCallable(() -> connectionFactory.newConnection("sender-sms-rabbitmq"));
	}
	
	@Bean
	public SenderOptions senderOptions(Mono<Connection> connectionMono) {
		return new SenderOptions().connectionMono(connectionMono)
				.resourceManagementScheduler(Schedulers.boundedElastic());
	}
	
	@Bean
	public Sender sender(SenderOptions senderOptions) {
		return RabbitFlux.createSender(senderOptions);
	}
}
