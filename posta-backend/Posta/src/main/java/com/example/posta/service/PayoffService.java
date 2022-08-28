package com.example.posta.service;

import com.example.posta.dto.PayoffDTO;
import com.example.posta.dto.SearchPayoffDTO;
import com.example.posta.model.Payoff;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
import com.example.posta.repository.PayoffRepository;
import com.example.posta.repository.PostOfficeRepository;
import com.example.posta.repository.WorkerRepository;
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

}
