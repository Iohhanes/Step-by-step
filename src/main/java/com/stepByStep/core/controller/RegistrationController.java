package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerUser(@RequestParam String username, @RequestParam String password){
        if(!(userService.addUser(username,password))){
            return "registration";
        }
        return "redirect:/login";
    }
}
