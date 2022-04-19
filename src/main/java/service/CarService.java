package service;

import model.Car;
import repo.Repository;
import repo.dbRepo.PagingRepository;

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
        List<Car> cars = StreamSupport.stream(this.repo.findAll().spliterator(), false).collect(Collectors.toList());
        cars.sort(Comparator.comparing(Car::getMakeYear));
        return cars;
    }

    public Iterable<Car> getNextCars() {
        PagingRepository <Integer, Car> repo = (PagingRepository<Integer, Car>) this.repo;
        return repo.getNext().getContent().collect(Collectors.toList());
    }

    public Iterable<Car> getPrevCars() {
        PagingRepository <Integer, Car> repo = (PagingRepository<Integer, Car>) this.repo;
        return repo.getPrev().getContent().collect(Collectors.toList());
    }
}
