package com.example.posta.controller;

import com.example.posta.dto.AbsenceRequestDTO;
import com.example.posta.dto.ProcessAbsenceRequestDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.AbsenceRequest;
import com.example.posta.service.AbsenceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/absence_request")
@RestController
@CrossOrigin
public class AbsenceRequestController {

    @Autowired
    AbsenceRequestService absenceRequestService;

    @RequestMapping(value="/getAll/{worker}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public @ResponseBody ResponseEntity<List<AbsenceRequestDTO>> getAllAbsenceRequests(@PathVariable String worker){
        List<AbsenceRequestDTO> ar = absenceRequestService.getAllAbsenceRequests(worker);
        if(ar != null){
            return new ResponseEntity<>(ar, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getByWorker/{worker}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<AbsenceRequestDTO>> getByWorker(@PathVariable String worker){
        List<AbsenceRequestDTO> ar = absenceRequestService.getByWorker(worker);
        if(ar != null){
            return new ResponseEntity<>(ar, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/processAbsenceRequest", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Boolean> processAbsenceRequest(@RequestBody ProcessAbsenceRequestDTO dto){
        Boolean processed = absenceRequestService.processAbsenceRequest(dto);
        if(processed){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/addAbsenceRequest", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<AbsenceRequest> addAbsenceRequest(@RequestBody AbsenceRequestDTO dto){
        AbsenceRequest a = absenceRequestService.addAbsenceRequest(dto);
        if(a != null){
            return new ResponseEntity<>(a, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
