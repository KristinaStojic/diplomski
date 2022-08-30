package com.example.posta.service;

import com.example.posta.dto.AddPayoffDTO;
import com.example.posta.dto.EditPayoffStatusDTO;
import com.example.posta.dto.PayoffDTO;
import com.example.posta.dto.SearchPayoffDTO;
import com.example.posta.model.*;
import com.example.posta.model.enums.PayoffType;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayoffService {

    @Autowired
    PayoffRepository payoffRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    PostOfficeRepository postOfficeRepository;

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
    CounterWorkerRepository counterWorkerRepository;

    @Autowired
    AccountingWorkerRepository accountingWorkerRepository;

    public List<PayoffDTO> getAllPayoffs(String worker){
        List<PayoffDTO> ret = new ArrayList<>();

        Worker w = workerRepository.findByEmail(worker);
        if(w == null){
            return null;
        }

        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        for(Payoff p: payoffRepository.findAll()){
            if(p.getAccountingWorker().getPostOffice().getId() == po.getId()){
                PayoffDTO pdto = new PayoffDTO(p);
                ret.add(pdto);
            }
        }

        return ret;
    }


    public Boolean changePaidOffStatus(EditPayoffStatusDTO dto){
        Payoff p = payoffRepository.findById(dto.getId()).orElseGet(null);
        if(p == null){
            return false;
        }

        Worker w = workerRepository.findByEmail(dto.getWorker());

        for(CounterWorker cw: counterWorkerRepository.findAll()){
            if(cw.getId().equals(w.getId())){
                p.setCounterWorker(cw);
            }
        }


        p.setPaidOff(true);
        p.setDate(LocalDateTime.now());
        payoffRepository.save(p);

        return true;
    }


    public List<PayoffDTO> search(SearchPayoffDTO dto){
        List<PayoffDTO> ret = new ArrayList<>();

        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }

        for(Payoff p: payoffRepository.findAll()){
            if(p.getCounterWorker().getPostOffice().getId() == w.getPostOffice().getId()){
                if(p.getClient().getName().toUpperCase().contains(dto.getCriteria().toUpperCase())
                        || p.getClient().getSurname().toUpperCase().contains(dto.getCriteria().toUpperCase())
                            || p.getClient().getAddress().getStreet().toUpperCase().contains(dto.getCriteria().toUpperCase())
                                || p.getClient().getAddress().getStreetNumber().toUpperCase().contains(dto.getCriteria().toUpperCase())){
                    PayoffDTO po = new PayoffDTO(p);
                    ret.add(po);
                }
            }
        }
        return ret;
    }

    public Payoff addPayoff(AddPayoffDTO dto){

        Payoff p = new Payoff();

        Worker w = workerRepository.findByEmail(dto.getCounterWorker());
        AccountingWorker cw = accountingWorkerRepository.findById(w.getId()).orElseGet(null);
        p.setAccountingWorker(cw);
        //p.setPost_office(w.getPostOffice().getId());
        p.setCounterWorker(null);

        p.setAmount(Double.valueOf(dto.getAmount()));
        p.setPaidOff(false);
        p.setCurrency(dto.getCurrency());

        Client client = new Client();
        Role r = roleRepository.findByName("ROLE_CLIENT");
        client.setName(dto.getClient().getName());
        client.setSurname(dto.getClient().getSurname());
        client.setDeleted(false);
        client.setEnabled(true);
        Address clientAddress = new Address(dto.getAddress());
        City c = new City(dto.getAddress().getCity());
        c.setPostalCode(dto.getAddress().getPostalCode());
        Country cnt = new Country();
        cnt.setCountryName(dto.getAddress().getCountry());
        countryRepository.save(cnt);
        c.setCountry(cnt);
        cityRepository.save(c);
        clientAddress.setCity(c);
        addressRepository.save(clientAddress);
        client.setAddress(clientAddress);
        client.setRole(r);
        clientRepository.save(client);

        p.setClient(client);

        if(dto.getPayoffType().equals(PayoffType.PENSION.toString())){
            p.setPayoffType(PayoffType.PENSION);
        }
        if(dto.getPayoffType().equals(PayoffType.CHILDRENS_ALLOWANCE.toString())){
            p.setPayoffType(PayoffType.CHILDRENS_ALLOWANCE);
        }
        if(dto.getPayoffType().equals(PayoffType.DISABILITY_ALLOWANCE.toString())){
            p.setPayoffType(PayoffType.DISABILITY_ALLOWANCE);
        }

        return payoffRepository.save(p);
    }

}
