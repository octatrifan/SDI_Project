package repo.dbRepo;

import java.util.stream.Stream;

/**
 * author: radu
 */
public interface Page<T> {
    Pageable getPageable();

    Pageable nextPageable();

    Pageable prevPageable();

    Stream<T> getContent();
}
