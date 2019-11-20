package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.util.ConfigurationPathManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@Controller
public class StoreController {

    private BoardGameService boardGameService;

    @Autowired
    public StoreController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping("/store")
    public ModelAndView viewStore() {
        List<BoardGame> boardGameList = boardGameService.findAll();
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(STORE_PAGE_PATH));
        modelAndView.addObject("boardGames", boardGameList);
        return modelAndView;
    }

}
