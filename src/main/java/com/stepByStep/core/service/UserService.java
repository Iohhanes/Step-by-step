package com.stepByStep.core.service;

import com.stepByStep.core.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {

    boolean addUser(User user);
}
