package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProcessAbsenceRequestDTO {

    private Long id;
    private String worker;
    private Boolean approved;
}
