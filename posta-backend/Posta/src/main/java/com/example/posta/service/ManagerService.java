package com.example.posta.service;

import com.example.posta.dto.AddManagerDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.Role;
import com.example.posta.repository.ManagerRepository;
import com.example.posta.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Role role = this.roleRepository.findByName(dto.getRole());
        m.setRole(role);

        return this.managerRepository.save(m);
    }
}
