package com.example.posta.dto;

import com.example.posta.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private String name;
    private String surname;
    private String email;
    private String role;
    private String phoneNumber;

    public ManagerDTO(Manager m){
        this.name = m.getName();
        this.surname = m.getSurname();
        this.email = m.getEmail();
        this.role = m.getRole().getName();
        this.phoneNumber = m.getPhoneNumber();
    }
}
