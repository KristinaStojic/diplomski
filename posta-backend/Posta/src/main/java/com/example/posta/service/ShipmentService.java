package com.example.posta.service;

import com.example.posta.dto.*;
import com.example.posta.model.*;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentStatus;
import com.example.posta.model.enums.ShipmentType;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    CounterWorkerRepository counterWorkerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailService emailService;

    public List<ShipmentDTO> getAllShipments(){
        List<ShipmentDTO> ret = new ArrayList<>();

        for(Shipment p: this.shipmentRepository.findAll()){
            ShipmentDTO s = new ShipmentDTO(p);
            ret.add(s);
        }
        return ret;
    }

    public List<ShipmentDTO> searchByCode(String code){
        List<ShipmentDTO> ret = new ArrayList<>();

        for(Shipment s: shipmentRepository.findAll()){
            if(s.getCode().contains(code)){
                ShipmentDTO ss = new ShipmentDTO(s);
                ret.add(ss);
            }
        }
        return ret;
    }

    public Shipment addShipment(AddShipmentDTO dto){
        Shipment s = new Shipment();

        Client client = new Client();
        Role r = roleRepository.findByName("ROLE_CLIENT");
        client.setName(dto.getClient().getName());
        client.setSurname(dto.getClient().getSurname());
        client.setDeleted(false);
        client.setEnabled(true);
        Address clientAddress = new Address(dto.getClientAddress());
        City c = new City(dto.getClientAddress().getCity());
        c.setPostalCode(dto.getClientAddress().getPostalCode());
        Country cnt = new Country();
        cnt.setCountryName(dto.getClientAddress().getCountry());
        countryRepository.save(cnt);
        c.setCountry(cnt);
        cityRepository.save(c);
        clientAddress.setCity(c);
        addressRepository.save(clientAddress);
        client.setAddress(clientAddress);
        client.setRole(r);
        clientRepository.save(client);

        Client receiver = new Client();
        receiver.setName(dto.getReceiver().getName());
        receiver.setSurname(dto.getReceiver().getSurname());
        receiver.setDeleted(false);
        receiver.setEnabled(true);
        Address receiverAddress = new Address(dto.getReceiverAddress());
        City rc = new City(dto.getReceiverAddress().getCity());
        Country cntr = new Country();
        cntr.setCountryName(dto.getReceiverAddress().getCountry());
        countryRepository.save(cntr);
        rc.setCountry(cntr);
        rc.setPostalCode(dto.getReceiverAddress().getPostalCode());
        cityRepository.save(rc);
        receiverAddress.setCity(rc);
        addressRepository.save(receiverAddress);
        receiver.setAddress(receiverAddress);
        receiver.setRole(r);
        clientRepository.save(receiver);

        s.setSender(client);
        s.setReceiver(receiver);
        s.setDate(LocalDateTime.now());
        s.setValue(dto.getValue());
        s.setWeight(dto.getWeight());
        s.setEmailReport(dto.getEmailReport());
        s.setEmail(dto.getEmail());
        s.setPersonalDelivery(dto.getPersonalDelivery());
        s.setReturnReceipt(dto.getReturnReceipt());
        s.setCode(UUID.randomUUID().toString().toUpperCase().substring(0,11));
        Worker w = workerRepository.findByEmail(dto.getCounterWorker());
        CounterWorker cw = counterWorkerRepository.getById(w.getId());

        s.setCounterWorker(cw);

        if(dto.getShipmentType().equals(ShipmentType.LETTER.toString())){
            s.setShipmentType(ShipmentType.LETTER);
        }
        else{
            s.setShipmentType(ShipmentType.PACKAGE);
        }

        if(dto.getLetterType().equals(LetterType.ORDINARY.toString())){
            s.setLetterType(LetterType.ORDINARY);
        }
        else if(dto.getLetterType().equals(LetterType.VALUE.toString())){
            s.setLetterType(LetterType.VALUE);
        }
        else if(dto.getLetterType().equals(LetterType.REGISTERED.toString())){
            s.setLetterType(LetterType.REGISTERED);
        }

        Double price = findTotalPrice(dto);
        s.setTotalPrice(price);
        s.setShipmentStatus(ShipmentStatus.RECEIVED);
        return shipmentRepository.save(s);
    }

    private Double findTotalPrice(AddShipmentDTO dto){
        double total = 0.0;

        if(dto.getShipmentType().equals(ShipmentType.PACKAGE.toString())){
            total += 100;

            if(1000 < dto.getWeight() && dto.getWeight() < 5000){
                total += 500;
            }
            else if(5000 <= dto.getWeight() && dto.getWeight() < 15000){
                total += 700;
            }
            else if(dto.getWeight() >= 15000){
                total += 1000;
            }
        }
        else{
            total += 50;

            if(dto.getWeight() < 300){
                total += 100;
            }
            else if(300 <= dto.getWeight() && dto.getWeight() < 500){
                total += 200;
            }
            else if(dto.getWeight() >= 500 && dto.getWeight() < 1000){
                total += 300;
            }
        }

        if(dto.getEmailReport()){
            total += 100;
        }
        if(dto.getReturnReceipt()){
            total += 50;
        }


        return total;
    }


    public Shipment editShipmentStatus(EditShipmentDTO dto) throws MessagingException {

        Shipment s = this.shipmentRepository.findById(dto.getId()).orElseGet(null);
        if(s == null){
            return null;
        }

        switch (dto.getNewStatus()) {
            case "Чека на испоруку" -> s.setShipmentStatus(ShipmentStatus.RECEIVED);
            case "Достављено" -> {
                s.setShipmentStatus(ShipmentStatus.DELIVERED);
                if(dto.getEmailReport()){
                    emailService.sendMailForDeliveredShipment(dto.getEmail(),dto.getCode());
                }
            }
            case "Послато на испоруку" -> s.setShipmentStatus(ShipmentStatus.SENDING);
            case "Враћено" -> s.setShipmentStatus(ShipmentStatus.RETURNED);
        }

        return this.shipmentRepository.save(s);
    }
}
