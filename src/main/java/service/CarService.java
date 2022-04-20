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

public abstract class CarService extends AService <Integer, Car> {
    public CarService(Repository<Integer, Car> repo) {
        this.repo = repo;
    }

    public Iterable<Car> filterByModel(String model) {
        return StreamSupport.stream(this.repo.findAll().spliterator(), false)
                .filter(movie -> movie.getModel().equals(model))
                .collect(Collectors.toList());
    }

    public abstract Iterable<Car> sortByYear();

    public abstract Iterable<Car> sortByBrandAndYear();
}
