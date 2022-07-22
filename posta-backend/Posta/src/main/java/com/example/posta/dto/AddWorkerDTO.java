package com.example.posta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddWorkerDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String phoneNumber;
    private String managerEmail;
}
