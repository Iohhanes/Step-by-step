package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.FOUND_BOARD_GAME_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.BOARD_GAME_DETAILS_PAGE_PATH;
import static com.stepByStep.core.util.constants.PageMessageConstant.STORE_PAGE_PATH;
import static com.stepByStep.core.util.constants.URLValueConstant.*;

@Log4j2
@Controller
public class StoreController {

    private BoardGameService boardGameService;

    @Autowired
    public StoreController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping(value = STORE_URL)
    public ModelAndView viewStore() {
        List<BoardGame> boardGameList = boardGameService.findAll();
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(STORE_PAGE_PATH));
        modelAndView.addObject("boardGames", boardGameList);
        return modelAndView;
    }

    @GetMapping(value = BOARD_GAME_DETAILS_URL)
    public ModelAndView showBoardGameDetails(@PathVariable long boardGameId) throws ControllerException {
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame, new ControllerException(FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(BOARD_GAME_DETAILS_PAGE_PATH));
        modelAndView.addObject("boardGame", boardGame);
        return modelAndView;
    }

    @PostMapping(value = SORTING_ALPHABETICALLY_BOARD_GAMES_URL)
    public ModelAndView sortBoardGamesAlphabetically() {
        List<BoardGame> boardGameList = boardGameService.findAllOrderByName();
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(STORE_PAGE_PATH));
        modelAndView.addObject("boardGames", boardGameList);
        return modelAndView;
    }

    @PostMapping(value = SEARCH_BOARD_GAME_URL)
    public ModelAndView findBoardGamesByFilters(@RequestParam String title, @RequestParam Double price,
                                                @RequestParam Integer countPlayers, @RequestParam Integer age) {
        List<BoardGame> boardGameList = boardGameService.findByFilters(title, price, countPlayers, age);
        ModelAndView modelAndView = new ModelAndView(STORE_URL);
        modelAndView.addObject("boardGames", boardGameList);
        return modelAndView;
    }

}
