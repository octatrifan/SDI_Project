package service;

import model.Car;
import repo.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Movie Service
 * 
 * @author Dani
 */

public class CarService extends AService <Integer, Car> {
    public CarService(Repository<Integer, Car> repo) {
        this.repo = repo;
    }

    public Iterable<Car> filterByModel(String model) {
        return StreamSupport.stream(this.repo.findAll().spliterator(), false)
                .filter(movie -> movie.getModel().equals(model))
                .collect(Collectors.toList());
    }

    public Iterable<Car> sortByYear() {
        return StreamSupport.stream(this.repo.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Car::getMakeYear)).collect(Collectors.toList());
    }
}
