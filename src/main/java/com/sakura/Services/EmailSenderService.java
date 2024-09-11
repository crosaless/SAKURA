/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
    public boolean sendEmail(String username,String usermail,String url) {
        try{
	String smtpHostServer = "smtp.gmail.com";
        String emailID = "sakuraeccomerce@gmail.com";
        String password = "vnmh htcd thbe jzjz";
        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(emailID, password);
         }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailID));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usermail));
        message.setSubject("Sakura: Restablecimiento de contraseña del sistema");
        message.setText("Hola "+username+" por favor clickea en el siguiente enlace para restablecer tu contraseña: \n"+url);
        Transport.send(message);
        return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
