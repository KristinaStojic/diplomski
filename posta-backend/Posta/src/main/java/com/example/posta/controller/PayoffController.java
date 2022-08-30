package com.example.posta.controller;

import com.example.posta.dto.*;
import com.example.posta.dto.EditPayoffStatusDTO;
import com.example.posta.model.Payoff;
import com.example.posta.service.PayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/payoff")
@RestController
@CrossOrigin
public class PayoffController {

    @Autowired
    PayoffService payoffService;

    @RequestMapping(value="/getAll/{worker}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PayoffDTO>> getAllPayoffs(@PathVariable String worker){
        List<PayoffDTO> p = payoffService.getAllPayoffs(worker);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/changePaidOffStatus", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER') || hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Boolean> changePaidOffStatus(@RequestBody EditPayoffStatusDTO dto){
        Boolean p = payoffService.changePaidOffStatus(dto);
        if(p){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value="/search", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER') || hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<List<PayoffDTO>> search(@RequestBody SearchPayoffDTO dto){
        List<PayoffDTO> p = payoffService.search(dto);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value="/addPayoff", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Payoff> addPayoff(@RequestBody AddPayoffDTO dto){
        Payoff p = payoffService.addPayoff(dto);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
