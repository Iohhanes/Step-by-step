package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.service.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    private BoardGameService boardGameService;

    @Autowired
    public MainController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String great(Model model){
        List<BoardGame> boardGameList=boardGameService.findAll();
        model.addAttribute("boardGames",boardGameList);
        return "main";
    }
}
