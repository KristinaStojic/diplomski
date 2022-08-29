package com.example.posta.controller;

import com.example.posta.dto.ChangePasswordDTO;
import com.example.posta.model.User;
import com.example.posta.security.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.posta.service.UserService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RequestMapping(value = "api/users")
@RestController
@CrossOrigin()
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;



    @RequestMapping(value="role", method = RequestMethod.GET)
    public @ResponseBody String getRole(@RequestHeader("Authorization") String token){
        return tokenUtils.getRoleFromToken(token.split(" ")[1]);
    }


    @RequestMapping(value="changePassword", method = RequestMethod.PUT)
    public ResponseEntity<ChangePasswordDTO> changePassword(@RequestBody ChangePasswordDTO dto) {

        User user = userService.findByEmail(dto.getEmail());
        ChangePasswordDTO u = userService.updatePasswod(dto);
        if(u == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(u,HttpStatus.OK);
    }


}
