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

import javax.mail.MessagingException;
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

    @RequestMapping(value="/addNotification", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public @ResponseBody ResponseEntity<Notification> addNotification(@RequestBody NotificationDTO dto) throws MessagingException {
        Notification n = notificationService.addNotification(dto);
        if(n != null){
            return new ResponseEntity<>(n, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
