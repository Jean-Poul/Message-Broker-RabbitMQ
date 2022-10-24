# RabbitMQ 
## Playground  
  
Note: Use Logback for logging in production  

- To install RabbitMQ server from a Docker image, type  
`docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management`  

When installed successfully, it runs on localhost at port 15672.  

- To get access to the administration dashboard, open http://localhost:15672/ and log in with
the credentials ‘guest’ and ‘guest’.  

### Covered  
- Exchanges, Queues, and Bindings in AMQP  
- Direct Exchange in AMQP – RabbitMQ  
- Fanout Exchange in AMQP – RabbitMQ  
- Topic Exchange in AMQP – RabbitMQ  
- Headers Exchange in AMQP – RabbitMQ  
- Default Exchange in AMQP – RabbitMQ  
- Exchange to Exchange binding in RabbitMQ  
- Alternate Exchange in RabbitMQ  
- RabbitMQ with Spring AMQP  
  
---  
  
### Tutorials followed in this example  
- RabbitMQ [RabbitMQ](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)  
- Installation and other guides [Other guides](https://www.rabbitmq.com/download.html)  
- Good RabbitMQ guide [RabbitMQ](https://jstobigdata.com/rabbitmq/complete-rabbitmq-tutorial-in-java/)  
