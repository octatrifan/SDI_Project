package repo.dbRepo.impl;

import repo.dbRepo.Page;
import repo.dbRepo.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommonPage<T> implements Page<T> {
    private final List<T> content;
    private final Pageable pageable;

    CommonPage(Pageable pageable, List <T> content) {
        this.pageable = pageable;
        this.content = content;
    }

    CommonPage(Pageable pageable) {
        this(pageable, new ArrayList<>());
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Pageable nextPageable() {
        return new CommonPageable(pageable.getPageNumber() + 1, pageable.getPageSize());
    }

    @Override
    public Pageable prevPageable() {
        return new CommonPageable(pageable.getPageNumber() - 1, pageable.getPageSize());
    }

    @Override
    public Stream<T> getContent() {
        return content.stream();
    }
}
