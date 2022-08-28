package com.example.posta.controller;

import com.example.posta.dto.PaymentDTO;
import com.example.posta.dto.PayoffDTO;
import com.example.posta.service.PaymentService;
import com.example.posta.service.PayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
