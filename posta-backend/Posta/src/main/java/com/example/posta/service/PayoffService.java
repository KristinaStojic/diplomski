package com.example.posta.service;

import com.example.posta.dto.AddPayoffDTO;
import com.example.posta.dto.PayoffDTO;
import com.example.posta.dto.SearchPayoffDTO;
import com.example.posta.model.*;
import com.example.posta.model.enums.LetterType;
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
            if(p.getCounterWorker().getPostOffice().getId() == po.getId()){
                PayoffDTO pdto = new PayoffDTO(p);
                ret.add(pdto);
            }
        }

        return ret;
    }


    public Boolean changePaidOffStatus(Long id){
        Payoff p = payoffRepository.findById(id).orElseGet(null);
        if(p == null){
            return false;
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
        CounterWorker cw = counterWorkerRepository.findById(w.getId()).orElseGet(null);
        p.setCounterWorker(cw);

        p.setAmount(dto.getAmount());
        p.setPaidOff(false);

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
