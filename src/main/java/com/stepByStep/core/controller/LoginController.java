package com.stepByStep.core.controller;

import com.stepByStep.core.util.ConfigurationPathManger;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.LOGIN_PAGE_PATH;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_LOGIN_DATA_MESSAGE;
import static com.stepByStep.core.util.constants.URLValueConstant.*;

@Log4j2
@Controller
public class LoginController {

    @RequestMapping(value = LOGIN_URL)
    public ModelAndView showLoginPage(String error) {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(LOGIN_PAGE_PATH));
        if (error != null) {
            log.debug(INVALID_LOGIN_DATA_MESSAGE);
            modelAndView.addObject("loginDataError", INVALID_LOGIN_DATA_MESSAGE);
        }
        return modelAndView;
    }

}
