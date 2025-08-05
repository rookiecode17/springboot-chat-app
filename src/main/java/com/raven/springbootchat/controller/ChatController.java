package com.raven.springbootchat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.springbootchat.entity.ChatMessage;

import com.raven.springbootchat.entity.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendPublic(@Payload ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/chat", message);
    }

    @MessageMapping("/chat.private.{recipient}")
    public void sendPrivate(@DestinationVariable String recipient,
                            @Payload ChatMessage message) {
        messagingTemplate.convertAndSendToUser(recipient, "/queue/messages", message);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chat")
    public ChatMessage addUser(@Payload ChatMessage message) {
        message.setType(MessageType.valueOf("JOIN"));
        return message;
    }
}
