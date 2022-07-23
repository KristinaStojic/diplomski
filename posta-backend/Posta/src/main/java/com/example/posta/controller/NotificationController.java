package com.example.posta.controller;

import com.example.posta.dto.NotificationDTO;
import com.example.posta.dto.WorkerDTO;
import com.example.posta.model.Notification;
import com.example.posta.service.ManagerService;
import com.example.posta.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/notification")
@RestController
@CrossOrigin
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<NotificationDTO>> getAllNotification(@RequestHeader("Authorization") String token){
        List<NotificationDTO> m = notificationService.getAllNotification();
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
