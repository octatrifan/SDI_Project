package repo.dbRepo.impl;

import repo.dbRepo.Pageable;

public class CommonPageable implements Pageable {
    private final int pageNumber;
    private final int pageSize;

    public CommonPageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public CommonPageable(int pageNumber) {
        this(pageNumber, 5);
    }

    /**
     * @return page number; page numbers start at 0.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @return the number of elements in a page.
     */
    public int getPageSize() {
        return pageSize;
    }
}
