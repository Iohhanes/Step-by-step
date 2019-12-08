package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.service.UserService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import com.stepByStep.core.util.validators.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_CART_ITEM_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.*;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_BOARD_GAME_IN_ORDER;

@Slf4j
@Controller
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
public class UserActionWithOrdersController {

    private UserService userService;
    private OrderService orderService;
    private CartItemService cartItemService;
    private OrderValidator orderValidator;

    @Autowired
    public UserActionWithOrdersController(UserService userService, OrderService orderService,
                                          CartItemService cartItemService, OrderValidator orderValidator) {
        this.userService = userService;
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.orderValidator = orderValidator;
    }

    @PostMapping(value = "/showPlacingForm")
    public ModelAndView showPlacingForm(@RequestParam Long cartItemId)
            throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
        CartItem cartItem = cartItemService.findById(cartItemId);
        ShopElementIsNullChecker.checkNull(cartItem,
                new ControllerException("Id = " + cartItemId + ". " + INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        modelAndView.addObject("cartItem", cartItem);
        return modelAndView;
    }

    @PostMapping(value = "/placeOrder")
    public ModelAndView placeNewOrder(@AuthenticationPrincipal User user, @RequestParam Long cartItemId,
                                      @RequestParam int quantity, @RequestParam String name, @RequestParam String email,
                                      @RequestParam String phone, BindingResult bindingResult)
            throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(CART_PAGE_PATH));
        CartItem cartItem = cartItemService.findById(cartItemId);
        ShopElementIsNullChecker.checkNull(cartItem,
                new ControllerException("Id = " + cartItemId + ". " + INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        Order order = Order.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .user(user)
                .boardGame(cartItem.getBoardGame())
                .quantity(quantity)
                .build();
        orderValidator.validate(order, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:" + ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
            return modelAndView;
        }
        try {
            orderService.place(user, order);
        } catch (NullParameterException exception) {
            log.error(exception.toString());
            throw new ControllerException(exception);
        } catch (ServiceException exception) {
            modelAndView.setViewName("redirect:" + ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
            modelAndView.addObject("invalidQuantityError", INVALID_QUANTITY_BOARD_GAME_IN_ORDER);
        }
        return modelAndView;
    }

    @GetMapping(value = "/historyOrders")
    public ModelAndView showHistoryUserOrders(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(HISTORY_ORDERS_PAGE_PATH));
        Set<Order> orders = user.getOrders();
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }


}
