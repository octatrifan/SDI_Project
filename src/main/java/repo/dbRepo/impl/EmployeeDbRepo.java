package repo.dbRepo.impl;
import exception.ValidatorException;
import model.Car;
import model.Employee;
import repo.dbRepo.Page;
import repo.dbRepo.Pageable;
import repo.dbRepo.PagingRepository;
import validator.IValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EmployeeDbRepo implements PagingRepository<Integer, Employee> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private final IValidator<Employee> validator;
    private Page <Employee> page;

    private static final String selectEmployeeSQL = "select * from Employee where ID=?";
    private static final String selectAllSQL = "select * from Employee";
    private static final String insertEmployeeSQL = "insert into Employee(ID, firstName, lastName, birthDate, email, salary) values (?, ?, ?, ?, ?, ?)";
    private static final String deleteEmployeeSQL = "delete from Employee where ID=?";
    private static final String updateEmployeeSQL = "UPDATE Employee\n" + "\tSET firstName=?, lastName=?, birthDate=?, email=?, salary=? where ID=?";

    public EmployeeDbRepo(IValidator<Employee> validator) {
        this.validator = validator;
        this.page = new CommonPage<>(new CommonPageable(-1, 4));
    }

    /**
     * Find the employee with the given {@code id}.
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<Employee> findOne(Integer id) {
        Employee c = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectEmployeeSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Date birthDate = resultSet.getDate("birthDate");
                String email = resultSet.getString("email");
                int salary = resultSet.getInt("salary");
                c = new Employee(firstName, lastName, birthDate, email, salary);
                c.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(c);
    }

    /**
     *
     * @return all employees from the database
     */
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Date birthDate = resultSet.getDate("birthDate");
                String email = resultSet.getString("email");
                int salary = resultSet.getInt("salary");
                Employee c = new Employee(firstName, lastName, birthDate, email, salary);
                c.setId(id);
                employees.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Saves the given employee in table.
     *
     * @param c - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Employee> save(Employee c) {
        this.validator.validate(c);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertEmployeeSQL)) {
            statement.setInt(1, c.getId());
            statement.setString(2, c.getFirstName());
            statement.setString(3, c.getLastName());
            statement.setDate(4, new java.sql.Date(c.getBirthDate().getTime()));
            statement.setString(5, c.getEmail());
            statement.setInt(6, (Integer) c.getSalary());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(c);
        }
    }

    /**
     * Delete from table the Employee with the given id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    public Optional<Employee> delete(Integer id) {
        Optional<Employee> c = this.findOne(id);
        try {
            c.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteEmployeeSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Updates the given employee.
     *
     * @param c must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Employee> update(Employee c) {
        this.validator.validate(c);
        Optional<Employee> employee = this.findOne(c.getId());
        try {
            employee.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateEmployeeSQL)) {
            statement.setString(1, c.getFirstName());
            statement.setString(2, c.getLastName());
            statement.setDate(3, new java.sql.Date(c.getBirthDate().getTime()));
            statement.setString(4, c.getEmail());
            statement.setInt(5, (Integer) c.getSalary());
            statement.setInt(6, c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        List <Employee> all = this.findAll();
        int begin = pageable.getPageNumber() * pageable.getPageSize();
        int end = begin + pageable.getPageSize();
        end = Math.min(end, all.size());
        return new CommonPage<>(pageable, all.subList(begin, end));
    }

    @Override
    public Page<Employee> getNext() {
        page = new CommonPage<>(page.nextPageable());
        return findAll(page.getPageable());
    }

    @Override
    public Page<Employee> getPrev() {
        page = new CommonPage<>(page.prevPageable());
        return findAll(page.getPageable());
    }
}
