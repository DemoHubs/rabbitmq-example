package com.peachyy.rabbitmq.three;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.QueueingConsumer;  
  
public class ReceiveLogsToConsole {  
    private final static String EXCHANGE_NAME = "ex_log1";
    public static void main(String[] args) throws Exception {  
        // 创建连接和频道    
        ConnectionFactory factory = new ConnectionFactory();    
        factory.setHost("192.168.73.129");
        // 指定用户 密码  
        factory.setUsername("admin");  
        factory.setPassword("admin");  
        // 指定端口  
        factory.setPort(AMQP.PROTOCOL.PORT);  
        Connection connection = factory.newConnection();    
        Channel channel = connection.createChannel();    
    
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);
        // 创建一个非持久的、唯一的且自动删除的队列且队列名称由服务器随机产生一般情况这个名称与amq.gen-JzTY20BRgKO-HjmUJj0wLg 类似。  
        String queueName="dqueue2";
        channel.queueDeclare(queueName,true,false,false,null);
        // 为转发器指定队列，设置binding    
        channel.queueBind(queueName, EXCHANGE_NAME, "");    
    
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");    
    
        QueueingConsumer consumer = new QueueingConsumer(channel);    
        // 指定接收者，第二个参数为自动应答，无需手动应答    
        channel.basicConsume(queueName, true, consumer);    
    
        while (true)    
        {    
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();    
            String message = new String(delivery.getBody());    
            System.out.println(" [x] Received '" + message + "'");    
    
        }    
    }  
  
}  