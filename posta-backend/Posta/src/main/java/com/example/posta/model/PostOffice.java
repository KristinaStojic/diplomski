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
@Table(name = "post_office")
public class PostOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phone_number", unique = false, nullable = true)
    private String phoneNumber;

    @Column(name = "employee_number", unique = false, nullable = true)
    private Integer employeeNumber;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;


    @OneToOne(targetEntity = Address.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;

//    @OneToOne(targetEntity = Manager.class,cascade = CascadeType.MERGE)
//    @JoinColumn(name = "manager_id")
//    private Manager manager;
}
