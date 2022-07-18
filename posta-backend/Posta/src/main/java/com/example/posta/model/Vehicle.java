package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_office_id", nullable = false)
    @JsonBackReference
    private PostOffice postOffice;

    @OneToOne(targetEntity = Worker.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id", nullable = true)
    private Worker worker;
}
