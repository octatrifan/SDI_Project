package repo.dbRepo.impl;
import exception.ValidatorException;
import model.Car;
import model.Fueling;
import repo.dbRepo.Page;
import repo.dbRepo.Pageable;
import repo.dbRepo.PagingRepository;
import validator.IValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FuelingDbRepo implements PagingRepository<Integer, Fueling> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private final IValidator<Fueling> validator;
    private Page <Fueling> page;

    private static final String selectFuelingSQL = "select * from Fueling where ID=?";
    private static final String selectAllSQL = "select * from Fueling";
    private static final String insertFuelingSQL = "insert into Fueling(ID, carID, gsID, date) values (?, ?, ?, ?)";
    private static final String deleteFuelingSQL = "delete from Fueling where ID=?";
    private static final String updateFuelingSQL = "UPDATE Fueling\n" + "\tSET carID=?, gsID=?, date=? where ID=?";

    public FuelingDbRepo(IValidator<Fueling> validator) {
        this.validator = validator;
        this.page = new CommonPage<>(new CommonPageable(-1, 4));
    }

    /**
     * Find the gasStation with the given {@code id}.
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    @Override
    public Optional<Fueling> findOne(Integer id) {
        Fueling c = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectFuelingSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                Integer CarID = resultSet.getInt("carID");
                Integer GasStationID = resultSet.getInt("gsID");
                Date date = resultSet.getDate("date");
                c = new Fueling(CarID, GasStationID, date);
                c.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(c);
    }

    /**
     *
     * @return all fuelings from the database
     */
    public List<Fueling> findAll() {
        List<Fueling> fuelings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                id = resultSet.getInt("ID");
                Integer CarID = resultSet.getInt("carID");
                Integer GasStationID = resultSet.getInt("gsID");
                Date date = resultSet.getDate("date");
                Fueling c = new Fueling(CarID, GasStationID, date);
                c.setId(id);
                fuelings.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fuelings;
    }

    /**
     * Saves the given fueling in table.
     *
     * @param c - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Fueling> save(Fueling c) {
        this.validator.validate(c);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertFuelingSQL)) {
            statement.setInt(1, c.getId());
            statement.setInt(2, c.getCarID());
            statement.setInt(3, c.getGasStationID());
            statement.setDate(4, new java.sql.Date(c.getDate().getTime()));
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(c);
        }
    }

    /**
     * Delete from table the Fueling with the given id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    public Optional<Fueling> delete(Integer id) {
        Optional<Fueling> c = this.findOne(id);
        try {
            c.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteFuelingSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Updates the given fueling.
     *
     * @param c must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<Fueling> update(Fueling c) {
        this.validator.validate(c);
        Optional<Fueling> fueling = this.findOne(c.getId());
        try {
            fueling.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateFuelingSQL)) {
            statement.setInt(1, c.getCarID());
            statement.setInt(2, c.getGasStationID());
            statement.setDate(3, new java.sql.Date(c.getDate().getTime()));
            statement.setInt(4, c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fueling;
    }

    @Override
    public Page<Fueling> findAll(Pageable pageable) {
        List <Fueling> all = this.findAll();
        int begin = pageable.getPageNumber() * pageable.getPageSize();
        int end = begin + pageable.getPageSize();
        end = Math.min(end, all.size());
        return new CommonPage<>(pageable, all.subList(begin, end));
    }

    @Override
    public Page<Fueling> getNext() {
        page = new CommonPage<>(page.nextPageable());
        return findAll(page.getPageable());
    }

    @Override
    public Page<Fueling> getPrev() {
        page = new CommonPage<>(page.prevPageable());
        return findAll(page.getPageable());
    }
}
