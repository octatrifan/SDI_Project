package service;

import model.Client;
import repo.Repository;

/**
 * Client Service
 * @author Carla
 */

public class ClientService extends AService<Integer, Client> {
    public ClientService(Repository<Integer, Client> repo) {
        this.repo = repo;
    }
}
