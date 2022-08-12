package com.example.posta.dto;

import com.example.posta.model.*;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentStatus;
import com.example.posta.model.enums.ShipmentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ShipmentDTO {

    private Long id;
    private String shipmentType;
    private String letterType;
    private String shipmentStatus;
    private String counterWorker;
    private String accountingWorker;
    private Region region;
    private String sender;
    private String receiver;
    private Double weight;
    private Double value;
    private Boolean personalDelivery;
    private Boolean returnReceipt;
    private Boolean emailReport;
    private String email;
    private String date;
    private Double totalPrice;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
    private String code;
    private String receiverAddress;

    public ShipmentDTO(Shipment s){
        this.id = s.getId();
        this.code = s.getCode();
        if(s.getShipmentType().toString().equals(ShipmentType.LETTER.toString())){
            this.shipmentType = "Писмоносна услуга";
        }
        else{
            this.shipmentType = "Пакет";
        }
        this.counterWorker = s.getCounterWorker().getEmail();
        this.receiverAddress = s.getReceiver().getAddress().getStreet() + " " + s.getReceiver().getAddress().getStreetNumber() + ", " + s.getReceiver().getAddress().getCity().getPostalCode() + " " + s.getReceiver().getAddress().getCity().getCityName() + ", " + s.getReceiver().getAddress().getCity().getCountry().getCountryName();
        if(s.getAccountingWorker() != null && s.getRegion() != null && s.getLetterType() != null){
            this.accountingWorker = s.getAccountingWorker().getEmail();
            this.region = s.getRegion();

            if(s.getLetterType().toString().equals(LetterType.VALUE.toString())){
                this.letterType = "Вриједносна пошиљка";
            }
            else if(s.getLetterType().toString().equals(LetterType.ORDINARY.toString())){
                this.letterType = "Писмо";
            }
            else{
                this.letterType = "Препоручена пошиљка";
            }
        }
        this.sender = s.getSender().getName() + " " + s.getSender().getSurname();
        this.receiver = s.getReceiver().getName() + " " + s.getReceiver().getSurname();
        this.weight = s.getWeight();
        this.value = s.getValue();
        this.personalDelivery = s.getPersonalDelivery();
        this.returnReceipt = s.getReturnReceipt();
        this.emailReport = s.getEmailReport();
        this.email = s.getEmail();
        this.date = s.getDate().format(formatter);
        this.totalPrice = s.getTotalPrice();

        if(s.getShipmentStatus().equals(ShipmentStatus.RECEIVED)){
            this.shipmentStatus = "Чека на испоруку";
        }
        else if(s.getShipmentStatus().equals(ShipmentStatus.DELIVERED)){
            this.shipmentStatus = "Достављено";
        }
        else if(s.getShipmentStatus().equals(ShipmentStatus.RETURNED)){
            this.shipmentStatus = "Враћено";
        }
        else if(s.getShipmentStatus().equals(ShipmentStatus.SENDING)){
            this.shipmentStatus = "Испорука у току";
        }
    }
}
