package com.example.posta.service;

import com.example.posta.dto.*;
import com.example.posta.model.*;
import com.example.posta.repository.*;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ReportService reportService;

    public List<PaymentDTO> getAllPayments(){
        List<PaymentDTO> ret = new ArrayList<>();

        for(Payment p: this.paymentRepository.findAll()){
                PaymentDTO payment = new PaymentDTO(p);
                ret.add(payment);
        }
        return ret;
    }

    public List<PaymentDTO> getAllPaymentsByWorker(String worker){
        List<PaymentDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(worker);

        for(Payment p: this.paymentRepository.findAll()){
            if(p.getWorker().getEmail().equals(w.getEmail())){
                PaymentDTO payment = new PaymentDTO(p);
                ret.add(payment);
            }
        }
        return ret;
    }

    public Payment addPayment(AddPaymentDTO dto) throws JRException, FileNotFoundException {
        Payment p = new Payment();

        Client client = new Client();
        client.setName(dto.getClient().getName());
        client.setSurname(dto.getClient().getSurname());
        client.setDeleted(false);
        client.setEnabled(true);
        Address clientAddress = new Address(dto.getClientAddress());
        City found = cityRepository.findByPostalCode(dto.getClientAddress().getPostalCode());
        if(found != null){
            clientAddress.setCity(found);
        }else{
            City c = new City(dto.getClientAddress().getCity());
            Country cnt = new Country();
            cnt.setCountryName(dto.getClientAddress().getCountry());
            countryRepository.save(cnt);
            c.setCountry(cnt);
            c.setPostalCode(dto.getClientAddress().getPostalCode());
            cityRepository.save(c);
            clientAddress.setCity(c);

        }

        addressRepository.save(clientAddress);
        client.setAddress(clientAddress);
        clientRepository.save(client);

        Client receiver = new Client();
        receiver.setName(dto.getReceiver().getName());
        receiver.setSurname(dto.getReceiver().getSurname());
        receiver.setDeleted(false);
        receiver.setEnabled(true);
        Address receiverAddress = new Address(dto.getReceiverAddress());
        System.out.println("adresa" + dto.getReceiverAddress().getPostalCode());
        City found2 = cityRepository.findByPostalCode(dto.getReceiverAddress().getPostalCode());
        if(found2 != null){
            receiverAddress.setCity(found2);
        }else{
            City rc = new City(dto.getReceiverAddress().getCity());
            rc.setPostalCode(dto.getReceiverAddress().getPostalCode());
            Country cntr = new Country();
            cntr.setCountryName(dto.getReceiverAddress().getCountry());
            countryRepository.save(cntr);
            rc.setCountry(cntr);
            cityRepository.save(rc);
            receiverAddress.setCity(rc);

        }


        addressRepository.save(receiverAddress);
        receiver.setAddress(receiverAddress);
        clientRepository.save(receiver);

        p.setClient(client);
        p.setReceiver(receiver);
        p.setPaymentCode(dto.getPaymentCode());
        p.setModel(dto.getModel());
        p.setAmount(dto.getAmount());
        p.setPurpose(dto.getPurpose());
        p.setReceiverAccount(dto.getReceiverAccount());
        p.setCurrency(dto.getCurrency());
        p.setDate(LocalDateTime.now());
        p.setReceivingPlace(dto.getReceivingPlace());
        p.setReferenceNumber(dto.getReferenceNumber());

        Worker w = workerRepository.findByEmail(dto.getCounterWorker());
        Worker cw = workerRepository.getById(w.getId());

        p.setWorker(cw);
        this.reportService.exportReport(p);
        return paymentRepository.save(p);
    }


    public Map<Integer, Integer> getNumberofPaymentsYearly(String worker) {
        Worker w = workerRepository.findByEmail(worker);

        Map<Integer, Integer> map = new HashMap<>();
        for (Payment r : paymentRepository.findAll()) {
                if (!map.containsKey(r.getDate().getYear())) {
                    Integer n = countPaymentPerYear(r.getDate().getYear(), w.getPostOffice().getId());
                    map.put(r.getDate().getYear(), n);
                }
        }
        return map;
    }

    private Integer countPaymentPerYear(Integer year, Long id) {
        Integer n = 0;

        for (Payment r : paymentRepository.findAll()) {
                if (r.getDate().getYear() == year && r.getWorker().getPostOffice().getId() == id) {
                    n++;
                }
        }

        return n;
    }


    public Map<String, Integer> getNumberofPaymentsMonthly(MonthReportDTO dto) {
        Map<String, Integer> map = new HashMap<>();
        Worker w = workerRepository.findByEmail(dto.getWorker());

        for (Payment r : paymentRepository.findAll()) {
                if (!map.containsKey(r.getDate().getMonth().toString())) {
                    Integer n = countPaymentPerMonth(r.getDate().getMonth().toString(), Integer.parseInt(dto.getYear()), w.getPostOffice().getId());
                    map.put(r.getDate().getMonth().toString(), n);
                }

        }

        return map;
    }


    private Integer countPaymentPerMonth(String month, Integer year, Long id) {
        Integer n = 0;

        for (Payment r : paymentRepository.findAll()) {
            if (r.getDate().getMonth().toString().equals(month) && r.getDate().getYear() == year && r.getWorker().getPostOffice().getId() == id) {
                n++;
            }
        }
        return n;
    }


    public Map<String, Integer> getNumberofPaymentsWeekly(WeekReportDTO dto) {
        Map<String, Integer> ret = new HashMap<>();
        LocalDate start = findDate(dto.getStartDate()).toLocalDate();
        LocalDate end = findDate(dto.getEndDate()).toLocalDate();
        Worker w = workerRepository.findByEmail(dto.getWorker());

        while (start.isBefore(end) || start.isEqual(end)) {
            for (Payment r : paymentRepository.findAll()) {
                  Integer n = countPaymentWeekly(start, w.getPostOffice().getId());
                  ret.put(start.toString().substring(0, 10), n);
                }
            start = start.plusDays(1);
        }
        
        return ret;
    }


    public Map<String, Double> getAmountofPaymentsWeekly(WeekReportDTO dto) {
        Map<String, Double> ret = new HashMap<>();
        LocalDate start = findDate(dto.getStartDate()).toLocalDate();
        LocalDate end = findDate(dto.getEndDate()).toLocalDate();
        Worker w = workerRepository.findByEmail(dto.getWorker());

        while (start.isBefore(end) || start.isEqual(end)) {
            for (Payment r : paymentRepository.findAll()) {
                Double n = countAmountWeekly(start, w.getPostOffice().getId());
                ret.put(start.toString().substring(0, 10), n);
            }
            start = start.plusDays(1);
        }

        return ret;
    }

    private LocalDateTime findDate(String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(start, formatter);
    }

    private Integer countPaymentWeekly(LocalDate date, Long id) {
        Integer n = 0;
        for (Payment r : paymentRepository.findAll()) {
                if (r.getDate().toLocalDate().isEqual(date) && r.getWorker().getPostOffice().getId() == id) {
                    n++;
                }
        }
        return n;
    }

    private Double countAmountWeekly(LocalDate date, Long id) {
        Double n = 0.0;
        for (Payment r : paymentRepository.findAll()) {
            if (r.getDate().toLocalDate().isEqual(date) && r.getWorker().getPostOffice().getId() == id) {
                n += r.getAmount();
            }
        }
        return n;
    }

}
