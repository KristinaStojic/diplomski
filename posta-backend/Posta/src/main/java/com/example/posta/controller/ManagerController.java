package com.example.posta.controller;

import com.example.posta.dto.AddManagerDTO;
import com.example.posta.model.Manager;
import com.example.posta.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "api/manager")
@RestController
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @RequestMapping(value="/addManager", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> addManager(@RequestBody AddManagerDTO dto) {
        Manager m = this.managerService.addManager(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
