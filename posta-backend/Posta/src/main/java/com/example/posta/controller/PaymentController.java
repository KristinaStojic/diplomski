package com.example.posta.controller;

import com.example.posta.dto.PaymentDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/payment")
@RestController
@CrossOrigin
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PaymentDTO>> getAllPayments(@RequestHeader("Authorization") String token){
        List<PaymentDTO> p = paymentService.getAllPayments();
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/getAll/{worker}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PaymentDTO>> getAllPaymentsByWorker(@PathVariable String worker){
        List<PaymentDTO> p = paymentService.getAllPaymentsByWorker(worker);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
