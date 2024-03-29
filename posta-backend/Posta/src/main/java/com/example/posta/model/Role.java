package com.example.posta.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="name",unique = true, nullable = false)
    String name;


    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
