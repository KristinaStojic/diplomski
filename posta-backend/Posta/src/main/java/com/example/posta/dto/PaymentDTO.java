package com.example.posta.dto;

import com.example.posta.model.Address;
import com.example.posta.model.Client;
import com.example.posta.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String receiver;
    private Address receiver_address;
    private String purpose;
    private String paymentCode;
    private String receiverAccount;
    private String model;
    private String referenceNumber;
    private String counterWorker;
    private Client client;
    private Double amount;
    private String currency;
    private LocalDateTime date;

    public PaymentDTO(Payment p){
        this.receiver = p.getReceiver();
        this.receiver_address = p.getReceiver_address();
        this.purpose = p.getPurpose();
        this.paymentCode = p.getPaymentCode();
        this.receiverAccount = p.getReceiverAccount();
        this.model = p.getModel();
        this.referenceNumber = p.getReferenceNumber();
        this.counterWorker = p.getCounterWorker().getEmail();
        this.client = p.getClient();
        this.amount = p.getAmount();
        this.currency = p.getCurrency();
        this.date = p.getDate();
    }
}
