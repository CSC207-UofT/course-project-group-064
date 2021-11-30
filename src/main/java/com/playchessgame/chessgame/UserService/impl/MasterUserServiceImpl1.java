package com.playchessgame.chessgame.UserService.impl;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MasterUserServiceImpl1 implements MasterUserService {

    private final String PASSWORD_RESET_SUCCESS = "Your Password Has Been Reset Successfully!";
    private final String PASSWORD_RESET_FAIL = "Your Password Fails to be Reset... Please Try Again!";

    private JavaMailSender javaMailSender;

    private String masterUserEmail = "maryzhou0218@gmail.com";

    @Autowired
    private Database database;

    @Override
    @Transactional
    public String resetPassword(Map message){

//        try {this.database.updateUserPassword(user);
//
//
//            return PASSWORD_RESET_SUCCESS;
//        }catch (UsernameDoesNotExist e){
//            return e.getMessage();
//        }catch (Exception e){
//            return PASSWORD_RESET_FAIL;
//        }
        receiveEmail("To Update Password", message);


        return "updated!";

    };

    private void receiveEmail(String subject, Map message) {

        var mailMessage = new SimpleMailMessage();

        String sentFrom = (String) message.get("email");

        mailMessage.setTo(masterUserEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message.toString());

        mailMessage.setFrom(sentFrom);

        //TODO: to be listened and reflected on masterUserPage.html
        javaMailSender.send(mailMessage);
    }
}
