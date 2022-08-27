package com.example.posta.dto;

import com.example.posta.model.AbsenceRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsenceRequestDTO {
    private Long id;
    private String worker;
    private String content;
    private String role;
    private String date;
    private Boolean approved;
    private Boolean reviewed;

    public AbsenceRequestDTO(AbsenceRequest a){
        this.id = a.getId();
        this.worker = a.getWorker().getName() + " " + a.getWorker().getSurname();
        this.content = a.getContent();
        if(a.getWorker().getRole().getName().equals("ROLE_COUNTER_WORKER")){
            this.role = "Шалтерски радник";
        }else if(a.getWorker().getRole().getName().toString().equals("ROLE_ACCOUNTING_WORKER")){
            this.role = "Обрачунски радник";
        }
        this.date = a.getDate().toString();
        this.approved = a.getApproved();
        this.reviewed = a.getReviewed();
    }
}
