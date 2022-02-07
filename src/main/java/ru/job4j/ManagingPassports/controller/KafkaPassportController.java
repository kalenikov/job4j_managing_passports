package ru.job4j.ManagingPassports.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.ManagingPassports.model.Passport;
import ru.job4j.ManagingPassports.service.PassportService;

import java.util.List;

@RestController
public class KafkaPassportController {

    private final PassportService service;

    public KafkaPassportController(@Qualifier("passportServiceImpl") PassportService service) {
        this.service = service;
    }

    @GetMapping("/check")
    public List<Passport> checkPassports() {
        return service.checkPassportByDate();
    }
}
