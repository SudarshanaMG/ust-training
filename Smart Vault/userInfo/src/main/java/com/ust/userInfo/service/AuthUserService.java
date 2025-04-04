package com.ust.userInfo.service;

import com.ust.userInfo.model.AuthUser;
import com.ust.userInfo.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository userRepository;

    public AuthUser addUser(AuthUser user){
        userRepository.save(user);
        return user;
    }

    public List<AuthUser> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<AuthUser> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<AuthUser> getUserByCardNumber(String cardNumber){
        return userRepository.findByCardNumber(cardNumber);
    }

}
