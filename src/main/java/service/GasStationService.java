package service;

import model.GasStation;
import model.Rental;
import repo.Repository;
/**
 * Employee Service
 *
 * @author Ani
 */

public class GasStationService extends AService<Integer, GasStation> {
    public GasStationService(Repository<Integer, GasStation> repo) {
        this.repo = repo;
    }
}
