package com.example.posta.service;

import com.example.posta.model.Payment;
import com.example.posta.repository.PaymentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    PaymentRepository paymentRepository;

    public String exportReport() throws FileNotFoundException, JRException{
        List<Payment> payments = paymentRepository.findAll();
        String path = "C:\\diplomski\\posta-backend\\Posta\\src\\main\\resources";
        File file = ResourceUtils.getFile("C:\\diplomski\\posta-backend\\Posta\\src\\main\\resources\\Uplatnica.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(payments);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\report.pdf");

        return path;
    }
}
