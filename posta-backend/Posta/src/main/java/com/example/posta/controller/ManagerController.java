package com.example.posta.controller;

import com.example.posta.dto.AddManagerDTO;
import com.example.posta.dto.ManagerDTO;
import com.example.posta.model.Manager;
import com.example.posta.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/manager")
@RestController
@CrossOrigin
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<ManagerDTO>> getAllManagers(@RequestHeader("Authorization") String token){
        List<ManagerDTO> m = managerService.getAllManagers();
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/addManager", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> addManager(@RequestBody AddManagerDTO dto) {
        Manager m = this.managerService.addManager(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/editManager", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> editManager(@RequestBody AddManagerDTO dto) {
        Manager m = this.managerService.editManager(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/deleteManager/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> editManager(@PathVariable Long id) {
        Manager m = this.managerService.deleteManager(id);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
