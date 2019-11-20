package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void save(User user);

    User findById(Long userId);

    boolean addUser(String username, String password);
}
