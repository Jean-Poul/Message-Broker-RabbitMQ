package jplm.dk.mom.rabbitmq.rabbitmqdemo.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchange {
    public static void declareExchange() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Declare my-direct-exchange DIRECT exchange
        channel.exchangeDeclare("my-direct-exchange", BuiltinExchangeType.DIRECT, true);
        channel.close();
    }

    public static void declareQueues() throws IOException, TimeoutException {
        //Create a channel - do not share the Channel instance
        Channel channel = ConnectionManager.getConnection().createChannel();
        //queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
        channel.queueDeclare("MobileQ", true, false, false, null);
        channel.queueDeclare("ACQ", true, false, false, null);
        channel.queueDeclare("LightQ", true, false, false, null);
        channel.close();
    }

    public static void declareBindings() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        //Create bindings - (queue, exchange, routingKey)
        channel.queueBind("MobileQ", "my-direct-exchange", "personalDevice");
        channel.queueBind("ACQ", "my-direct-exchange", "homeAppliance");
        channel.queueBind("LightQ", "my-direct-exchange", "homeAppliance");
        channel.close();
    }

    public static void subscribeMessage() throws IOException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        channel.basicConsume("LightQ", true, ((consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("LightQ:" + new String(message.getBody()));
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("ACQ", true, ((consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("ACQ:" + new String(message.getBody()));
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
        channel.basicConsume("MobileQ", true, ((consumerTag, message) -> {
            System.out.println(consumerTag);
            System.out.println("MobileQ:" + new String(message.getBody()));
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
    }

    //Publish the message
    public static void publishMessage() throws IOException, TimeoutException {
        Channel channel = ConnectionManager.getConnection().createChannel();
        String message = "Direct message - Turn on the Home Appliances ";
        channel.basicPublish("my-direct-exchange", "homeAppliance", null, message.getBytes());
        channel.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        DirectExchange.declareQueues();
        DirectExchange.declareExchange();
        DirectExchange.declareBindings();
        Thread subscribe = new Thread() {
            @Override
            public void run() {
                try {
                    DirectExchange.subscribeMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread publish = new Thread() {
            @Override
            public void run() {
                try {
                    DirectExchange.publishMessage();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        };
        subscribe.start();
        publish.start();
    }
}
