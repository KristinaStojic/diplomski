package com.example.posta.service;

import com.example.posta.model.Role;
import com.example.posta.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findById(Long id) {
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }

    public Role findByName(String name) {
        Role role = this.roleRepository.findByName(name);
        return role;
    }

}
