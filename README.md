# Reactor-RabbitMQ-in-your-Reactive-Spring-Boot-Application
Project about the reactive-stack web framework, Spring WebFlux and implementing RabbitMQ as the message broker for the application.

Let's suppose that we have designed the architecture with 5 components need to be implement.

1. Client

2. API Gateway

3. Sender Service

4. Message Broker (RabbitMQ)

5. Receiver Service

Start Initialize Spring Project Application
I will build spring boot project from scratch. And I will categorize with 3 module : 
First module is parent pom module as wrapper project, second is the sender module for sender service to 
send message to rabbitmq broker and the last one is the receiver module for receiver queue message from rabbitmq broker. 

- YT: https://www.youtube.com/playlist?list=PLYZvWFacASF_7vWvA5yKhNL8BxvmgN7Fk

- Blogger: https://khmerside.blogspot.com/2022/08/reactor-rabbitmq-in-your-reactive.html

- Medium: https://medium.com/@khmerside/reactor-rabbitmq-in-your-reactive-spring-boot-application-part-1-3da6495d73ec

- FB: https://www.facebook.com/watch/100063605890994/787309449118184/
