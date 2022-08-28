package com.example.posta.service;

import com.example.posta.dto.PayoffDTO;
import com.example.posta.model.Payoff;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
import com.example.posta.repository.PayoffRepository;
import com.example.posta.repository.PostOfficeRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
