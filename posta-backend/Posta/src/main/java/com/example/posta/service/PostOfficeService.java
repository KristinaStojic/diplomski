package com.example.posta.service;

import com.example.posta.dto.AddPostOfficeDTO;
import com.example.posta.dto.PostOfficeDTO;
import com.example.posta.model.*;
import com.example.posta.repository.*;
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

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AddressRepository addressRepository;

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
            if(w.getPostOffice() != null && id.equals(w.getPostOffice().getId())){
                w.setPostOffice(null);
                //p.setManager(null);
                workerRepository.save(w);
            }
        }
        postOfficeRepository.save(p);
        return p;
    }


    public PostOffice addPostOffice(AddPostOfficeDTO dto){
        PostOffice p = new PostOffice();
        p.setDeleted(false);
        p.setPhoneNumber(dto.getPhoneNumber());
        p.setEmployeeNumber(1);

        Manager m = this.managerRepository.findById(dto.getManagerID()).orElseGet(null);
//        p.setManager(m);
        Address a = new Address();
        City found = cityRepository.findByPostalCode(dto.getPostalCode());
        if(found != null){
            a.setCity(found);
        }
        else{
            Country c = new Country();
            c.setCountryName(dto.getCountry());
            this.countryRepository.save(c);

            City city = new City();
            city.setCountry(c);
            city.setCityName(dto.getCity());
            this.cityRepository.save(city);

            a.setCity(city);
        }

        a.setStreet(dto.getStreet());
        a.setStreetNumber(dto.getStreetNumber());
        a.setLongitude(dto.getLongitude());
        a.setLatitude(dto.getLatitude());
        addressRepository.save(a);
        p.setAddress(a);
        this.postOfficeRepository.save(p);

        m.setPostOffice(p);
        this.managerRepository.save(m);
        return p;
    }


    public PostOffice editPostOffice(AddPostOfficeDTO dto){
        PostOffice p = this.postOfficeRepository.findById(dto.getId()).orElseGet(null);
        Manager m = this.managerRepository.findById(dto.getManagerID()).orElseGet(null);
        //p.setManager(m);
        m.setPostOffice(p);
        p.setPhoneNumber(dto.getPhoneNumber());
        p.getAddress().getCity().setCityName(dto.getCity());
        p.getAddress().getCity().getCountry().setCountryName(dto.getCountry());
        p.getAddress().setStreet(dto.getStreet());
        p.getAddress().setStreetNumber(dto.getStreetNumber());
        p.getAddress().setLatitude(dto.getLatitude());
        p.getAddress().setLongitude(dto.getLongitude());
        this.managerRepository.save(m);
        return p;
    }
}
