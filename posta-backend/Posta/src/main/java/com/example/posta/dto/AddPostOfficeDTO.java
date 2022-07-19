package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddPostOfficeDTO {
    private String phoneNumber;
    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;
    private Long managerId;
    private Double longitude;
    private Double latitude;

}
