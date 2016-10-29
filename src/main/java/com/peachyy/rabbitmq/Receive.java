package com.peachyy.rabbitmq;

import com.rabbitmq.client.*;

public class Receive {
      
    //消息队列名称  
    private final static String QUEUE_NAME = "helloword1";
  
    public static void main(String[] args) throws Exception {  
         //打开连接和创建频道，与发送端一样    
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.73.129");
        //指定用户 密码  
        factory.setUsername("admin");  
        factory.setPassword("admin");  
        //指定端口  
        factory.setPort(AMQP.PROTOCOL.PORT);
        //创建一个连接    
        Connection connection = factory.newConnection();
        //创建一个频道    
        Channel channel = connection.createChannel();
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。    
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            
        //创建队列消费者    
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费队列    
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true)    
        {    
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）    
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();    
            String message = new String(delivery.getBody());    
            System.out.println("Received Message：'" + message + "'");    
        }    
    }  
  
}  