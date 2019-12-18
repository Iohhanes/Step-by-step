package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.UserService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.FOUND_BOARD_GAME_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_CART_ITEM_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.CART_ITEM_DETAILS_PAGE_PATH;
import static com.stepByStep.core.util.constants.PageMessageConstant.CART_PAGE_PATH;
import static com.stepByStep.core.util.constants.URLValueConstant.*;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_ITEM_IN_CART;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_ITEM_IN_STORE;

@Log4j2
@Controller
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
public class CustomerActionWithCartController {

    private UserService userService;
    private CartService cartService;
    private CartItemService cartItemService;
    private BoardGameService boardGameService;

    public CustomerActionWithCartController(UserService userService, CartService cartService,
                                            CartItemService cartItemService, BoardGameService boardGameService) {
        this.userService = userService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.boardGameService = boardGameService;
    }

    @GetMapping(value = CART_USER_URL)
    public ModelAndView viewUserCart(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(CART_PAGE_PATH));
        modelAndView.addObject("cartItems", user.getCart().getItems());
        modelAndView.addObject("cart", user.getCart());
        return modelAndView;
    }

    @PostMapping(value = ADDING_BOARD_GAME_TO_CART_URL)
    public ModelAndView addItemToUserCart(@AuthenticationPrincipal User user, @RequestParam long boardGameId,
                                          @RequestParam int quantity)
            throws ControllerException {
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        ModelAndView modelAndView =
                new ModelAndView(REDIRECT_PART_URL + STORE_URL);
        try {
            cartService.extendUserCart(user.getCart(), cartItemService.createNewCartItem(boardGame, quantity));
        } catch (NullParameterException exception) {
            log.warn(exception.toString());
            throw new ControllerException(exception);
        } catch (ServiceException exception) {
            modelAndView.addObject("invalidQuantityError", INVALID_QUANTITY_ITEM_IN_STORE);
        }
        userService.save(user);
        return modelAndView;
    }

    @PostMapping(value = DELETING_CART_ITEM_FROM_CART_URL)
    public ModelAndView deleteItemFromUserCart(@AuthenticationPrincipal User user, @RequestParam long cartItemId,
                                               @RequestParam int quantity) throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_PART_URL + CART_USER_URL);
        try {
            cartService.reduceUserCart(user.getCart(), cartItemService.findById(cartItemId), quantity);
        } catch (NullParameterException exception) {
            log.warn(exception.toString());
            throw new ControllerException(exception);
        } catch (ServiceException exception) {
            modelAndView.addObject("invalidQuantityError", INVALID_QUANTITY_ITEM_IN_CART);
        }
        userService.save(user);
        return modelAndView;
    }

    @PostMapping(value = CLEANING_UP_USER_CART_URL)
    public ModelAndView clearUserCart(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView =
                new ModelAndView(REDIRECT_PART_URL + CART_USER_URL);
        cartService.clearUserCart(user.getCart());
        return modelAndView;
    }

    @GetMapping(value = CART_ITEM_DETAILS_URL)
    public ModelAndView showCartItemDetails(@PathVariable long cartItemId) throws ControllerException {
        CartItem cartItem = cartItemService.findById(cartItemId);
        ShopElementIsNullChecker.checkNull(cartItem, new ControllerException(INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(CART_ITEM_DETAILS_PAGE_PATH));
        modelAndView.addObject("cartItem", cartItem);
        return modelAndView;
    }

}

