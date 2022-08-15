package com.example.posta.controller;

import com.example.posta.dto.*;
import com.example.posta.model.Payment;
import com.example.posta.service.PaymentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value="/addPayment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Payment> addPayment(@RequestBody AddPaymentDTO dto) throws JRException, FileNotFoundException {
        Payment m = this.paymentService.addPayment(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/getNumberofPaymentsYearly", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Map<Integer, Integer>> getNumberofPaymentsYearly() {
        Map<Integer, Integer> n = this.paymentService.getNumberofPaymentsYearly();
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getNumberofPaymentsMonthly/{year}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Map<String, Integer>> getNumberofPaymentsMonthly(@PathVariable String year) {
        Map<String, Integer> n = this.paymentService.getNumberofPaymentsMonthly(year);
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/getNumberofPaymentsWeekly", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Map<String, Integer>> getNumberofPaymentsWeekly(@RequestBody WeekReportDTO dto) {
        Map<String, Integer> n = this.paymentService.getNumberofPaymentsWeekly(dto);
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/getAmountofPaymentsWeekly", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Map<String, Double>> getAmountofPaymentsWeekly(@RequestBody WeekReportDTO dto) {
        Map<String, Double> n = this.paymentService.getAmountofPaymentsWeekly(dto);
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }

}
