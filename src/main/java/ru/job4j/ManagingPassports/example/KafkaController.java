package ru.job4j.ManagingPassports.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class KafkaController {
    private final Producer producer;

    public KafkaController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/example")
    public String sendMsg(@PathParam("message") String message) {
        producer.sendMessage(message);
        return "OK";
    }
}
