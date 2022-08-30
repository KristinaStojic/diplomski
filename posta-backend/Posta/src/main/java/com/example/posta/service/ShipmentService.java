package com.example.posta.service;

import com.example.posta.dto.*;
import com.example.posta.model.*;
import com.example.posta.model.enums.LetterType;
import com.example.posta.model.enums.ShipmentStatus;
import com.example.posta.model.enums.ShipmentType;
import com.example.posta.repository.*;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    CounterWorkerRepository counterWorkerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PostOfficeRepository postOfficeRepository;

    public List<ShipmentDTO> getAllShipments(){
        List<ShipmentDTO> ret = new ArrayList<>();

        for(Shipment p: this.shipmentRepository.findAll()){
            ShipmentDTO s = new ShipmentDTO(p);
            ret.add(s);
        }
        return ret;
    }

    public List<ShipmentDTO> getAllByWorker(String email){
        List<ShipmentDTO> ret = new ArrayList<>();

        for(Shipment p: this.shipmentRepository.findAll()){
            Worker w = workerRepository.findByEmail(email);
            if(w == null){
                return null;
            }

            if(p.getReceivingPostOffice().getId() == w.getPostOffice().getId()){
                ShipmentDTO s = new ShipmentDTO(p);
                ret.add(s);
            }

            if(p.getDeliveringPostOffice() != null && p.getDeliveringPostOffice().getId() != p.getReceivingPostOffice().getId() && p.getDeliveringPostOffice().getId() == w.getPostOffice().getId()){
                ShipmentDTO s = new ShipmentDTO(p);
                ret.add(s);
            }

        }
        return ret;
    }

    public List<ShipmentDTO> searchByCode(SearchShipmentDTO dto){
        List<ShipmentDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(dto.getWorker());
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);

        for(Shipment s: shipmentRepository.findAll()){
            if(s.getCode().contains(dto.getCode()) && (s.getReceivingPostOffice().getId() == po.getId() || (s.getDeliveringPostOffice() != null && s.getDeliveringPostOffice().getId() == po.getId()))){
                ShipmentDTO ss = new ShipmentDTO(s);
                ret.add(ss);
            }
        }
        return ret;
    }

    public Shipment addShipment(AddShipmentDTO dto){
        Shipment s = new Shipment();

        Client client = new Client();
        Role r = roleRepository.findByName("ROLE_CLIENT");
        client.setName(dto.getClient().getName());
        client.setSurname(dto.getClient().getSurname());
        client.setDeleted(false);
        client.setEnabled(true);
        Address clientAddress = new Address(dto.getClientAddress());
        City c = new City(dto.getClientAddress().getCity());
        c.setPostalCode(dto.getClientAddress().getPostalCode());
        Country cnt = new Country();
        cnt.setCountryName(dto.getClientAddress().getCountry());
        countryRepository.save(cnt);
        c.setCountry(cnt);
        cityRepository.save(c);
        clientAddress.setCity(c);
        addressRepository.save(clientAddress);
        client.setAddress(clientAddress);
        client.setRole(r);
        clientRepository.save(client);

        Client receiver = new Client();
        receiver.setName(dto.getReceiver().getName());
        receiver.setSurname(dto.getReceiver().getSurname());
        receiver.setDeleted(false);
        receiver.setEnabled(true);
        Address receiverAddress = new Address(dto.getReceiverAddress());
        City rc = new City(dto.getReceiverAddress().getCity());
        Country cntr = new Country();
        cntr.setCountryName(dto.getReceiverAddress().getCountry());
        countryRepository.save(cntr);
        rc.setCountry(cntr);
        rc.setPostalCode(dto.getReceiverAddress().getPostalCode());
        cityRepository.save(rc);
        receiverAddress.setCity(rc);
        addressRepository.save(receiverAddress);
        receiver.setAddress(receiverAddress);
        receiver.setRole(r);
        clientRepository.save(receiver);

        s.setSender(client);
        s.setReceiver(receiver);
        s.setDate(LocalDateTime.now());
        s.setValue(dto.getValue());
        s.setWeight(dto.getWeight());
        s.setEmailReport(dto.getEmailReport());
        s.setEmail(dto.getEmail());
        s.setPersonalDelivery(dto.getPersonalDelivery());
        s.setReturnReceipt(dto.getReturnReceipt());
        s.setCode(UUID.randomUUID().toString().toUpperCase().substring(0,11));
        Worker w = workerRepository.findByEmail(dto.getCounterWorker());
        CounterWorker cw = counterWorkerRepository.findById(w.getId()).orElseGet(null);

        if (cw == null){
            return null;
        }

        s.setReceivingPostOffice(postOfficeRepository.findById(cw.getPostOffice().getId()).orElseGet(null));
        s.setDeliveringPostOffice(null);

        if(dto.getShipmentType().equals(ShipmentType.LETTER.toString())){
            s.setShipmentType(ShipmentType.LETTER);
        }
        else{
            s.setShipmentType(ShipmentType.PACKAGE);
        }

        if(dto.getLetterType().equals(LetterType.ORDINARY.toString())){
            s.setLetterType(LetterType.ORDINARY);
        }
        else if(dto.getLetterType().equals(LetterType.VALUE.toString())){
            s.setLetterType(LetterType.VALUE);
        }
        else if(dto.getLetterType().equals(LetterType.REGISTERED.toString())){
            s.setLetterType(LetterType.REGISTERED);
        }

        Double price = findTotalPrice(dto);
        s.setTotalPrice(price);
        s.setShipmentStatus(ShipmentStatus.RECEIVED);
        return shipmentRepository.save(s);
    }

    private Double findTotalPrice(AddShipmentDTO dto){
        double total = 0.0;

        if(dto.getShipmentType().equals(ShipmentType.PACKAGE.toString())){
            total += 100;

            if(1000 < dto.getWeight() && dto.getWeight() < 5000){
                total += 500;
            }
            else if(5000 <= dto.getWeight() && dto.getWeight() < 15000){
                total += 700;
            }
            else if(dto.getWeight() >= 15000){
                total += 1000;
            }
        }
        else{
            total += 50;

            if(dto.getWeight() < 300){
                total += 100;
            }
            else if(300 <= dto.getWeight() && dto.getWeight() < 500){
                total += 200;
            }
            else if(dto.getWeight() >= 500 && dto.getWeight() < 1000){
                total += 300;
            }
        }

        if(dto.getEmailReport()){
            total += 100;
        }
        if(dto.getReturnReceipt()){
            total += 50;
        }


        return total;
    }


    public Shipment editShipmentStatus(EditShipmentDTO dto) throws MessagingException {

        Shipment s = this.shipmentRepository.findById(dto.getId()).orElseGet(null);
        if(s == null){
            return null;
        }

        switch (dto.getNewStatus()) {
            case "Чека на испоруку" -> s.setShipmentStatus(ShipmentStatus.RECEIVED);
            case "Достављено" -> {
                s.setShipmentStatus(ShipmentStatus.DELIVERED);
                Worker w = workerRepository.findByEmail(dto.getCounterWorkerEmail());

                if(w.getPostOffice() != null){
                    s.setDeliveringPostOffice(postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null));
                }

                if(dto.getEmailReport()){
                    emailService.sendMailForDeliveredShipment(dto.getEmail(),dto.getCode());
                }
            }
            //case "Послато на испоруку" -> s.setShipmentStatus(ShipmentStatus.SENDING);
            case "Враћено" -> s.setShipmentStatus(ShipmentStatus.RETURNED);
        }

        return this.shipmentRepository.save(s);
    }

    public Shipment recordShipmentInPostOffice(RecordShipmentDTO dto){
        Shipment s = shipmentRepository.findByCode(dto.getCode());

        if(s == null){
            return null;
        }

        Worker w = workerRepository.findByEmail(dto.getAccountingWorkerEmail());

        if(w == null){
            return null;
        }

        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);

        if(po == null){
            return null;
        }

        s.setDeliveringPostOffice(po);
        //s.setShipmentStatus(ShipmentStatus.SENDING);

        return shipmentRepository.save(s);
    }


    public Map<String,Integer> getNumberofShipmentsYearly(ShipmentReportDTO dto){
        Map<String, Integer> map = new HashMap<>();
        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        for (Shipment r : shipmentRepository.findAll()) {
            if (!map.containsKey(r.getShipmentStatus().toString())) {
                Integer n = countShipmentsYearly(r.getShipmentStatus().toString(), Integer.parseInt(dto.getYear()), po.getId());
                if(r.getShipmentStatus().toString().equals("RECEIVED")){
                    map.put("Чека на испоруку", n);
                }
                else if(r.getShipmentStatus().toString().equals("DELIVERED")){
                    map.put("Достављено", n);
                }
                else if(r.getShipmentStatus().toString().equals("SENDING")){
                    map.put("Послато на испоруку", n);
                }
                else if(r.getShipmentStatus().toString().equals("RETURNED")){
                    map.put("Враћено", n);
                }
            }

        }

        return map;
    }

    private Integer countShipmentsYearly(String status, Integer year, Long id) {
        Integer n = 0;

        for (Shipment r : shipmentRepository.findAll()) {
            if(r.getDeliveringPostOffice() != null){
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().getYear() == year && r.getDeliveringPostOffice().getId() == id) {
                    n++;
                }
            }
            else{
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().getYear() == year && r.getReceivingPostOffice().getId() == id) {
                    n++;
                }
            }

        }
        return n;
    }


    public Map<String,Integer> getNumberofShipmentsMonthly(ShipmentReportDTO dto){
        Map<String, Integer> map = new HashMap<>();
        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        for (Shipment r : shipmentRepository.findAll()) {
            if (!map.containsKey(r.getShipmentStatus().toString())) {
                Integer n = countShipmentsMonthly(r.getShipmentStatus().toString(), Integer.parseInt(dto.getYear()), dto.getMonth(), po.getId());
                if(r.getShipmentStatus().toString().equals("RECEIVED")){
                    map.put("Чека на испоруку", n);
                }
                else if(r.getShipmentStatus().toString().equals("DELIVERED")){
                    map.put("Достављено", n);
                }
                else if(r.getShipmentStatus().toString().equals("SENDING")){
                    map.put("Послато на испоруку", n);
                }
                else if(r.getShipmentStatus().toString().equals("RETURNED")){
                    map.put("Враћено", n);
                }
            }

        }

        return map;
    }


    private Integer countShipmentsMonthly(String status, Integer year, String month, Long id) {
        Integer n = 0;

        for (Shipment r : shipmentRepository.findAll()) {
            if(r.getDeliveringPostOffice() != null){
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().getYear() == year && r.getDate().getMonth().toString().equals(month) && r.getDeliveringPostOffice().getId() == id) {
                    n++;
                }
            }
            else{
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().getMonth().toString().equals(month) && r.getDate().getYear() == year && r.getReceivingPostOffice().getId() == id) {
                    n++;
                }
            }

        }
        return n;
    }

    private LocalDateTime findDate(String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(start, formatter);
    }

    public Map<String,Integer> getNumberofShipmentsSelectedPeriod(SelectedPeriodShipmentReportDTO dto){
        Map<String, Integer> map = new HashMap<>();
        LocalDate start = findDate(dto.getStartDate()).toLocalDate();
        LocalDate end = findDate(dto.getEndDate()).toLocalDate();

        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }
        Integer received = 0;
        Integer returned = 0;
        Integer sending = 0;
        Integer delivered = 0;

        while (start.isBefore(end) || start.isEqual(end)) {
            for (Shipment r : shipmentRepository.findAll()) {
                if(r.getShipmentStatus().toString().equals("RECEIVED")){
                    Integer n = countShipmentsSelectedPeriod(r, r.getShipmentStatus().toString(), start, po.getId());
                    received += n;
                }
                else if(r.getShipmentStatus().toString().equals("DELIVERED")){
                    Integer n = countShipmentsSelectedPeriod(r, r.getShipmentStatus().toString(), start, po.getId());
                    delivered += n;
                }
                else if(r.getShipmentStatus().toString().equals("SENDING")){
                    Integer n = countShipmentsSelectedPeriod(r, r.getShipmentStatus().toString(), start, po.getId());
                    sending += n;
                }
                else if(r.getShipmentStatus().toString().equals("RETURNED")){
                    Integer n = countShipmentsSelectedPeriod(r, r.getShipmentStatus().toString(), start, po.getId());
                    returned += n;
                }
            }
            start = start.plusDays(1);
        }

        map.put("Чека на испоруку", received);
        map.put("Достављено", delivered);
        map.put("Послато на испоруку", sending);
        map.put("Враћено", returned);


        return map;
    }


    private Integer countShipmentsSelectedPeriod(Shipment r, String status, LocalDate date, Long id) {
        Integer n = 0;

        //for (Shipment r : shipmentRepository.findAll()) {
            if(r.getDeliveringPostOffice() != null){
                if (r.getShipmentStatus().toString().equals(status)  && r.getDate().toLocalDate().isEqual(date)  && r.getDeliveringPostOffice().getId() == id) {
                    n++;
                }
            }
            else{
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().toLocalDate().isEqual(date)  && r.getReceivingPostOffice().getId() == id) {
                    n++;
                }
            }

       // }
        return n;
    }



    public Map<String,Integer> getNumberofShipmentsByTypeSelectedPeriod(SelectedPeriodShipmentReportDTO dto){
        Map<String, Integer> map = new HashMap<>();
        LocalDate start = findDate(dto.getStartDate()).toLocalDate();
        LocalDate end = findDate(dto.getEndDate()).toLocalDate();

        Worker w = workerRepository.findByEmail(dto.getWorker());
        if(w == null){
            return null;
        }
        PostOffice po = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);
        if(po == null){
            return null;
        }

        while (start.isBefore(end) || start.isEqual(end)) {
            for (Shipment r : shipmentRepository.findAll()) {
                Integer n = countShipmentsByTypeSelectedPeriod(dto.getStatus(), start, po.getId());
                map.put(start.toString().substring(0, 10), n);
            }
            start = start.plusDays(1);
        }

        return map;
    }


    private Integer countShipmentsByTypeSelectedPeriod(String status, LocalDate date, Long id) {
        Integer n = 0;

        for (Shipment r : shipmentRepository.findAll()) {
            if(r.getDeliveringPostOffice() != null){
                if (r.getShipmentStatus().toString().equals(status)  && r.getDate().toLocalDate().isEqual(date)  && r.getDeliveringPostOffice().getId() == id) {
                    n++;
                }
            }
            else{
                if (r.getShipmentStatus().toString().equals(status) && r.getDate().toLocalDate().isEqual(date)  && r.getReceivingPostOffice().getId() == id) {
                    n++;
                }
            }

        }
        return n;
    }

}
