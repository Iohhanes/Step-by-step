package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String viewRegistrationPage(){
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(User user){
        if(!(userService.addUser(user))){
            return "registration";
        }
        return "redirect:/login";
    }
}
