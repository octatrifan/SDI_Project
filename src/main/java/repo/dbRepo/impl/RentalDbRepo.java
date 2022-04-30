package repo.dbRepo.impl;
import exception.ValidatorException;
import model.Car;
import model.Rental;
import repo.dbRepo.Page;
import repo.dbRepo.Pageable;
import repo.dbRepo.PagingRepository;
import validator.IValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RentalDbRepo implements PagingRepository<Integer, Rental> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private final IValidator<Rental> validator;
    private Page <Rental> page;

    private static final String selectRentalSQL = "select * from Rental where ID=?";
    private static final String selectAllSQL = "select * from Rental";
    private static final String insertRentalSQL = "insert into Rental(ID, rfID, carID, clientID, rentDate, deadlineDate, isRented) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String deleteRentalSQL = "delete from Rental where ID=?";
    private static final String updateRentalSQL = "UPDATE Rental\n" + "\tSET rfID=?, carID=?, clientID=?, rentDate=?, deadlineDate=?, isRented=? where ID=?";

    public RentalDbRepo(IValidator<Rental> validator) {
        this.validator = validator;
        this.page = new CommonPage<>(new CommonPageable(-1, 4));
    }

    /**
     * Find the rental with the given {@code id}.
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<Rental> findOne(Integer id) {
        Rental c = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectRentalSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                int rfID = resultSet.getInt("rfID");
                int carID = resultSet.getInt("carID");
                int clientID = resultSet.getInt("clientID");
                Date rentDate = resultSet.getDate("rentDate");
                Date deadlineDate = resultSet.getDate("deadlineDate");
                boolean isRented = resultSet.getInt("avCars") == 1;
                c = new Rental(carID, clientID, rfID, rentDate, deadlineDate, isRented);
                c.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(c);
    }

    /**
     *
     * @return all rentals from the database
     */
    public List<Rental> findAll() {
        List<Rental> rentals = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int rfID = resultSet.getInt("rfID");
                int carID = resultSet.getInt("carID");
                int clientID = resultSet.getInt("clientID");
                Date rentDate = resultSet.getDate("rentDate");
                Date deadlineDate = resultSet.getDate("deadlineDate");
                boolean isRented = resultSet.getInt("avCars") == 1;
                Rental c = new Rental(carID, clientID, rfID, rentDate, deadlineDate, isRented);
                c.setId(id);
                rentals.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    /**
     * Saves the given rental in table.
     *
     * @param c - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Rental> save(Rental c) {
        this.validator.validate(c);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertRentalSQL)) {
            statement.setInt(1, c.getId());
            statement.setInt(2, c.getRentalFirmID());
            statement.setInt(3, c.getCarID());
            statement.setInt(4,c.getClientID());
            statement.setDate(5, new java.sql.Date(c.getRentDate().getTime()));
            statement.setDate(6, new java.sql.Date(c.getDeadlineDate().getTime()));
            statement.setInt(7, c.isRented() ? 1 : 0);
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(c);
        }
    }

    /**
     * Delete from table the Rental with the given id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    public Optional<Rental> delete(Integer id) {
        Optional<Rental> c = this.findOne(id);
        try {
            c.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteRentalSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Updates the given rental.
     *
     * @param c must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Rental> update(Rental c) {
        this.validator.validate(c);
        Optional<Rental> rental = this.findOne(c.getId());
        try {
            rental.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateRentalSQL)) {
            statement.setInt(1, c.getRentalFirmID());
            statement.setInt(2, c.getCarID());
            statement.setInt(3,c.getClientID());
            statement.setDate(4, new java.sql.Date(c.getRentDate().getTime()));
            statement.setDate(5, new java.sql.Date(c.getDeadlineDate().getTime()));
            statement.setInt(6, c.isRented() ? 1 : 0);
            statement.setInt(7, c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rental;
    }

    @Override
    public Page<Rental> findAll(Pageable pageable) {
        List <Rental> all = this.findAll();
        int begin = pageable.getPageNumber() * pageable.getPageSize();
        int end = begin + pageable.getPageSize();
        end = Math.min(end, all.size());
        return new CommonPage<>(pageable, all.subList(begin, end));
    }

    @Override
    public Page<Rental> getNext() {
        page = new CommonPage<>(page.nextPageable());
        return findAll(page.getPageable());
    }

    @Override
    public Page<Rental> getPrev() {
        page = new CommonPage<>(page.prevPageable());
        return findAll(page.getPageable());
    }
}
