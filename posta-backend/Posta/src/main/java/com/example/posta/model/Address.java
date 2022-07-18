package com.example.posta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street", unique = false, nullable = true)
    private String street;

    @Column(name = "streetNumber", unique = false, nullable = true)
    private String streetNumber;

    @Column(name = "longitude", unique = false, nullable = true)
    private double longitude;

    @Column(name = "latitude", unique = false, nullable = true)
    private double latitude;

    @OneToOne(targetEntity = City.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;
}
