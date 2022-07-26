package com.example.posta.service;

import com.example.posta.model.Worker;
import com.example.posta.repository.WorkerRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private WorkerRepository workerRepository;

    public void sendMailForNewNotification(String userEmail, String content) throws MessagingException {
        String text = "<br>Ново обавјештење примљено:<br>" + content;
        Worker worker = workerRepository.findByEmail(userEmail);

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setTo(userEmail);
        helper.setFrom("wish.and.fish.serbia@gmail.com");
        helper.setSubject("Ново обавјештење");
        helper.setText(text, true);
        javaMailSender.send(mail);

    }
}
