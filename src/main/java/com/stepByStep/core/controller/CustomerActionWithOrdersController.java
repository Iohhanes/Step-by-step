package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.CartItem;
import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.OrderService;
import com.stepByStep.core.util.ConfigurationPathManger;
import com.stepByStep.core.util.ShopElementIsNullChecker;
import com.stepByStep.core.util.exceptions.ControllerException;
import com.stepByStep.core.util.exceptions.NullParameterException;
import com.stepByStep.core.util.exceptions.ServiceException;
import com.stepByStep.core.util.validators.OrderValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_CART_ITEM_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.ExceptionDescriptionConstant.INPUT_ORDER_IS_NULL_EXCEPTION;
import static com.stepByStep.core.util.constants.PageMessageConstant.*;
import static com.stepByStep.core.util.constants.URLValueConstant.*;
import static com.stepByStep.core.util.constants.ValidationDescriptionConstant.INVALID_QUANTITY_BOARD_GAME_IN_ORDER;

@Log4j2
@Controller
@PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER')")
public class CustomerActionWithOrdersController {

    private OrderService orderService;
    private CartItemService cartItemService;
    private OrderValidator orderValidator;

    @Autowired
    public CustomerActionWithOrdersController(OrderService orderService, CartItemService cartItemService,
                                              OrderValidator orderValidator) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.orderValidator = orderValidator;
    }

    @PostMapping(value = PLACING_FORM_URL)
    public ModelAndView showPlacingForm(@RequestParam Long cartItemId)
            throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
        CartItem cartItem = cartItemService.findById(cartItemId);
        ShopElementIsNullChecker.checkNull(cartItem,
                new ControllerException("Id = " + cartItemId + ". " + INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        modelAndView.addObject("orderForm", new Order());
        modelAndView.addObject("cartItem", cartItem);
        return modelAndView;
    }

    @PostMapping(value = PLACING_ORDER_URL)
    public ModelAndView placeNewOrder(@ModelAttribute("orderForm") Order orderForm, @AuthenticationPrincipal User user,
                                      @RequestParam("cartItemId") long cartItemId, BindingResult bindingResult)
            throws ControllerException {
        ModelAndView modelAndView = new ModelAndView(REDIRECT_PART_URL + CART_USER_URL);
        CartItem cartItem = cartItemService.findById(cartItemId);
        ShopElementIsNullChecker.checkNull(cartItem, new ControllerException(INPUT_CART_ITEM_IS_NULL_EXCEPTION));
        orderForm.setBoardGame(cartItem.getBoardGame());
        orderForm.setCustomer(user);
        orderValidator.validate(orderForm, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("cartItem", cartItem);
            modelAndView.setViewName(ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
            return modelAndView;
        }
        try {
            orderService.place(user, orderForm);
        } catch (NullParameterException exception) {
            log.error(exception.toString());
            throw new ControllerException(exception);
        } catch (ServiceException exception) {
            modelAndView.addObject("cartItem", cartItem);
            modelAndView.setViewName(ConfigurationPathManger.getPath(PLACE_ORDER_PAGE_PATH));
            modelAndView.addObject("invalidQuantityError", INVALID_QUANTITY_BOARD_GAME_IN_ORDER);
        }
        return modelAndView;
    }

    @GetMapping(value = ORDERS_USER_HISTORY_URL)
    public ModelAndView showHistoryUserOrders(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(ORDERS_HISTORY_PAGE_PATH));
        Set<Order> orders = user.getOrders();
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN','ROLE_CUSTOMER'})")
    @GetMapping(value = ORDER_DETAILS_URL)
    public ModelAndView showOrderDetails(@PathVariable long orderId) throws ControllerException {
        Order order = orderService.findById(orderId);
        ShopElementIsNullChecker.checkNull(order, new ControllerException(INPUT_ORDER_IS_NULL_EXCEPTION));
        ModelAndView modelAndView = new ModelAndView(ConfigurationPathManger.getPath(ORDER_DETAILS_PAGE_PATH));
        modelAndView.addObject("order", order);
        return modelAndView;
    }
}
