package com.example.posta.dto;

import com.example.posta.model.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String content;
    private String date;
    private String manager;

    public NotificationDTO(Notification n){
        this.id = n.getId();
        this.content = n.getContent();
        this.date = n.getDate().toString();
    }
}
