package com.example.posta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Admin extends Worker{
    public Admin(Worker u) {
        super(u);
    }
}
