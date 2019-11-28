package spring_rabbitmq_test.message_service;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import spring_rabbitmq_test.entity.Msg;

@Service
public class MessageService {

    @RabbitListener(queues ="test")  //listener 会一直监听消息队列
    public void getTestMessage(Object message, Msg msg){  //收消息, 自动转换为Msg 对象类型
        System.out.println( message);
    }
}

