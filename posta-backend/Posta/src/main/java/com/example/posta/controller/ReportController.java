package com.example.posta.controller;

import com.example.posta.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RequestMapping(value = "api/report")
@RestController
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/generate")
    public String generateReport() throws JRException, FileNotFoundException {
        return reportService.exportReport();
    }
}
