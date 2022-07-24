package com.example.posta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Postman extends Worker{
    public Postman(Worker u) {
        super(u);
    }

}
