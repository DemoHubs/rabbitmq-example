package com.peachyy.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    //消息队列名称  
    private final static String QUEUE_NAME = "helloword1";
  
    public static void main(String[] args) throws Exception {  
          /**  
         * 创建连接连接到MabbitMQ  
         */    
        ConnectionFactory factory = new ConnectionFactory();
        //设置MabbitMQ所在主机ip或者主机名    
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
        //指定一个队列    
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //发送的消息    
        String message = "hello world!";    
        //往队列中发出一条消息
        for(int i=0;i<100;i++){
            String a=message.concat(i+"");
            channel.basicPublish("", QUEUE_NAME, null, a.getBytes());
            System.out.println("Sent Message：'" + message + "'");
            Thread.sleep(500);
        }

        //关闭频道和连接    
        channel.close();    
        connection.close();    
    }  
  
}  