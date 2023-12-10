package com.main.service.iml;

import com.main.model.User;
import com.main.repository.UserRepository;
import com.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceImpl implements UserService {
    //inject the repository reference
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
