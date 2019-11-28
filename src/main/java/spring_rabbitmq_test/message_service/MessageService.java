package spring_rabbitmq_test.message_service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import spring_rabbitmq_test.entity.Msg;

import java.io.IOException;

@Service
public class MessageService {

    @RabbitListener(queues ="test")  //listener 会一直监听消息队列
    public void getTestMessage(Message message, Msg msg, Channel channel) throws IOException {  //收消息, 自动转换为Msg 对象类型

        System.out.println(message);
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); //requeue =true
    }
}

