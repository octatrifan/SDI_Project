package service;

import model.Client;
import model.Fueling;
import repo.Repository;


public class FuelingService extends AService<Integer, Fueling> {
    public FuelingService(Repository<Integer, Fueling> repo) {
        this.repo = repo;
    }
}