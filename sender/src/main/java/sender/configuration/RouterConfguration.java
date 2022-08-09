package sender.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import sender.handler.ProductHandler;

@Configuration
public class RouterConfguration {

	/**
	 * Here I do not user controller annotation-based to create end-point. Instead I will use Router function
	 * Router function: To use Spring Webflux to its full potential I believe it is better to move away from
	 * the controller annotation-based and move towards using functions to route and handle requests
	 */
	private static final String ENDPOINT_PATH = "/";
	
	/**
	 * Router function for the end-point of the Sender Service.
	 * 
	 * @param handler
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> routerFunction(ProductHandler handler){
		
		return RouterFunctions
				.route(RequestPredicates.POST(ENDPOINT_PATH), handler::sendOrder);
	}
}
