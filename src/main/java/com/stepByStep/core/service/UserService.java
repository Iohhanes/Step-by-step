package com.stepByStep.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {

    boolean addUser(String username, String password);
}
