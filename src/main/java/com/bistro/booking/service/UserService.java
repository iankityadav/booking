package com.bistro.booking.service;

import com.bistro.booking.model.Users;
import com.bistro.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<Users> findUserById(long id){
        System.out.println("asdfghjk "+id);
        return userRepository.findById(id);
    }
}
