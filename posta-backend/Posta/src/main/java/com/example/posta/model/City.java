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
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "postalCode", unique = false, nullable = true)
    private String postalCode;

    @Column(unique = false, nullable = true)
    private String cityName;

    @OneToOne(targetEntity = Country.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "country_id")
    private Country country;
}
