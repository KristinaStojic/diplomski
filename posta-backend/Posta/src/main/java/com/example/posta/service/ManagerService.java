package com.example.posta.service;

import com.example.posta.dto.AddManagerDTO;
import com.example.posta.dto.ManagerDTO;
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

    public Manager addManager(AddManagerDTO dto){
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

    public List<ManagerDTO> getAllManagers(){
        List<ManagerDTO> ret = new ArrayList<>();

        for(Manager m: this.managerRepository.findAll()){
            ManagerDTO manager = new ManagerDTO(m);
            ret.add(manager);
        }
        return ret;
    }
}
