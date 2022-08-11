package com.example.posta.controller;

import com.example.posta.dto.*;
import com.example.posta.model.Payment;
import com.example.posta.model.Shipment;
import com.example.posta.service.PaymentService;
import com.example.posta.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RequestMapping(value = "api/shipment")
@RestController
@CrossOrigin
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<ShipmentDTO>> getAllShipments(@RequestHeader("Authorization") String token){
        List<ShipmentDTO> p = shipmentService.getAllShipments();
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/addShipment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER')")
    public ResponseEntity<Shipment> addShipment(@RequestBody AddShipmentDTO dto) {
        Shipment m = this.shipmentService.addShipment(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/editShipmentStatus", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_COUNTER_WORKER') || hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Shipment> editShipmentStatus(@RequestBody EditShipmentDTO dto) throws MessagingException {
        Shipment m = this.shipmentService.editShipmentStatus(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/searchByCode/{code}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<ShipmentDTO>> searchByCode(@PathVariable String code){
        List<ShipmentDTO> p = shipmentService.searchByCode(code);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
