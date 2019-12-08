package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.FOUND_BOARD_GAME_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.CART_PAGE_PATH;
import static com.stepByStep.core.util.constants.PageMessageConstant.STORE_PAGE_PATH;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_ITEM_IN_CART;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_ITEM_IN_STORE;

@Slf4j
@Controller
@PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_USER'})")
public class UserActionWithCartController {

    private UserService userService;
    private CartService cartService;
    private CartItemService cartItemService;
    private BoardGameService boardGameService;

    public UserActionWithCartController(UserService userService, CartService cartService,
                                        CartItemService cartItemService, BoardGameService boardGameService) {
        this.userService = userService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.boardGameService = boardGameService;
    }

    @GetMapping(value = "/cart")
    public ModelAndView viewUserCart(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(CART_PAGE_PATH));
        modelAndView.addObject("cartItems", user.getCart().getItems());
        modelAndView.addObject("cart", user.getCart());
        return modelAndView;
    }

    @PostMapping(value = "/addItemToUserCart")
    public ModelAndView addItemToUserCart(@AuthenticationPrincipal User user, @RequestParam long boardGameId,
                                          @RequestParam int quantity)
            throws ControllerException {
        BoardGame boardGame = boardGameService.findById(boardGameId);
        ShopElementIsNullChecker.checkNull(boardGame,
                new ControllerException("Id = " + boardGameId + ". " + FOUND_BOARD_GAME_IS_NULL_EXCEPTION));
        ModelAndView modelAndView =
                new ModelAndView("redirect:" + ConfigurationPathManger.getPath(STORE_PAGE_PATH));
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

    @PostMapping(value = "deleteItemFromUserCart")
    public ModelAndView deleteItemFromUserCart(@AuthenticationPrincipal User user, @RequestParam long cartItemId,
                                               @RequestParam int quantity) throws ControllerException {
        ModelAndView modelAndView =
                new ModelAndView("redirect:" + ConfigurationPathManger.getPath(CART_PAGE_PATH));
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

    @PostMapping(value = "clearUserCart")
    public ModelAndView clearUserCart(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView =
                new ModelAndView("redirect:" + ConfigurationPathManger.getPath(CART_PAGE_PATH));
        cartService.clearUserCart(user.getCart());
        userService.save(user);
        return modelAndView;
    }

}

