package ru.job4j.managingpassports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.managingpassports.model.Passport;
import ru.job4j.managingpassports.service.PassportService;

import java.util.List;

@RestController
public class KafkaPassportController {

    @Qualifier("passportServiceImpl")
    @Autowired
    private PassportService service;

    @GetMapping("/check")
    public List<Passport> checkPassports() {
        return service.checkPassportByDate();
    }
}
