package service;

import model.Car;
import model.Client;
import repo.Repository;
import repo.dbRepo.PagingRepository;

import java.util.stream.Collectors;

/**
 * Client Service
 *
 * @author Carla
 */

public class ClientService extends AService<Integer, Client> {
    public ClientService(Repository<Integer, Client> repo) {
        this.repo = repo;
    }

    public Iterable<Client> getNextClients() {
        PagingRepository<Integer, Client> repo = (PagingRepository<Integer, Client>) this.repo;
        return repo.getNext().getContent().collect(Collectors.toList());
    }

    public Iterable<Client> getPrevClients() {
        PagingRepository <Integer, Client> repo = (PagingRepository<Integer, Client>) this.repo;
        return repo.getPrev().getContent().collect(Collectors.toList());
    }
}
