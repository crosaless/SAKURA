/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import com.sakura.Entities.User;
import com.sakura.repository.UserRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordRecoveryService {
    
    @Autowired
    EmailSenderService emailService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    
    @Transactional
    public void enviarMail(String username) throws Exception{
        User user = this.userRepository.findByUsername(username).get();
        String token = generadorTokenPassword();
        String url = "http://localhost:8080/restablecerContraseña?token="+token;
       
        if(!userRepository.existsByPasswordToken(token)){
            user.setPasswordToken(token);
            user.setFechaExpToken(LocalDateTime.now());
            userRepository.save(user);
        }
        
        if(!this.emailService.sendEmail(user.getUsername(),user.getEmail(),url)){
            throw new Exception("Se produjo un problema al enviar el mail");
        }
        
    
    }
    
    @Transactional
    public void restablecer(String token,String contraseña) throws Exception{
        if(token != null && userRepository.existsByPasswordToken(token)){
            User user = userRepository.findUserByPasswordToken(token);
            if(!LocalDateTime.now().isAfter(user.getFechaExpToken().plusHours(12))){
                user.setPassword(encoder.encode(contraseña));
                user.setPasswordToken(null);
                userRepository.save(user);
            }
            else{
                throw new Exception("El token se encuentra expirado");
            }
        }else{
            throw new Exception ("El token especificado no existe");
        }
    }
    
    public String generadorTokenPassword(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom RANDOM = new SecureRandom();
        
        StringBuilder sb = new StringBuilder(CHARACTERS.length());
        for (int i = 0; i < CHARACTERS.length(); i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();

    }
}
