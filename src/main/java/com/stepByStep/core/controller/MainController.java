package com.stepByStep.core.controller;

import com.stepByStep.core.util.ConfigurationPathManger;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.MAIN_PAGE_PATH;
import static com.stepByStep.core.util.constants.URLValueConstant.*;

@Log4j2
@Controller
public class MainController {

    @GetMapping(value = MAIN_PAGE_URL)
    public ModelAndView great() {
        return new ModelAndView(ConfigurationPathManger.getPath(MAIN_PAGE_PATH));
    }

}
