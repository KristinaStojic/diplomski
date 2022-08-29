package com.example.posta.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.example.posta.dto.ChangePasswordDTO;
import com.example.posta.model.*;
import com.example.posta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findOne(Long id) {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }


    public ChangePasswordDTO updatePasswod(ChangePasswordDTO user) {
        User updated=userRepository.findByEmail(user.getEmail());
        if(!user.getPassword().equals(user.getPasswordRepeated()) || !user.getOldPassword().equals(updated.getPassword())){
            return null;
        }
        updated.setPassword(user.getPassword());
        userRepository.save(updated);
        return user;
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
