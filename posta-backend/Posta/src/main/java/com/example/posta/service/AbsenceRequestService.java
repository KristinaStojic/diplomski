package com.example.posta.service;

import com.example.posta.dto.AbsenceRequestDTO;
import com.example.posta.dto.ProcessAbsenceRequestDTO;
import com.example.posta.model.AbsenceRequest;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
import com.example.posta.repository.AbsenceRequestRepository;
import com.example.posta.repository.PostOfficeRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AbsenceRequestService {

    @Autowired
    AbsenceRequestRepository absenceRequestRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    PostOfficeRepository postOfficeRepository;



    public List<AbsenceRequestDTO> getAllAbsenceRequests(String worker){
        List<AbsenceRequestDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(worker);
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        for(AbsenceRequest a: absenceRequestRepository.findAll()){
            if(a.getWorker().getPostOffice().getId() == po.getId()){
                AbsenceRequestDTO adto = new AbsenceRequestDTO(a);
                ret.add(adto);
            }
        }
        return ret;
    }

    public List<AbsenceRequestDTO> getByWorker(String worker){
        List<AbsenceRequestDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(worker);
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        for(AbsenceRequest a: absenceRequestRepository.findAll()){
            if(a.getWorker().getPostOffice().getId() == po.getId() && w.getId().equals(a.getWorker().getId())){
                AbsenceRequestDTO adto = new AbsenceRequestDTO(a);
                ret.add(adto);
            }
        }
        return ret;
    }


    public Boolean processAbsenceRequest(ProcessAbsenceRequestDTO dto){
        AbsenceRequest ar  = absenceRequestRepository.findById(dto.getId()).orElseGet(null);
        if(ar == null){
            return false;
        }

        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return false;
        }
//        Manager m = managerRepository.findById(w.getId()).orElseGet(null);
//        if(m == null){
//            return false;
//        }

        ar.setReviewed(true);
        ar.setApproved(dto.getApproved());
        //ar.setManager(m);

        absenceRequestRepository.save(ar);

        return true;
    }

    public AbsenceRequest addAbsenceRequest(AbsenceRequestDTO dto){
        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }

        AbsenceRequest a = new AbsenceRequest();
        a.setApproved(false);
        a.setReviewed(false);
        a.setContent(dto.getContent());
        a.setDate(LocalDate.now());
        a.setWorker(w);

        return absenceRequestRepository.save(a);
    }
}
