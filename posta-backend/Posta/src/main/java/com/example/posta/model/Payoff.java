package com.example.posta.model;

import com.example.posta.model.enums.PayoffType;
import com.example.posta.model.enums.ShipmentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Payoff extends FinancialService{

    public Payoff(FinancialService f) {
        super(f);
    }

    @Column(name = "type", unique = false)
    private PayoffType payoffType;

    @Column(name = "paid_off", unique = false)
    private Boolean paidOff;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "accounting_worker_id", nullable = false)
    @JsonBackReference
    private AccountingWorker accountingWorker;

}
