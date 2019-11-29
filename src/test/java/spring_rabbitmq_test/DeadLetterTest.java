package spring_rabbitmq_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring_rabbitmq_test.entity.Msg;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeadLetterTest {

    @Autowired AmqpAdmin amqpAdmin;
    @Autowired RabbitTemplate rabbitTemplate;

    @Test
    public void deadLetterTest1(){

        Map<String, Object> argsMap= new HashMap<>();
        argsMap.put("x-message-ttl",15000);  //消息存活时间
        argsMap.put("x-dead-letter-exchange","delayQueue.dead.direct"); //死信交换机
        argsMap.put("x-dead-letter-routing-key","delay.message");  //死信路由键

        // 声明队列
        Queue delayQueue= new Queue("delayQueue",true,false, false, argsMap);
        amqpAdmin.declareQueue(delayQueue);

        // 声明交换机
        Exchange orderQueueDirect= new DirectExchange("orderQueue.direct",true,false);
        amqpAdmin.declareExchange(orderQueueDirect);

        // 绑定交换机和队列
        /*Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey,
         *      Map<String, Object> arguments)
         */
        Binding binding= new Binding("delayQueue", Binding.DestinationType.QUEUE,"orderQueue.direct","order",null);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    public void deadLetterTest2(){
        // 声明死信交换机
        Exchange delayQueueDeadDirect= new DirectExchange("delayQueue.dead.direct",true,false);
        amqpAdmin.declareExchange(delayQueueDeadDirect);

        // 声明死信队列
        Queue deadQueue= new Queue("deadQueue",true,false, false);
        amqpAdmin.declareQueue(deadQueue);

        // 绑定交换机和队列
        /*Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey,
         *      Map<String, Object> arguments)
         */
        Binding binding= new Binding("deadQueue", Binding.DestinationType.QUEUE,"delayQueue.dead.direct","delay.message",null);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    public void sendMsgTest(){
        Msg msg= new Msg().setMsgHead("msg_head").setMsgBody("the_msg_body");
        for(int i=0; i<5; i++)
            rabbitTemplate.convertAndSend("orderQueue.direct","order", msg); //exchange, routingKey, object
    }
}
