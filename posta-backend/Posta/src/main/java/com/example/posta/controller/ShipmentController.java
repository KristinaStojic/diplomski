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
import java.util.Map;

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

    @RequestMapping(value="/getAllByWorker/{email}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<ShipmentDTO>> getAllByWorker(@PathVariable String email){
        List<ShipmentDTO> p = shipmentService.getAllByWorker(email);
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

    @RequestMapping(value="/searchByCode", method = RequestMethod.POST)
    public ResponseEntity<List<ShipmentDTO>> searchByCode(@RequestBody SearchShipmentDTO dto){
        List<ShipmentDTO> p = shipmentService.searchByCode(dto);
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value="/recordShipmentInPostOffice", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Shipment> recordShipmentInPostOffice(@RequestBody RecordShipmentDTO dto) {
        Shipment m = this.shipmentService.recordShipmentInPostOffice(dto);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/getNumberofShipmentsYearly", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Map<String, Integer>> getNumberofShipmentsYearly(@RequestBody ShipmentReportDTO dto) {
        Map<String, Integer> n = this.shipmentService.getNumberofShipmentsYearly(dto);

        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getNumberofShipmentsMonthly", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_ACCOUNTING_WORKER')")
    public ResponseEntity<Map<String, Integer>> getNumberofShipmentsMonthly(@RequestBody ShipmentReportDTO dto) {
        Map<String, Integer> n = this.shipmentService.getNumberofShipmentsMonthly(dto);

        if (n == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(n, HttpStatus.OK);
        }
    }

}
