package service;

import model.Employee;
import repo.Repository;

/**
 * Employee Service
 *
 * @author Carla
 */

public class EmployeeService extends AService<Integer, Employee> {
    public EmployeeService(Repository<Integer, Employee> repo) {
        this.repo = repo;
    }
}
