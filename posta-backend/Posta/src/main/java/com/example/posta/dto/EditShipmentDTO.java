package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EditShipmentDTO {

    private Long id;
    private String newStatus;
    private String email;
    private String code;
    private Boolean emailReport;
}
