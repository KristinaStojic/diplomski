package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@Entity
@NoArgsConstructor
public class AccountingWorker extends Worker{
    public AccountingWorker(Worker u) {
        super(u);
    }
}
