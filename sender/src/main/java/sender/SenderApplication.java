package sender;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.Connection;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class SenderApplication {

	@Autowired
	private Mono<Connection> conMono;
	
	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}
	
	// Close the connection before the program is finished
	@PreDestroy
	public void close() throws IOException {
		conMono.block().close();
	}

}
