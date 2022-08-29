package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChangePasswordDTO {
    private String email;
    private String oldPassword;
    private String password;
    private String passwordRepeated;

}
