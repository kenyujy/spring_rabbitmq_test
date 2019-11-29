package spring_rabbitmq_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring_rabbitmq_test.entity.Msg;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void sendMsgTest(){
        Msg msg= new Msg().setMsgHead("msg_head").setMsgBody("the_msg_body");
        for(int i=0; i<5; i++)
            rabbitTemplate.convertAndSend("qx.direct","qx", msg); //exchange, routingKey, object
    }

    @Test
    public void amqpAdminTest(){
        // 声明队列, default exchange bind to every queue by queue name as key
        Queue qx= new Queue("qx",true,false, false);
        amqpAdmin.declareQueue(qx);
    }

    @Test
    public void createExchange(){
        // 声明交换机
        Exchange exDirect= new DirectExchange("qx.direct",true,false);
        amqpAdmin.declareExchange(exDirect);
    }

    @Test
    public void createBinding(){
        // 绑定交换机和队列
        /*Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey,
         *      Map<String, Object> arguments)
         */
        Binding binding= new Binding("qx", Binding.DestinationType.QUEUE,"qx.direct","qx",null);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    public void receiveMsg(){
        //SignUpDto out = (SignUpDto) template.receiveAndConvert("myQueue");
    }

    @Test
    public void sendWrongMsgTest(){

        rabbitTemplate.convertAndSend("amq.direct","test", "hello"); //exchange, eoutingKey, object
    }

    @Test
    public void cal(){
        System.out.println(5/3);
    }

}


