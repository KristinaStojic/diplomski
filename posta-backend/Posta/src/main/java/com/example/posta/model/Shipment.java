package com.example.posta.model;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentStatus;
import com.example.posta.model.enums.ShipmentType;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "shipment_type", unique = false)
    private ShipmentType shipmentType;

    @Column(name = "letter_type", unique = false, nullable = true)
    private LetterType letterType;

    @Column(name = "shipment_status", unique = false, nullable = true)
    private ShipmentStatus shipmentStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiving_post_office_id", nullable = true)
    @JsonBackReference
    private PostOffice receivingPostOffice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivering_post_office_id", nullable = true)
    @JsonBackReference
    private PostOffice deliveringPostOffice;

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

    @Column(name = "email_report", unique = false)
    private Boolean emailReport;

    @Column(name = "email", unique = false)
    private String email;

    @Column(name = "sent_date", unique = false, nullable = false)
    private LocalDateTime date;

    @Column(name = "total_price", unique = false)
    private Double totalPrice;
}
