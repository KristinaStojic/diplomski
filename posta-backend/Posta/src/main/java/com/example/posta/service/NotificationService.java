package com.example.posta.service;

import com.example.posta.dto.NotificationDTO;
import com.example.posta.model.Notification;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
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

//    @Autowired
//    ManagerRepository managerRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PostOfficeRepository postOfficeRepository;

    @Autowired
    WorkerRepository workerRepository;

    public List<NotificationDTO> getAllNotification(String workerEmail){
        List<NotificationDTO> ret = new ArrayList<>();
        Worker w = workerRepository.findByEmail(workerEmail);
        PostOffice p = postOfficeRepository.findById(w.getPostOffice().getId()).orElseGet(null);

        for(Notification n: notificationRepository.findAll()){
            if(n.getWorker().getPostOffice() != null && p.getId() == n.getWorker().getPostOffice().getId()){
                NotificationDTO ndto = new NotificationDTO(n);
                ret.add(ndto);
            }
        }

        return ret;
    }


    public Notification addNotification(NotificationDTO dto) throws MessagingException {
        Worker m = workerRepository.findByEmail(dto.getManager());
        Notification n = new Notification();
        n.setContent(dto.getContent());
        n.setDate(LocalDate.now());
        n.setWorker(m);

        PostOffice po = postOfficeRepository.findById(m.getPostOffice().getId()).orElseGet(null);

        for(Worker w: workerRepository.findAll()){
            if(w.getPostOffice() != null){
                if(w.getPostOffice().getId() == po.getId()){
                    emailService.sendMailForNewNotification(w.getEmail(),dto.getContent());
                }
            }

        }

        return notificationRepository.save(n);
    }
}
