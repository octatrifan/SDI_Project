package repo.inMemoryRepo;

import model.GasStation;
import org.junit.jupiter.api.Test;
import validator.GasStationValidator;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepoTest {

    @Test
    void findOne() {
        var r1 = new InMemoryRepo<Integer, GasStation>(new GasStationValidator());
        var g1 = new GasStation("BP", 7, 8);
        g1.setId(1);
        var g2 = new GasStation("BP", 7, 8);
        g2.setId(2);
        var g3 = new GasStation("Rompetrol", 7, 8);
        g3.setId(3);

        r1.save(g1);
        r1.save(g2);
        r1.save(g3);
        var it = r1.findAll();
        for(var i: it)
            assert (i.getGasolinePrice() == 7 && i.getDieselPrice() == 8);
    }

    @Test
    void findAll() {
        var r1 = new InMemoryRepo<Integer, GasStation>(new GasStationValidator());
        var g1 = new GasStation("BP", 7, 8);
        g1.setId(1);
        var g2 = new GasStation("BP", 7, 8);
        g2.setId(2);
        var g3 = new GasStation("Rompetrol", 7, 8);
        g3.setId(3);

        r1.save(g1);
        r1.save(g2);
        r1.save(g3);
        var it = r1.findAll();
        for(var i: it)
            assert (i.getGasolinePrice() == 7 && i.getDieselPrice() == 8);

    }

    @Test
    void save() {
        var r1 = new InMemoryRepo<Integer, GasStation>(new GasStationValidator());
        var g1 = new GasStation("BP", 7, 8);
        g1.setId(1);
        var g2 = new GasStation("BP", 7, 8);
        g2.setId(2);
        var g3 = new GasStation("Rompetrol", 7, 8);
        g3.setId(3);

        r1.save(g1);
        r1.save(g2);
        r1.save(g3);
        var it = r1.findAll();
        for(var i: it)
            assert (i.getGasolinePrice() == 7 && i.getDieselPrice() == 8);

    }

    @Test
    void delete() {
        var r1 = new InMemoryRepo<Integer, GasStation>(new GasStationValidator());
        var g1 = new GasStation("BP", 7, 8);
        g1.setId(1);
        var g2 = new GasStation("BP", 7, 8);
        g2.setId(2);
        var g3 = new GasStation("Rompetrol", 7, 8);
        g3.setId(3);
        var g4 = new GasStation("Rompetrol", 7, 9);
        g4.setId(4);

        r1.save(g1);
        r1.save(g2);
        r1.save(g3);
        r1.save(g4);
        r1.delete(4);
        var it = r1.findAll();

        for(var i: it)
            assert (i.getGasolinePrice() == 7 && i.getDieselPrice() == 8);

    }
}