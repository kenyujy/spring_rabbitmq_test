package spring_rabbitmq_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring_rabbitmq_test.entity.Msg;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void sendMsgTest(){

        Msg msg= new Msg().setMsgHead("msg_head").setMsgBody("msg_body");
        rabbitTemplate.convertAndSend("amq.direct","test", msg); //exchange, eoutingKey, object
    }

}


