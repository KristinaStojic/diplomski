package com.example.posta.service;

import com.example.posta.dto.AddWorkerDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.Role;
import com.example.posta.model.Worker;
import com.example.posta.repository.RoleRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private ManagerRepository managerRepository;

    @Autowired
    WorkerRepository workerRepository;

    public Worker addManager(AddWorkerDTO dto){
        Worker m = new Worker();
        m.setEnabled(true);
        m.setDeleted(false);
        m.setName(dto.getName());
        m.setSurname(dto.getSurname());
        m.setEmail(dto.getEmail());
        m.setPhoneNumber(dto.getPhoneNumber());
        m.setPassword(dto.getPhoneNumber());
        Role role = this.roleRepository.findByName("ROLE_MANAGER");
        m.setRole(role);

        return this.workerRepository.save(m);
    }

    public Worker editManager(AddWorkerDTO dto){
        Worker m = this.workerRepository.findById(dto.getId()).orElseGet(null);
        m.setName(dto.getName());
        m.setSurname(dto.getSurname());
        m.setEmail(dto.getEmail());
        m.setPhoneNumber(dto.getPhoneNumber());
        return this.workerRepository.save(m);
    }

    public List<WorkerDTO> getAllManagers(){
        List<WorkerDTO> ret = new ArrayList<>();

        for(Worker m: this.workerRepository.findAll()){
            if(!m.isDeleted() && m.getRole().getName().equals("ROLE_MANAGER")){
                WorkerDTO manager = new WorkerDTO(m);
                ret.add(manager);
            }
        }
        return ret;
    }

    public WorkerDTO getById(Long id){
        Worker m = workerRepository.findById(id).orElseGet(null);

        if(m == null){
            return null;
        }
        WorkerDTO ret = new WorkerDTO(m);
        return ret;
    }

    public Worker deleteManager(Long id){
        Worker m = this.workerRepository.findById(id).orElseGet(null);
        m.setDeleted(true);
        this.workerRepository.save(m);

        return m;
    }

    public List<WorkerDTO> getFreeManagers(){
        List<WorkerDTO> ret = new ArrayList<>();

        for(Worker m: this.workerRepository.findAll()){
            if(!m.isDeleted() && m.getPostOffice() == null){
                WorkerDTO manager = new WorkerDTO(m);
                ret.add(manager);
            }
        }
        return ret;
    }

    public Boolean checkIfHasPostOffice(Long id){
        Worker m = workerRepository.findById(id).orElseGet(null);
        return m.getPostOffice() != null;
    }
}
