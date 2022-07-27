package com.example.posta.dto;

import com.example.posta.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String city;
    private String country;
    private String street;
    private String streetNumber;
    private String postalCode;

    public AddressDTO(Address a){
        this.city = a.getCity().getCityName();
        this.country = a.getCity().getCountry().getCountryName();
        this.street = a.getStreet();
        this.streetNumber = a.getStreetNumber();
        this.postalCode = a.getCity().getPostalCode();
    }
}
