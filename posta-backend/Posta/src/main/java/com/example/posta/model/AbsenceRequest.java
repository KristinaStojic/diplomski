package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.jdbc.Work;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class AbsenceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "worker_id", nullable = false)
    @JsonBackReference
    private Worker worker;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manager_id", nullable = true)
    @JsonBackReference
    private Manager manager;
}
