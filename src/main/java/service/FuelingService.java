package service;

import model.Client;
import model.Fueling;
import repo.Repository;
/**
 * Employee Service
 *
 * @author Tudor
 */

public class FuelingService extends AService<Integer, Fueling> {
    public FuelingService(Repository<Integer, Fueling> repo) {
        this.repo = repo;
    }
}