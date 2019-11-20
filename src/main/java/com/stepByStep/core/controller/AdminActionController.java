package com.stepByStep.core.controller;

import com.stepByStep.core.service.BoardGameService;
import com.stepByStep.core.util.ConfigurationPathManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.stepByStep.core.util.constants.PageMessageConstant.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminActionController {

    private BoardGameService boardGameService;

    @Autowired
    public AdminActionController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping("/post_new")
    public ModelAndView showPostingForm() {
        return new ModelAndView(ConfigurationPathManger.getPath(POST_NEW_PAGE_PATH));
    }

    @PostMapping("/post_new")
    public ModelAndView postNewBoardGame(@RequestParam("title") String title, @RequestParam String description,
                                         @RequestParam double price, @RequestParam int age,
                                         @RequestParam int countPlayers, @RequestParam("file") MultipartFile file) {
        boardGameService.post(title, price, age, countPlayers, description, file);
        return new ModelAndView("redirect:" + ConfigurationPathManger.getPath(STORE_PAGE_PATH));

    }


}
