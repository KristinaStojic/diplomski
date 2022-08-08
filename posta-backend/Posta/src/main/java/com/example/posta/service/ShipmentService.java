package com.example.posta.service;

import com.example.posta.dto.AddPaymentDTO;
import com.example.posta.dto.AddShipmentDTO;
import com.example.posta.model.*;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentStatus;
import com.example.posta.model.enums.ShipmentType;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        cnt.setCountryName(dto.getReceiverAddress().getCountry());
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
        s.setDate(LocalDate.now());
        s.setValue(dto.getValue());
        s.setWeight(dto.getWeight());
        s.setSMSReport(dto.getSmsReport());
        s.setSmsNumber(dto.getSmsNumber());
        s.setPersonalDelivery(dto.getPersonalDelivery());
        s.setReturnReceipt(dto.getReturnReceipt());

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
            else if(5000 < dto.getWeight() && dto.getWeight() < 15000){
                total += 1000;
            }
            else if(dto.getWeight() > 15000){
                total += 1500;
            }
        }
        else{
            total += 50;

            if(dto.getWeight() < 300){
                total += 100;
            }
            else if(300 < dto.getWeight() && dto.getWeight() < 500){
                total += 200;
            }
            else if(dto.getWeight() > 500 && dto.getWeight() < 1000){
                total += 300;
            }
        }

        if(dto.getSmsReport()){
            total += 200;
        }
        if(dto.getReturnReceipt()){
            total += 50;
        }

        return total;
    }
}
