package com.example.posta.service;

import com.example.posta.dto.PaymentDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.Payment;
import com.example.posta.model.Worker;
import com.example.posta.repository.PaymentRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    WorkerRepository workerRepository;

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
}
