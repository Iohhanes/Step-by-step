package com.stepByStep.core.util.handler;

import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.exceptions.ControllerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ControllerException.class)
    public ModelAndView handleControllerErrors(HttpServletRequest request, ControllerException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName(ConfigurationPathManger.getPath(ERROR_PAGE_PATH));
        return modelAndView;
    }
}
