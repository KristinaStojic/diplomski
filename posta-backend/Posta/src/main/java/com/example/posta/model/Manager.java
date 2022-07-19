package com.example.posta.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.jdbc.Work;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Manager extends Worker {
    public Manager(Worker u) {
        super(u);
    }
}
