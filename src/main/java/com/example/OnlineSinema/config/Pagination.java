package com.example.OnlineSinema.config;

public class Pagination {
    private int page;
    private int totalPages;
    private int totalItems;

    public Pagination(int page, int totalPages, int totalItems) {
        this.page = page;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}