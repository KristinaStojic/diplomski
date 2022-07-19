package com.example.posta.service;

import com.example.posta.dto.PostOfficeDTO;
import com.example.posta.model.PostOffice;
import com.example.posta.model.Worker;
import com.example.posta.repository.ManagerRepository;
import com.example.posta.repository.PostOfficeRepository;
import com.example.posta.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostOfficeService {

    @Autowired
    PostOfficeRepository postOfficeRepository;

    @Autowired
    WorkerRepository workerRepository;

    public List<PostOfficeDTO> getAllPostOffices(){
        List<PostOfficeDTO> ret = new ArrayList<>();

        for(PostOffice p: postOfficeRepository.findAll()){
            if(!p.getDeleted()){
                PostOfficeDTO po = new PostOfficeDTO(p);
                ret.add(po);
            }
        }
        return ret;
    }

    public PostOffice deletePostOffice(Long id){
        PostOffice p = postOfficeRepository.findById(id).orElseGet(null);
        p.setDeleted(true);
        for(Worker w: workerRepository.findAll()){
            if(id.equals(w.getPostOffice().getId())){
                w.setPostOffice(null);
            }
        }
        postOfficeRepository.save(p);
        return p;
    }
}
