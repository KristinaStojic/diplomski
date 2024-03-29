package com.example.posta.dto;

import com.example.posta.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddPayoffDTO {
    private Client client;
    private AddressDTO address;
    private String counterWorker;
    private String amount;
    private String currency;
    private String payoffType;
}
