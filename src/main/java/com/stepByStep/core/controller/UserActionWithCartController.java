package com.stepByStep.core.controller;

import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.service.CartItemService;
import com.stepByStep.core.service.CartService;
import com.stepByStep.core.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@PreAuthorize("hasAuthority({'ADMIN','USER'})")
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


//    @PostMapping("/{boardGameTitle}")
//    public ModelAndView addItemToCart(@RequestParam(name = "boardGameId") Long boardGameId,
//                                      @AuthenticationPrincipal User user,
//                                      @RequestParam(name = "quantity") int quantity) throws ServiceException {
//        BoardGame boardGame = boardGameService.findById(boardGameId);
//        cartService.extendUserCart(user.getCart(), cartItemService.createNewCartItem(boardGame, quantity));
//        userService.save(user);
//        return new ModelAndView(ConfigurationPathManger.getPath(MAIN_PAGE_PATH));
//    }

//    @PostMapping("/cart")
//    public ModelAndView viewCart(@AuthenticationPrincipal User user) {
//        ModelAndView modelAndView = new ModelAndView("");
//        modelAndView.addObject("userCart", user.getCart().getItems());
//        return modelAndView;
//    }
//
//    @PostMapping("/cart")
//    public ModelAndView deleteItemFromCart(@AuthenticationPrincipal User user,
//                                           @RequestParam(name = "cartItem") Long cartItemId,
//                                           @RequestParam(name = "quantity") int quantity) {
//        ModelAndView modelAndView = new ModelAndView("");
//        cartService.reduceUserCart(user.getCart(), cartItemService.findById(cartItemId),quantity);
//        return modelAndView;
//}
    }

