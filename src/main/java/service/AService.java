package service;

import exception.ValidatorException;
import model.BaseEntity;
import repo.Repository;

import java.util.Optional;

/**
 * This is the base Service class
 * Other services can extend from this class
 * @author Octa
 */

public abstract class AService<ID, T extends BaseEntity<ID>> {
    protected Repository<ID, T> repo;

    public Optional<T> save(T object) throws ValidatorException {
        return repo.save(object);
    }

    public Optional<T> delete(ID id) {
        return repo.delete(id);
    }

    public Optional<T> update(T object) throws ValidatorException {
        return repo.update(object);
    }

    public Iterable<T> findAll() {
        return repo.findAll();
    }
}
