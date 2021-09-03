package ru.job4j.managingpassports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.managingpassports.model.Passport;
import ru.job4j.managingpassports.repository.PassportRepository;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class PassportServiceImpl implements PassportService {
    private final PassportRepository repository;
    @Autowired
    private KafkaTemplate<Integer, Passport> kafkaTemplate;

    public PassportServiceImpl(PassportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Passport save(Passport passport) {
        repository.save(passport);
        return passport;
    }

    @Override
    public boolean update(int id, Passport passport) {
        boolean result = false;
        if (repository.findById(id).isPresent()) {
            Passport changedPassport = repository.findById(id).get();
            changedPassport.setSeries(passport.getSeries());
            changedPassport.setNumber(passport.getNumber());
            changedPassport.setEndDate(passport.getEndDate());
            repository.save(changedPassport);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
            result = true;
        }
        return result;
    }

    @Override
    public List<Passport> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Passport> findBySeries(String series) {
        return repository.findPassportBySeries(series);
    }

    @Override
    public List<Passport> unavailable() {
        return repository.findPassportByEndDate();
    }

    private Date getDateFor3Months() {
        long before3Months = new Date().getTime() + 90L * 24 * 60 * 60 * 1000;
        return new Date(before3Months);
    }

    @Override
    public List<Passport> findReplaceable() {
        Date dateFor = getDateFor3Months();
        return repository.findPassportsByEndDateFor3Months(dateFor);
    }

    @Override
    @Scheduled(cron = "*/10 * * * * *")
    public List<Passport> checkPassportByDate() {
        List<Passport> passports = unavailable();
        if (!passports.isEmpty()) {
            System.out.println(new Date());
            System.out.println("There are some passports for replacement: ");
            for (Passport p : passports) {
                kafkaTemplate.send("passport", p);
            }
        } else {
            System.out.println("There are no passports for replacement");
        }
        return passports;
    }
}
