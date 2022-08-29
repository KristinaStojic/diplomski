package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EditInfoDTO {
    private String oldEmail;
    private String email;
    private String name;
    private String surname;
    private String phone;
}
