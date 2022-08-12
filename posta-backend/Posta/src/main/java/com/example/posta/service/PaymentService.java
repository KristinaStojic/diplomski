package com.example.posta.service;

import com.example.posta.dto.*;
import com.example.posta.model.*;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

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

    public List<PaymentDTO> getAllPayments(){
        List<PaymentDTO> ret = new ArrayList<>();

        for(Payment p: this.paymentRepository.findAll()){
                PaymentDTO payment = new PaymentDTO(p);
                ret.add(payment);
        }
        return ret;
    }

    public List<PaymentDTO> getAllPaymentsByWorker(String worker){
        List<PaymentDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(worker);

        for(Payment p: this.paymentRepository.findAll()){
            if(p.getCounterWorker().getEmail().equals(w.getEmail())){
                PaymentDTO payment = new PaymentDTO(p);
                ret.add(payment);
            }
        }
        return ret;
    }

    public Payment addPayment(AddPaymentDTO dto){
        Payment p = new Payment();

        Client client = new Client();
        client.setName(dto.getClient().getName());
        client.setSurname(dto.getClient().getSurname());
        client.setDeleted(false);
        client.setEnabled(true);
        Address clientAddress = new Address(dto.getClientAddress());
        City c = new City(dto.getClientAddress().getCity());
        Country cnt = new Country();
        cnt.setCountryName(dto.getClientAddress().getCountry());
        countryRepository.save(cnt);
        c.setCountry(cnt);
        cityRepository.save(c);
        clientAddress.setCity(c);
        addressRepository.save(clientAddress);
        client.setAddress(clientAddress);
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
        cityRepository.save(rc);
        receiverAddress.setCity(rc);
        addressRepository.save(receiverAddress);
        receiver.setAddress(receiverAddress);
        clientRepository.save(receiver);

        p.setClient(client);
        p.setReceiver(receiver);
        p.setPaymentCode(dto.getPaymentCode());
        p.setModel(dto.getModel());
        p.setAmount(dto.getAmount());
        p.setPurpose(dto.getPurpose());
        p.setReceiverAccount(dto.getReceiverAccount());
        p.setCurrency(dto.getCurrency());
        p.setDate(LocalDateTime.now());
        p.setReceivingPlace(dto.getReceivingPlace());
        p.setReferenceNumber(dto.getReferenceNumber());

        Worker w = workerRepository.findByEmail(dto.getCounterWorker());
        CounterWorker cw = counterWorkerRepository.getById(w.getId());

        p.setCounterWorker(cw);

        return paymentRepository.save(p);
    }


    public Map<Integer, Integer> getNumberofPaymentsYearly() {
        Map<Integer, Integer> map = new HashMap<>();
        for (Payment r : paymentRepository.findAll()) {
                if (!map.containsKey(r.getDate().getYear())) {
                    Integer n = countPaymentPerYear(r.getDate().getYear());
                    map.put(r.getDate().getYear(), n);
                }

        }
        return map;
    }

    private Integer countPaymentPerYear(Integer year) {
        Integer n = 0;

        for (Payment r : paymentRepository.findAll()) {
                if (r.getDate().getYear() == year) {
                    n++;
                }
        }

        return n;
    }


    public Map<String, Integer> getNumberofPaymentsMonthly(String year) {
        Map<String, Integer> map = new HashMap<>();
        for (Payment r : paymentRepository.findAll()) {
                if (!map.containsKey(r.getDate().getMonth().toString())) {
                    Integer n = countPaymentnPerMonth(r.getDate().getMonth().toString(), Integer.parseInt(year));
                    map.put(r.getDate().getMonth().toString(), n);
                }

        }

        return map;
    }


    private Integer countPaymentnPerMonth(String month, Integer year) {
        Integer n = 0;

        for (Payment r : paymentRepository.findAll()) {
            if (r.getDate().getMonth().toString().equals(month) && r.getDate().getYear() == year) {
                n++;
            }
        }
        return n;
    }
}
