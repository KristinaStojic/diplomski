package com.example.posta.service;

import com.example.posta.dto.AddWorkerDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.*;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private PostmanRepository postmanRepository;

    @Autowired
    private CounterWorkerRepository counterWorkerRepository;

    @Autowired
    private AccountingWorkerRepository accountingWorkerRepository;

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Boolean addWorker(AddWorkerDTO dto){
        switch (dto.getRole()) {
            case "ROLE_POSTMAN" -> {
                createPostman(dto);
                return true;
            }
            case "ROLE_ACCOUNTING_WORKER" -> {
                createAccountingWorker(dto);
                return true;
            }
            case "ROLE_COUNTER_WORKER" -> {
                createCounterWorker(dto);
                return true;
            }
        }

        return false;
    }

    private void createPostman(AddWorkerDTO dto){
        Manager m = managerRepository.findByEmail(dto.getManagerEmail());
        PostOffice po = postOfficeRepository.findById(m.getPostOffice().getId()).orElseGet(null);

        Postman p = new Postman();
        Role role = this.roleRepository.findByName(dto.getRole());
        p.setEnabled(true);
        p.setDeleted(false);
        p.setName(dto.getName());
        p.setSurname(dto.getSurname());
        p.setEmail(dto.getEmail());
        p.setPhoneNumber(dto.getPhoneNumber());
        p.setPassword(dto.getPhoneNumber());
        p.setRole(role);
        p.setPostOffice(po);
        postmanRepository.save(p);
    }

    private void createCounterWorker(AddWorkerDTO dto){
        Manager m = managerRepository.findByEmail(dto.getManagerEmail());
        PostOffice po = postOfficeRepository.findById(m.getPostOffice().getId()).orElseGet(null);

        CounterWorker p = new CounterWorker();
        Role role = this.roleRepository.findByName(dto.getRole());
        p.setEnabled(true);
        p.setDeleted(false);
        p.setName(dto.getName());
        p.setSurname(dto.getSurname());
        p.setEmail(dto.getEmail());
        p.setPhoneNumber(dto.getPhoneNumber());
        p.setPassword(dto.getPhoneNumber());
        p.setRole(role);
        p.setPostOffice(po);
        counterWorkerRepository.save(p);
    }

    private void createAccountingWorker(AddWorkerDTO dto){
        Manager m = managerRepository.findByEmail(dto.getManagerEmail());
        PostOffice po = postOfficeRepository.findById(m.getPostOffice().getId()).orElseGet(null);

        AccountingWorker p = new AccountingWorker();
        Role role = this.roleRepository.findByName(dto.getRole());
        p.setEnabled(true);
        p.setDeleted(false);
        p.setName(dto.getName());
        p.setSurname(dto.getSurname());
        p.setEmail(dto.getEmail());
        p.setPhoneNumber(dto.getPhoneNumber());
        p.setPassword(dto.getPhoneNumber());
        p.setRole(role);
        p.setPostOffice(po);
        accountingWorkerRepository.save(p);
    }

    public List<WorkerDTO> getAllByManagersPostOffice(String manager){
        List<WorkerDTO> ret = new ArrayList<>();

                for(Worker w: this.workerRepository.findAll()){
                    if(w.getEmail().equals(manager)) {
                        for (Worker worker : this.workerRepository.findAll()) {
                            if (!worker.isDeleted() && !worker.getRole().getName().equals("ROLE_MANAGER") && w.getPostOffice() != null && worker.getPostOffice() != null && (w.getPostOffice().getId() == worker.getPostOffice().getId())) {
                                WorkerDTO ww = new WorkerDTO(worker);
                                ret.add(ww);
                            }
                        }
                    }
            }

        return ret;
    }
}
