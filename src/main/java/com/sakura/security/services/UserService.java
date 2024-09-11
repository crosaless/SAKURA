/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakura.security.services;

import org.springframework.stereotype.Service;

import com.sakura.Entities.User;
import com.sakura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    @Autowired
    public UserRepository user_repository;

    public User findById(Long id) {
        return this.user_repository.findById(id).get();
    }

    public User findByUsername(String username) {
        return this.user_repository.findByUsername(username).get();
    }
    
}
