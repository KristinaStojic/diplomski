package com.example.posta.service;

import com.example.posta.dto.NotificationDTO;
import com.example.posta.model.Notification;
import com.example.posta.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public List<NotificationDTO> getAllNotification(){
        List<NotificationDTO> ret = new ArrayList<>();

        for(Notification n: notificationRepository.findAll()){
            NotificationDTO ndto = new NotificationDTO(n);
            ret.add(ndto);
        }

        return ret;
    }
}
