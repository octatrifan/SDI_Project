package service;

import exception.RepoException;
import model.Car;
import repo.Repository;
import repo.Sorting.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Movie Service
 *
 * @author Dani
 */

public class CarService extends AService<Integer, Car> {
    public CarService(Repository<Integer, Car> repo) {
        this.repo = repo;
    }

    public Iterable<Car> filterByModel(String model) {
        return StreamSupport.stream(this.repo.findAll().spliterator(), false)
                .filter(movie -> movie.getModel().equals(model))
                .collect(Collectors.toList());
    }

    public Iterable<Car> sortByYear() {
        return repo.sort(new Sort().by("makeYear"));
    }

    public Iterable<Car> sortByBrandAndYear()
    {
        try
        {
            return repo.sort(new Sort().by("brand").and(new Sort().by("makeYear").descending()));
        } catch (RepoException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
