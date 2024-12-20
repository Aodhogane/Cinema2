package com.example.OnlineSinema.dto.filmDTO;

public class FilmSalesDTO {
    private String title;
    private int ticketCount;

    public FilmSalesDTO(String title, int ticketCount) {
        this.title = title;
        this.ticketCount = ticketCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}