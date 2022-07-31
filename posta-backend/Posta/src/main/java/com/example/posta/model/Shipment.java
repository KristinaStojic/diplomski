package com.example.posta.model;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "shipment_type", unique = false)
    private ShipmentType shipmentType;

    @Column(name = "letter_type", unique = false, nullable = true)
    private LetterType letterType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "counter_worker_id", nullable = true)
    @JsonBackReference
    private CounterWorker counterWorker;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "accounting_worker_id", nullable = true)
    @JsonBackReference
    private AccountingWorker accountingWorker;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "region_id", nullable = true)
    @JsonBackReference
    private Region region;

    @OneToOne(targetEntity = Client.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id")
    private Client sender;

    @OneToOne(targetEntity = Client.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @Column(name = "weight", unique = false)
    private Double weight;

    @Column(name = "value", unique = false)
    private Double value;

    @Column(name = "personal_delivery", unique = false)
    private Boolean personalDelivery;

    @Column(name = "return_receipt", unique = false)
    private Boolean returnReceipt;

    @Column(name = "SMS_report", unique = false)
    private Boolean SMSReport;

    @Column(name = "sent_date", unique = false, nullable = false)
    private LocalDate date;
}
