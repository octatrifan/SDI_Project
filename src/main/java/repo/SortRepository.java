package repo;

import model.BaseEntity;
import repo.Sorting.Sort;

/**
 * Sorting repo interface
 *
 * @author Liviu.
 */

public interface SortRepository<ID, T extends BaseEntity<ID>>
{
    public Iterable<T> sort(Sort sortMethod);
}
