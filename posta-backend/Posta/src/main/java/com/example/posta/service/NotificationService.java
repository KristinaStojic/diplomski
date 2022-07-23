package com.example.posta.service;

import com.example.posta.dto.NotificationDTO;
import com.example.posta.model.Manager;
import com.example.posta.model.Notification;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
import com.example.posta.repository.ManagerRepository;
import com.example.posta.repository.NotificationRepository;
import com.example.posta.repository.PostOfficeRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PostOfficeRepository postOfficeRepository;

    @Autowired
    WorkerRepository workerRepository;

    public List<NotificationDTO> getAllNotification(){
        List<NotificationDTO> ret = new ArrayList<>();

        for(Notification n: notificationRepository.findAll()){
            NotificationDTO ndto = new NotificationDTO(n);
            ret.add(ndto);
        }

        return ret;
    }


    public Notification addNotification(NotificationDTO dto) throws MessagingException {
        Manager m = managerRepository.findByEmail(dto.getManager());
        Notification n = new Notification();
        n.setContent(dto.getContent());
        n.setDate(LocalDate.now());
        n.setManager(m);

        PostOffice po = postOfficeRepository.findById(m.getPostOffice().getId()).orElseGet(null);

        for(Worker w: workerRepository.findAll()){
            if(w.getPostOffice().getId() == po.getId()){
                emailService.sendMailForNewNotification(w.getEmail(),dto.getContent());
            }
        }

        return notificationRepository.save(n);
    }
}
