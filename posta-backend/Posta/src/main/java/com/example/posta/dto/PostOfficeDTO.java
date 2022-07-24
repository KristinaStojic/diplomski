package com.example.posta.dto;

import com.example.posta.model.PostOffice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostOfficeDTO {
    private Long id;
    private String phoneNumber;
    private Integer employeeNumber;
    private String street;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;
    private Long managerID;

    public PostOfficeDTO(PostOffice p){
        this.id = p.getId();
        this.phoneNumber = p.getPhoneNumber();
        this.employeeNumber = p.getEmployeeNumber();
        this.street = p.getAddress().getStreet() + " " + p.getAddress().getStreetNumber();
        this.city = p.getAddress().getCity().getCityName();
        this.country = p.getAddress().getCity().getCountry().getCountryName();
        this.longitude = p.getAddress().getLongitude();
        this.latitude = p.getAddress().getLatitude();
        //this.managerID = p.getManager().getId();
    }
}
