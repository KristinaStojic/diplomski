package com.example.posta.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Payment extends FinancialService{

    public Payment(FinancialService u) {
        super(u);
    }

    @OneToOne(targetEntity = Client.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id")
    private Client receiver;

//    @OneToOne(targetEntity = Address.class,cascade = CascadeType.MERGE)
//    @JoinColumn(name = "receiver_address_id")
//    private Address receiver_address;

    @Column(unique = false, nullable = false)
    private String purpose;

    @Column(unique = false, nullable = false)
    private String paymentCode;

    @Column(unique = false, nullable = false)
    private String receiverAccount;

    @Column(unique = false, nullable = false)
    private String model;

    @Column(unique = false, nullable = false)
    private String referenceNumber;

    @Column(unique = false, nullable = false)
    private String receivingPlace;

    @Column(unique = false, nullable = false)
    private String currency;

}
