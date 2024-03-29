package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "financial_service")
@Inheritance(strategy = InheritanceType.JOINED)
public class FinancialService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "counter_worker_id", nullable = true)
    @JsonBackReference
    private Worker worker;

    @OneToOne(targetEntity = Client.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id")
    private Client client;

//    @OneToOne(targetEntity = Address.class,cascade = CascadeType.MERGE)
//    @JoinColumn(name = "client_address_id")
//    private Address client_address;

    @Column(unique = false, nullable = false)
    private Double amount;

    @Column(name = "date", unique = false, nullable = true)
    private LocalDateTime date;

    @Column(unique = false, nullable = false)
    private String currency;



    public FinancialService(FinancialService u) {
        //this.counterWorker = u.getCounterWorker();
        //this.client = u.getClient();
        this.amount = u.getAmount();
        this.currency = u.getCurrency();
        this.date = u.getDate();
    }
}
