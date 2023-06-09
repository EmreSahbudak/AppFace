package com.emre.rabbitmq.producer;


import com.emre.rabbitmq.model.CreateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {

    private final RabbitTemplate rabbitTemplate;

    public void converAndSendData(CreateUserModel model){
        rabbitTemplate.convertAndSend("auht-exchange2",
                "auth-routing-key-create-user2", model);
    }
}
