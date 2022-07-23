package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", unique = false, nullable = false)
    private String content;

    @Column(name = "creation_date", unique = false, nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "manager_id", nullable = false)
    @JsonBackReference
    private Manager manager;
}
