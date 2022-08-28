package com.example.posta.dto;

import com.example.posta.model.Client;
import com.example.posta.model.Payment;
import com.example.posta.model.Payoff;
import com.example.posta.model.enums.PayoffType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PayoffDTO {
    private Long id;
    private Client client;
    private Double amount;
    private String date;
    private String payoffType;
    private Boolean paidOff;
    private String clientAddress;
    private String worker;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

    public PayoffDTO(Payoff p){
        this.id = p.getId();
        this.client = p.getClient();
        this.amount = p.getAmount();
        if(p.getPayoffType().toString().equals(PayoffType.PENSION.toString())){
            this.payoffType= "Пензија";
        }else if(p.getPayoffType().toString().equals(PayoffType.CHILDRENS_ALLOWANCE.toString())){
            this.payoffType= "Дјечији додатак";
        }else if(p.getPayoffType().toString().equals(PayoffType.DISABILITY_ALLOWANCE.toString())){
            this.payoffType= "Инвалиднина";
        }

        this.paidOff = p.getPaidOff();
        this.clientAddress = p.getClient().getAddress().getStreet() + " " + p.getClient().getAddress().getStreetNumber()
                + ", " + p.getClient().getAddress().getCity().getPostalCode() + " " + p.getClient().getAddress().getCity().getCityName();
        if(p.getDate() != null){
            this.date = p.getDate().format(formatter);
        }
    }

}
