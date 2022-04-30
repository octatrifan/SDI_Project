package service;

import model.Client;
import model.Rental;
import repo.Repository;

/**
 * Rental Service
 *
 * @author Carla
 */

public class RentalService extends AService<Integer, Rental> {
    public RentalService(Repository<Integer, Rental> repo) {
        this.repo = repo;
    }
}
