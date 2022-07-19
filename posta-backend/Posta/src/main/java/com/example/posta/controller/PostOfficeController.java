package com.example.posta.controller;

import com.example.posta.dto.ManagerDTO;
import com.example.posta.dto.PostOfficeDTO;
import com.example.posta.service.ManagerService;
import com.example.posta.service.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/postOffice")
@RestController
public class PostOfficeController {

    @Autowired
    PostOfficeService postOfficeService;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<PostOfficeDTO>> getAllPostOffices(@RequestHeader("Authorization") String token){
        List<PostOfficeDTO> p = postOfficeService.getAllPostOffices();
        if(p != null){
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
