package repo.dbRepo.impl;
import exception.ValidatorException;
import model.Car;
import model.RentalFirm;
import repo.dbRepo.Page;
import repo.dbRepo.Pageable;
import repo.dbRepo.PagingRepository;
import validator.IValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RentalFirmDbRepo implements PagingRepository<Integer, RentalFirm> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private final IValidator<RentalFirm> validator;
    private Page <RentalFirm> page;

    private static final String selectRentalFirmSQL = "select * from RentalFirm where ID=?";
    private static final String selectAllSQL = "select * from RentalFirm";
    private static final String insertRentalFirmSQL = "insert into RentalFirm(ID, name, address, avCars) values (?, ?, ?, ?)";
    private static final String deleteRentalFirmSQL = "delete from RentalFirm where ID=?";
    private static final String updateRentalFirmSQL = "UPDATE RentalFirm\n" + "\tSET name=?, address=?, avCars=? where ID=?";

    public RentalFirmDbRepo(IValidator<RentalFirm> validator) {
        this.validator = validator;
        this.page = new CommonPage<>(new CommonPageable(-1, 4));
    }

    /**
     * Find the rentalFirm with the given {@code id}.
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<RentalFirm> findOne(Integer id) {
        RentalFirm c = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectRentalFirmSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                int avCars = resultSet.getInt("avCars");
                c = new RentalFirm(name, address, avCars);
                c.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(c);
    }

    /**
     *
     * @return all rentalFirms from the database
     */
    public List<RentalFirm> findAll() {
        List<RentalFirm> rentalFirms = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                int avCars = resultSet.getInt("avCars");
                RentalFirm c = new RentalFirm(name, address, avCars);
                c.setId(id);
                rentalFirms.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalFirms;
    }

    /**
     * Saves the given rentalFirm in table.
     *
     * @param c - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<RentalFirm> save(RentalFirm c) {
        this.validator.validate(c);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertRentalFirmSQL)) {
            statement.setInt(1, c.getId());
            statement.setString(2, c.getRentalFirmName());
            statement.setString(3, c.getAddress());
            statement.setInt(4,c.getAvailableCars());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(c);
        }
    }

    /**
     * Delete from table the RentalFirm with the given id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    public Optional<RentalFirm> delete(Integer id) {
        Optional<RentalFirm> c = this.findOne(id);
        try {
            c.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteRentalFirmSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Updates the given rentalFirm.
     *
     * @param c must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<RentalFirm> update(RentalFirm c) {
        this.validator.validate(c);
        Optional<RentalFirm> rentalFirm = this.findOne(c.getId());
        try {
            rentalFirm.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateRentalFirmSQL)) {
            statement.setString(1, c.getRentalFirmName());
            statement.setString(2, c.getAddress());
            statement.setInt(3,c.getAvailableCars());
            statement.setInt(4, c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalFirm;
    }

    @Override
    public Page<RentalFirm> findAll(Pageable pageable) {
        List <RentalFirm> all = this.findAll();
        int begin = pageable.getPageNumber() * pageable.getPageSize();
        int end = begin + pageable.getPageSize();
        end = Math.min(end, all.size());
        return new CommonPage<>(pageable, all.subList(begin, end));
    }

    @Override
    public Page<RentalFirm> getNext() {
        page = new CommonPage<>(page.nextPageable());
        return findAll(page.getPageable());
    }

    @Override
    public Page<RentalFirm> getPrev() {
        page = new CommonPage<>(page.prevPageable());
        return findAll(page.getPageable());
    }
}
