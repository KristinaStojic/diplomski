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
public class AddShipmentDTO {
    private Client receiver;
    private AddressDTO receiverAddress;
    private Client client;
    private AddressDTO clientAddress;
    private String counterWorker;
    private String date;
    private Double weight;
    private Double value;
    private Boolean personalDelivery;
    private Boolean returnReceipt;
    private Boolean smsReport;
    private String shipmentType;
    private String letterType;
    private String smsNumber;
}