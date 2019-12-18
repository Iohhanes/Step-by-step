package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.service.StorageService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.validators.BoardGameValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.FOUND_BOARD_GAME_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_ORDER_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.*;
import static com.stepByStep.core.util.constants.URLValueConstant.*;

@Log4j2
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminActionController {

    private BoardGameService boardGameService;
    private BoardGameValidator boardGameValidator;
    private StorageService storageService;
    private CartService cartService;
    private OrderService orderService;

    @Autowired
    public AdminActionController(BoardGameService boardGameService, BoardGameValidator boardGameValidator,
                                 StorageService storageService, CartService cartService, OrderService orderService) {
        this.boardGameService = boardGameService;
        this.boardGameValidator = boardGameValidator;
        this.storageService = storageService;
        this.cartService = cartService;
        this.orderService = orderService;

    }

    @PostMapping(value = POSTING_FORM_URL)
    public ModelAndView showPostingForm() {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(POST_BOARD_GAME_PAGE_PATH));
        modelAndView.addObject("boardGameForm", new BoardGame());
        return modelAndView;
    }

    @PostMapping(value = POSTING_BOARD_GAME_URL)
    public ModelAndView postBoardGame(@ModelAttribute("boardGameForm") BoardGame boardGameForm,
                                      BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_PART_URL + STORE_URL);
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

    @PostMapping(value = DELETING_BOARD_GAME_URL)
    public ModelAndView deleteBoardGame(@RequestParam long boardGameId) throws ControllerException {
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        boardGameService.delete(boardGame);
        storageService.deleteImage(boardGame.getFilename());
        cartService.updateAll();
        return new ModelAndView(REDIRECT_PART_URL + STORE_URL);
    }

    @PostMapping(value = EDITING_FORM_URL)
    public ModelAndView showEditingForm(@RequestParam long boardGameId) throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(EDIT_BOARD_GAME_PAGE_PATH));
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        modelAndView.addObject("boardGameForm", boardGame);
        return modelAndView;
    }


    @PostMapping(value = EDITING_BOARD_GAME_URL)
    public ModelAndView editBoardGame(@ModelAttribute("boardGameForm") BoardGame boardGameForm,
                                      @RequestParam("boardGameFormId") Long boardGameFormId,
                                      BindingResult bindingResult, @RequestParam("file") MultipartFile file)
            throws ControllerException {
        ModelAndView modelAndView =
                new ModelAndView(REDIRECT_PART_URL + STORE_URL);
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

    @GetMapping(value = ORDERS_MANAGEMENT_URL)
    public ModelAndView showAllOrders() {
        List<Order> orders = orderService.findAll();
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(ORDERS_MANAGEMENT_PAGE_PATH));
        modelAndView.addObject("orders", orders);
        log.error(modelAndView.getViewName());
        return modelAndView;
    }

    @PostMapping(value = CHANGING_ORDER_STATUS)
    public ModelAndView changeOrderStatus(@RequestParam long orderId) throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_PART_URL+ORDERS_MANAGEMENT_URL);
        try {
            Order order = orderService.findById(orderId);
            orderService.changeStatus(order);
        } catch (NullParameterException exception) {
            throw new ControllerException(INPUT_ORDER_IS_NULL_EXCEPTION);
        }
        return modelAndView;
    }

}
