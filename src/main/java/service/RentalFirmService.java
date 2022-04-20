package service;

import model.RentalFirm;
import repo.Repository;

/**
 * RentalFirm Service
 *
 * @author Liviu
 */

public class RentalFirmService extends AService<Integer, RentalFirm> {
    public RentalFirmService(Repository<Integer, RentalFirm> repo) {
        this.repo = repo;
    }
}
