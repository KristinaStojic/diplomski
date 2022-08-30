package com.example.posta.dto;

import com.example.posta.model.Address;
import com.example.posta.model.Client;
import com.example.posta.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private String receiver;
    private AddressDTO receiver_address;
    private String receiverAddressFull;
    private String purpose;
    private String paymentCode;
    private String receiverAccount;
    private String model;
    private String referenceNumber;
    private String counterWorker;
    private Client client;
    private String clientName;
    private String clientAddressFull;
    private Double amount;
    private String currency;
    private String date;
    private String receivingPlace;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

    public PaymentDTO(Payment p){
        this.id = p.getId();
        this.receivingPlace = p.getReceivingPlace();
        this.clientName = p.getClient().getName() + " " + p.getClient().getSurname();
        this.receiver = p.getReceiver().getName() + " " + p.getReceiver().getSurname();
        this.receiver_address = new AddressDTO(p.getReceiver().getAddress());
        this.receiverAddressFull = p.getReceiver().getAddress().getStreet() + " " + p.getReceiver().getAddress().getStreetNumber() + ", " + p.getReceiver().getAddress().getCity().getPostalCode() + " " + p.getReceiver().getAddress().getCity().getCityName() + ", " + p.getReceiver().getAddress().getCity().getCountry().getCountryName();
        this.clientAddressFull = p.getClient().getAddress().getStreet() + " " + p.getClient().getAddress().getStreetNumber() + ", " + p.getClient().getAddress().getCity().getPostalCode() + " " + p.getClient().getAddress().getCity().getCityName() + ", " + p.getClient().getAddress().getCity().getCountry().getCountryName();
        this.purpose = p.getPurpose();
        this.paymentCode = p.getPaymentCode();
        this.receiverAccount = p.getReceiverAccount();
        this.model = p.getModel();
        this.referenceNumber = p.getReferenceNumber();
        this.counterWorker = p.getCounterWorker().getEmail();
        this.client = p.getClient();
        this.amount = p.getAmount();
        this.currency = p.getCurrency();
        this.date = p.getDate().format(formatter);
    }
}
