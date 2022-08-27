package com.example.posta.controller;

import com.example.posta.dto.AbsenceRequestDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.service.AbsenceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/absence_request")
@RestController
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
}
