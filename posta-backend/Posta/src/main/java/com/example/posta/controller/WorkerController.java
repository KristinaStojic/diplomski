package com.example.posta.controller;

import com.example.posta.dto.AddWorkerDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.Worker;
import com.example.posta.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/worker")
@RestController
@CrossOrigin
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @RequestMapping(value="/addWorker", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Boolean> addWorker(@RequestBody AddWorkerDTO dto) {
        Boolean m = this.workerService.addWorker(dto);
        if(m){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getAllByManagersPostOffice/{manager}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<WorkerDTO>> getAllByManagersPostOffice(@PathVariable String manager){
        List<WorkerDTO> m = workerService.getAllByManagersPostOffice(manager);
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getById/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<WorkerDTO> getById(@PathVariable Long id){
        WorkerDTO m = workerService.getById(id);
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/deleteWorker/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Worker> deleteWorker(@PathVariable Long id) {
        Worker m = this.workerService.deleteWorker(id);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
