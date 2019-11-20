package com.stepByStep.core.controller;

import com.stepByStep.core.service.UserService;
import com.stepByStep.core.util.ConfigurationPathManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView viewRegistrationPage() {
        return new ModelAndView(ConfigurationPathManger.getPath(REGISTRATION_PAGE_PATH));
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(@RequestParam String username, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView("redirect:" + ConfigurationPathManger.
                getPath(LOGIN_PAGE_PATH));
        if (!(userService.addUser(username, password))) {
            modelAndView.setViewName(ConfigurationPathManger.getPath(REGISTRATION_PAGE_PATH));
        }
        return modelAndView;
    }
}
