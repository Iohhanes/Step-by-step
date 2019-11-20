package com.stepByStep.core.controller;

import com.stepByStep.core.util.ConfigurationPathManger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView great() {
        return new ModelAndView(ConfigurationPathManger.getPath(MAIN_PAGE_PATH));
    }
}
