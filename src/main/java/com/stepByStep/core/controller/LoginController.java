package com.stepByStep.core.controller;

import com.stepByStep.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @RequestMapping("/login")
//    public ModelAndView forwardToLoginPage() {
//        log.warn("method get");
//        return new ModelAndView(ConfigurationPathManger.getPath(LOGIN_PAGE_PATH));
//    }


}
