/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mpatel.bookwebapp1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ankita
 */
@Service
public class MailService {
    @Autowired
    private MailSender mailSender;
       @Autowired
       private SimpleMailMessage templateMessage;
       
       public void sendMessage(String emailAddress){
           SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
           msg.setTo(emailAddress);
           msg.setText("This is sample");
           
           try{
           mailSender.send(msg);
           }catch (NullPointerException npe){
               throw new MailSendException("Email Send Error");
           }
       }
}
