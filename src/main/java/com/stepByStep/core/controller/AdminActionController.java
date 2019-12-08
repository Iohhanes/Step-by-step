package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.StorageService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import com.stepByStep.core.util.validators.BoardGameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.FOUND_BOARD_GAME_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminActionController {

    private BoardGameService boardGameService;
    private BoardGameValidator boardGameValidator;
    private StorageService storageService;
    private CartService cartService;

    @Autowired
    public AdminActionController(BoardGameService boardGameService, BoardGameValidator boardGameValidator,
                                 StorageService storageService, CartService cartService) {
        this.boardGameService = boardGameService;
        this.boardGameValidator = boardGameValidator;
        this.storageService = storageService;
        this.cartService = cartService;

    }

    @PostMapping(value = "/showPostingForm")
    public ModelAndView showPostingForm() {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(POST_BOARD_GAME_PAGE_PATH));
        modelAndView.addObject("boardGameForm", new BoardGame());
        return modelAndView;
    }

    @PostMapping(value = "/postBoardGame")
    public ModelAndView postBoardGame(@ModelAttribute("boardGameForm") BoardGame boardGameForm,
                                      BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView =
                new ModelAndView("redirect:" + ConfigurationPathManger.getPath(STORE_PAGE_PATH));
        boardGameForm.setFilename(storageService.loadImage(file));
        boardGameValidator.validate(boardGameForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.debug("Invalid data when posting a new board game: \n" + bindingResult.getAllErrors().toString());
            modelAndView.setViewName(ConfigurationPathManger.getPath(POST_BOARD_GAME_PAGE_PATH));
            return modelAndView;
        }
        boardGameService.save(boardGameForm);
        return modelAndView;

    }

    @PostMapping(value = "/deleteBoardGame")
    public ModelAndView deleteBoardGame(@RequestParam long boardGameId) throws ControllerException {
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        boardGameService.delete(boardGame);
        return new ModelAndView("redirect:" + ConfigurationPathManger.getPath(STORE_PAGE_PATH));
    }

    @PostMapping(value = "/showEditingForm")
    public ModelAndView showEditingForm(@RequestParam long boardGameId) throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(EDIT_BOARD_GAME_PAGE_PATH));
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        modelAndView.addObject("boardGameForm", boardGame);
        return modelAndView;
    }


    @PostMapping(value = "/editBoardGame")
    public ModelAndView editBoardGame(@ModelAttribute("boardGameForm") BoardGame boardGameForm,
                                      @RequestParam("boardGameFormId") Long boardGameFormId,
                                      BindingResult bindingResult, @RequestParam("file") MultipartFile file)
            throws ControllerException {
        ModelAndView modelAndView =
                new ModelAndView("redirect:" + ConfigurationPathManger.getPath(STORE_PAGE_PATH));
        boardGameForm.setFilename(storageService.loadImage(file));
        boardGameValidator.validate(boardGameForm, bindingResult);
        if (bindingResult.hasErrors()) {
            log.debug("Invalid data when editing a board game: \n" + bindingResult.getAllErrors().toString());
            modelAndView.setViewName(ConfigurationPathManger.getPath(EDIT_BOARD_GAME_PAGE_PATH));
            return modelAndView;
        }
        BoardGame boardGame = boardGameService.findById(boardGameFormId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameFormId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        boardGameService.edit(boardGame, boardGameForm);
        cartService.updateAll();
        return modelAndView;
    }

}
