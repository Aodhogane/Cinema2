package com.example.OnlineSinema.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Access")
public class Access extends BaseEntity {

    private String registered;

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

}