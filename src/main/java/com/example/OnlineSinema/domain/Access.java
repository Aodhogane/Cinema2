package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Access")
public class Access extends BaseEntity {

    private String registered;
    private List<User> users;

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

    @OneToMany(mappedBy = "access", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<User>  getUser() {
        return users;
    }
    public void setUser(List<User> users) {
        this.users = users;
    }
}