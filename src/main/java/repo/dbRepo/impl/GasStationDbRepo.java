package repo.dbRepo.impl;
import exception.ValidatorException;
import model.Car;
import model.GasStation;
import repo.dbRepo.Page;
import repo.dbRepo.Pageable;
import repo.dbRepo.PagingRepository;
import validator.IValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GasStationDbRepo implements PagingRepository<Integer, GasStation> {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private final IValidator<GasStation> validator;
    private Page <GasStation> page;

    private static final String selectGasStationSQL = "select * from GasStation where ID=?";
    private static final String selectAllSQL = "select * from GasStation";
    private static final String insertGasStationSQL = "insert into GasStation(ID, name, gasoline, diesel) values (?, ?, ?, ?)";
    private static final String deleteGasStationSQL = "delete from GasStation where ID=?";
    private static final String updateGasStationSQL = "UPDATE GasStation\n" + "\tSET name=?, gasoline=?, diesel=? where ID=?";

    public GasStationDbRepo(IValidator<GasStation> validator) {
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
    public Optional<GasStation> findOne(Integer id) {
        GasStation c = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectGasStationSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                Integer gasoline = resultSet.getInt("gasoline");
                Integer diesel = resultSet.getInt("diesel");
                c = new GasStation(name, gasoline, diesel);
                c.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(c);
    }

    /**
     *
     * @return all gasStations from the database
     */
    public List<GasStation> findAll() {
        List<GasStation> gasStations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                Integer gasoline = resultSet.getInt("gasoline");
                Integer diesel = resultSet.getInt("diesel");
                GasStation c = new GasStation(name, gasoline, diesel);
                c.setId(id);
                gasStations.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gasStations;
    }

    /**
     * Saves the given gasStation in table.
     *
     * @param c - must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<GasStation> save(GasStation c) {
        this.validator.validate(c);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(insertGasStationSQL)) {
            statement.setInt(1, c.getId());
            statement.setString(2, c.getGasStationName());
            statement.setInt(3, c.getGasolinePrice());
            statement.setInt(4,c.getDieselPrice());
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(c);
        }
    }

    /**
     * Delete from table the GasStation with the given id (if exists)
     *
     * @param id must not be null.
     * @return an {@code Optional} - returns the entity if it was deleted, otherwise (e.g. id does not exist) null;
     * @throws IllegalArgumentException if the given id is null.
     */
    public Optional<GasStation> delete(Integer id) {
        Optional<GasStation> c = this.findOne(id);
        try {
            c.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(deleteGasStationSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Updates the given gasStation.
     *
     * @param c must not be null.
     * @return an {@code Optional} - null if the entity was not updated (e.g. id does not exist), otherwise returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException if the entity is not valid.
     */
    public Optional<GasStation> update(GasStation c) {
        this.validator.validate(c);
        Optional<GasStation> gasStation = this.findOne(c.getId());
        try {
            gasStation.orElseThrow(Exception::new);
        } catch (Exception e) {
            return Optional.empty();
        }
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(updateGasStationSQL)) {
            statement.setString(1, c.getGasStationName());
            statement.setInt(2, c.getGasolinePrice());
            statement.setInt(3,c.getDieselPrice());
            statement.setInt(4, c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gasStation;
    }

    @Override
    public Page<GasStation> findAll(Pageable pageable) {
        List <GasStation> all = this.findAll();
        int begin = pageable.getPageNumber() * pageable.getPageSize();
        int end = begin + pageable.getPageSize();
        end = Math.min(end, all.size());
        return new CommonPage<>(pageable, all.subList(begin, end));
    }

    @Override
    public Page<GasStation> getNext() {
        page = new CommonPage<>(page.nextPageable());
        return findAll(page.getPageable());
    }

    @Override
    public Page<GasStation> getPrev() {
        page = new CommonPage<>(page.prevPageable());
        return findAll(page.getPageable());
    }
}
