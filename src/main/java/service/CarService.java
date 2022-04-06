package service;

import main.java.model.Car;
import main.java.repo.Repository;

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

    public Iterable<Car> filterByGenre(String genre) {
        return StreamSupport.stream(this.repo.findAll().spliterator(), false)
                .filter(movie -> movie.getModel().equals(genre))
                .collect(Collectors.toList());
    }

    public Iterable<Car> sortByYear() {
        List<Car> cars = StreamSupport.stream(this.repo.findAll().spliterator(), false).toList();
        cars.sort(Comparator.comparing(Car::getMakeYear));
        return cars;
    }
}
