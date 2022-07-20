package com.example.posta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class AddManagerDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String phoneNumber;
}
