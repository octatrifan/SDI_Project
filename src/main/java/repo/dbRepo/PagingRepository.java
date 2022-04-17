package repo.dbRepo;

import model.BaseEntity;
import model.Car;
import repo.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable, T extends BaseEntity<ID>>
        extends Repository<ID, T> {
    /**
     * returns all entities from the given page
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * returns all entities from the next page
     * @return
     */
    Page<T> getNext();

    /**
     * returns all entities from the previous page
     * @return
     */
    Page<T> getPrev();
}
