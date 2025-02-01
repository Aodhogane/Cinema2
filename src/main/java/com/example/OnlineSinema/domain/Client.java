package com.example.OnlineSinema.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "client")
public class Client extends BaseEntity{

    private String name;
    private Set<Reviews> reviews;
    private User user;

    public Client(String name, Set<Reviews> reviews) {
        this.name = name;
        this.reviews = reviews;
    }

    protected Client(){}

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    public Set<Reviews> getReviews() {
        return reviews;
    }
    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
