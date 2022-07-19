package com.example.posta.service;

import com.example.posta.dto.PostOfficeDTO;
import com.example.posta.model.PostOffice;
import com.example.posta.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostOfficeService {

    @Autowired
    PostOfficeRepository postOfficeRepository;

    public List<PostOfficeDTO> getAllPostOffices(){
        List<PostOfficeDTO> ret = new ArrayList<>();

        for(PostOffice p: postOfficeRepository.findAll()){
            PostOfficeDTO po = new PostOfficeDTO(p);
            ret.add(po);
        }
        return ret;
    }
}
