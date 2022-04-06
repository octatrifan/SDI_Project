package service;

import main.java.model.Client;
import main.java.model.Rental;
import main.java.repo.Repository;

/**
 * Rental Service
 * @author Carla
 */

public class RentalService extends AService<Integer, Rental> {
    public RentalService(Repository<Integer, Rental> repo) {
        this.repo = repo;
    }
}
