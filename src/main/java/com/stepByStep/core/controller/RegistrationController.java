package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.UserService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.validators.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.REGISTRATION_PAGE_PATH;
import static com.stepByStep.core.util.constants.URLValueConstant.*;

@Log4j2
@Controller
public class RegistrationController {

    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = REGISTRATION_URL)
    public ModelAndView viewRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(REGISTRATION_PAGE_PATH));
        modelAndView.addObject("userForm", new User());
        return modelAndView;
    }

    @PostMapping(value = REGISTRATION_URL)
    public ModelAndView registerUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        ModelAndView modelAndView =
                new ModelAndView(REDIRECT_PART_URL + LOGIN_URL);
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.debug("Invalid data at registration: \n" + bindingResult.getAllErrors().toString());
            modelAndView.setViewName(ConfigurationPathManger.getPath(REGISTRATION_PAGE_PATH));
            return modelAndView;
        }
        userService.addUser(userForm.getUsername(), userForm.getPassword());
        return modelAndView;
    }
}
