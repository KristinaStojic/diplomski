package com.example.posta.service;

import com.example.posta.dto.AddWorkerDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.Role;
import com.example.posta.repository.ManagerRepository;
import com.example.posta.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Manager addManager(AddWorkerDTO dto){
        Manager m = new Manager();
        m.setEnabled(true);
        m.setDeleted(false);
        m.setName(dto.getName());
        m.setSurname(dto.getSurname());
        m.setEmail(dto.getEmail());
        m.setPhoneNumber(dto.getPhoneNumber());
        m.setPassword(dto.getPhoneNumber());
        Role role = this.roleRepository.findByName("ROLE_MANAGER");
        m.setRole(role);

        return this.managerRepository.save(m);
    }

    public Manager editManager(AddWorkerDTO dto){
        Manager m = this.managerRepository.findById(dto.getId()).orElseGet(null);
        m.setName(dto.getName());
        m.setSurname(dto.getSurname());
        m.setEmail(dto.getEmail());
        m.setPhoneNumber(dto.getPhoneNumber());
        return this.managerRepository.save(m);
    }

    public List<WorkerDTO> getAllManagers(){
        List<WorkerDTO> ret = new ArrayList<>();

        for(Manager m: this.managerRepository.findAll()){
            if(!m.isDeleted()){
                WorkerDTO manager = new WorkerDTO(m);
                ret.add(manager);
            }
        }
        return ret;
    }

    public WorkerDTO getById(Long id){
        Manager m = managerRepository.findById(id).orElseGet(null);

        if(m == null){
            return null;
        }
        WorkerDTO ret = new WorkerDTO(m);
        return ret;
    }

    public Manager deleteManager(Long id){
        Manager m = this.managerRepository.findById(id).orElseGet(null);
        m.setDeleted(true);
        this.managerRepository.save(m);

        return m;
    }

    public List<WorkerDTO> getFreeManagers(){
        List<WorkerDTO> ret = new ArrayList<>();

        for(Manager m: this.managerRepository.findAll()){
            if(!m.isDeleted() && m.getPostOffice() == null){
                WorkerDTO manager = new WorkerDTO(m);
                ret.add(manager);
            }
        }
        return ret;
    }

    public Boolean checkIfHasPostOffice(Long id){
        Manager m = managerRepository.findById(id).orElseGet(null);
        return m.getPostOffice() != null;
    }
}
