package service;

import exception.RepoException;
import exception.ValidatorException;
import model.Car;
import repo.Repository;
import repo.Sorting.Sort;
import repo.XMLRepo.CarXMLRepo;

import java.util.Optional;

public class SortCarService extends CarService
{
    protected CarXMLRepo repo;

    public SortCarService(CarXMLRepo repo)
    {
        super(repo);
        this.repo = repo;
    }

    public Optional<Car> save(Car object) throws ValidatorException
    {
        return repo.save(object);
    }

    public Optional<Car> delete(Integer id) {
        return repo.delete(id);
    }

    public Optional<Car> update(Car object) throws ValidatorException {
        return repo.update(object);
    }

    public Iterable<Car> findAll() {
        return repo.findAll();
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
