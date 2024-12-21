package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Access")
public class Access extends BaseEntity {

    private String registered;
    private User user;

    public Access(String registered) {
        this.registered = registered;
    }

    protected Access() {}

    @Column(name = "access")
    public String getRegistered() {
        return registered;
    }
    public void setRegistered(String registered) {
        this.registered = registered;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}