package service;

import model.Car;
import model.Fueling;
import org.junit.jupiter.api.Test;
import repo.Repository;
import repo.inMemoryRepo.InMemoryRepo;
import validator.FuelingValidator;

import java.util.Calendar;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FuelingServiceTest {
    @Test
    void addService() {
        var s1 = new FuelingService(new InMemoryRepo<Integer, Fueling>(new FuelingValidator()));
        var f1 = new Fueling(1, 1,  new Calendar.Builder()
                .setDate(2012, 2, 21)
                .build().getTime());
        f1.setId(1);
        s1.save(f1);
        var findAll = s1.findAll();
        for (var i: findAll) {
            assert (i.getCarID() == 1 && i.getGasStationID() == 1);
        }
    }

    @Test
    void deleteService() {
        var s1 = new FuelingService(new InMemoryRepo<Integer, Fueling>(new FuelingValidator()));
        var f1 = new Fueling(1, 1,  new Calendar.Builder()
                .setDate(2012, 2, 21)
                .build().getTime());
        f1.setId(1);
        var f2 = new Fueling(2, 2,  new Calendar.Builder()
                .setDate(2012, 2, 21)
                .build().getTime());
        f2.setId(2);
        s1.save(f1);
        s1.save(f2);
        s1.delete(2);
        var findAll = s1.findAll();
        for (var i: findAll) {
            assert (i.getCarID() == 1 && i.getGasStationID() == 1);
        }
    }
}