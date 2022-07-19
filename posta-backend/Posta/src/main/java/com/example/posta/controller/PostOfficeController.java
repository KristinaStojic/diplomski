package com.example.posta.controller;

import com.example.posta.dto.ManagerDTO;
import com.example.posta.dto.PostOfficeDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.PostOffice;
import com.example.posta.service.ManagerService;
import com.example.posta.service.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/postOffice")
@RestController
@CrossOrigin
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

    @RequestMapping(value="/deletePostOffice/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PostOffice> deletePostOffice(@PathVariable Long id) {
        PostOffice m = this.postOfficeService.deletePostOffice(id);
        if(m!=null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
