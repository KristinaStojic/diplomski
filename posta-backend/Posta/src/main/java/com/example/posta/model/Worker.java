package com.example.posta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Worker extends User{

    @ManyToOne(fetch = FetchType.EAGER) //, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_office_id", nullable = true)
    @JsonBackReference
    private PostOffice postOffice;

    public Worker(User u){
        super(u);
    }

    public Worker(){

    }

    public Worker(Worker w){
        this.postOffice = w.getPostOffice();
    }
}
