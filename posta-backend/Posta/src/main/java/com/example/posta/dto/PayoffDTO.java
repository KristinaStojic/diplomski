package com.example.posta.dto;

import com.example.posta.model.Client;
import com.example.posta.model.Payment;
import com.example.posta.model.Payoff;
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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

    public PayoffDTO(Payoff p){
        this.id = p.getId();
        this.client = p.getClient();
        this.amount = p.getAmount();
        this.payoffType= p.getPayoffType().toString();
        this.paidOff = p.getPaidOff();
        if(p.getDate() != null){
            this.date = p.getDate().format(formatter);
        }
    }

}
