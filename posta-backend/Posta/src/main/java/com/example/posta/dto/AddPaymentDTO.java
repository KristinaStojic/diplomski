package com.example.posta.dto;

import com.example.posta.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddPaymentDTO {
    private Client receiver;
    private AddressDTO receiverAddress;
    private Client client;
    private AddressDTO clientAddress;
    private String purpose;
    private String paymentCode;
    private String receiverAccount;
    private String model;
    private String referenceNumber;
    private String counterWorker;
    private Double amount;
    private String currency;
    private String date;
    private String receivingPlace;
}
