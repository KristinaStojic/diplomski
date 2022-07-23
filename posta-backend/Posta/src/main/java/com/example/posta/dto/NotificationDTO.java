package com.example.posta.dto;

import com.example.posta.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {
    private String content;
    private String date;

    public NotificationDTO(Notification n){
        this.content = n.getContent();
        this.date = n.getDate().toString();
    }
}
