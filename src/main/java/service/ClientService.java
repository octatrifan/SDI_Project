package service;

import main.java.model.Client;
import main.java.repo.Repository;

/**
 * Client Service
 * @author Carla
 */

public class ClientService extends AService<Integer, Client> {
    public ClientService(Repository<Integer, Client> repo) {
        this.repo = repo;
    }
}
