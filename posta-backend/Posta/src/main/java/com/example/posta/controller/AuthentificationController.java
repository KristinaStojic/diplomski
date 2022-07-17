package com.example.posta.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.posta.dto.JwtAuthenticationRequest;
import com.example.posta.dto.UserTokenState;
import com.example.posta.exception.ResourceConflictException;
import com.example.posta.model.User;
import com.example.posta.service.*;
import com.example.posta.security.util.TokenUtils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin()
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthentificationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ResponseEntity<UserTokenState>createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        if(!user.isEnabled() || user.isDeleted()){
            return ResponseEntity.ok(null);
        }
        String jwt = tokenUtils.generateToken(user.getEmail(),user.getRole().getName());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

//    @PostMapping("/signup")
//    public ResponseEntity<User> addUser(@RequestBody UserDTO userRequest,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
//        User existUser = this.userService.findByEmail(userRequest.getEmail());
//        if (existUser != null) {
//            throw new ResourceConflictException(userRequest.getId(), "Email already exists");
//        }
//
//        if (userRequest.getRoleName().equals("ROLE_COTTAGE_OWNER")){
//            User user = this.cottageOwnerService.save(userRequest);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }
//        else if(userRequest.getRoleName().equals("ROLE_CLIENT")){
//            try{
//                String randomCode = RandomString.make(64);
//                userRequest.setVerificationCode(randomCode);
//                User user = this.clientService.save(userRequest, getSiteURL(request));
//
//                return new ResponseEntity<>(user, HttpStatus.CREATED);
//
//            } catch (Exception e){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//        else if(userRequest.getRoleName().equals("ROLE_BOAT_OWNER")){
//            User user = this.boatOwnerService.save(userRequest);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }
//        else if(userRequest.getRoleName().equals("ROLE_FISHING_INSTRUCTOR")){
//            User user = this.fishingInstructorService.save(userRequest);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }
//        else if(userRequest.getRoleName().equals("ROLE_ADMIN")){
//            User user = this.adminService.save(userRequest);
//            return new ResponseEntity<>(user, HttpStatus.CREATED);
//        }
//        else{
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/verify/{code}")
    public String verifyUser(@PathVariable String code) {
        if (userService.verify(code)) {
            return "success";
        } else {
            return "failed";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
