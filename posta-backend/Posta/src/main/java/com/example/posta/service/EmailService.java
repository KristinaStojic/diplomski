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
        String text = "<br>Novo obavještenje primljeno:<br>" + content;
        Worker worker = workerRepository.findByEmail(userEmail);

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setTo(userEmail);
        helper.setFrom("posta.diplomski@gmail.com");
        helper.setSubject("Novo obavještenje");
        helper.setText(text, true);
        javaMailSender.send(mail);

    }

    public void sendMailForDeliveredShipment(String userEmail, String content) throws MessagingException {
        String text = "<br>Poštоvani,<br><br>Vaša pošiljka " + content + " je uspješno isporučena!<br><br>Pozdrav";

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setTo(userEmail);
        helper.setFrom("posta.diplomski@gmail.com");
        helper.setSubject("Isporuka pošiljke");
        helper.setText(text, true);
        javaMailSender.send(mail);
    }
}
