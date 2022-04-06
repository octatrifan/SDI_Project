package model;

/**
 * Entity Base class
 * Client, Rental and Car
 * will inherit from Entity
 *
 * @author Octa.
 *
 */


public class BaseEntity<ID> {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}

