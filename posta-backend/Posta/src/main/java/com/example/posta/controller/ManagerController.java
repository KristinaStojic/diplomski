package com.example.posta.controller;

import com.example.posta.dto.AddWorkerDTO;
import com.example.posta.dto.WorkerDTO;
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
    public @ResponseBody ResponseEntity<List<WorkerDTO>> getAllManagers(@RequestHeader("Authorization") String token){
        List<WorkerDTO> m = managerService.getAllManagers();
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getById/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<WorkerDTO> getById(@PathVariable Long id){
        WorkerDTO m = managerService.getById(id);
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/addManager", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> addManager(@RequestBody AddWorkerDTO dto) {
        Manager m = this.managerService.addManager(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/editManager", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> editManager(@RequestBody AddWorkerDTO dto) {
        Manager m = this.managerService.editManager(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/deleteManager/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Manager> deleteManager(@PathVariable Long id) {
        Manager m = this.managerService.deleteManager(id);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getFreeManagers", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity<List<WorkerDTO>> getFreeManagers(@RequestHeader("Authorization") String token){
        List<WorkerDTO> m = managerService.getFreeManagers();
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


//    @RequestMapping(value="/checkIfHasPostOffice/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
//    public @ResponseBody ResponseEntity<Boolean> checkIfHasPostOffice(@PathVariable Long id){
//        if(managerService.checkIfHasPostOffice(id)){
//            return new ResponseEntity<>(true, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

}
