package service;

import model.Client;
import model.Employee;
import repo.Repository;

public class EmployeeService extends AService<Integer, Employee> {
    public EmployeeService(Repository<Integer, Employee> repo) {
        this.repo = repo;
    }
}
