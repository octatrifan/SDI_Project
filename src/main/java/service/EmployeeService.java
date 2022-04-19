package service;

import model.Client;
import model.Employee;
import repo.Repository;
import repo.dbRepo.PagingRepository;

import java.util.stream.Collectors;

public class EmployeeService extends AService<Integer, Employee> {
    public EmployeeService(Repository<Integer, Employee> repo) {
        this.repo = repo;
    }

    public Iterable<Employee> getNextEmployees() {
        PagingRepository<Integer, Employee> repo = (PagingRepository<Integer, Employee>) this.repo;
        return repo.getNext().getContent().collect(Collectors.toList());
    }

    public Iterable<Employee> getPrevEmployees() {
        PagingRepository <Integer, Employee> repo = (PagingRepository<Integer, Employee>) this.repo;
        return repo.getPrev().getContent().collect(Collectors.toList());
    }
}
