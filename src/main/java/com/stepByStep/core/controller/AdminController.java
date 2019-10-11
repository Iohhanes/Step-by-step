package com.stepByStep.core.controller;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.service.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/post_new")
@PreAuthorize("permitAll" +
        "hasAuthority('ADMIN')")
public class AdminController {


    @Value("${upload.path}")
    private String uploadPath;

    private BoardGameService boardGameService;

    @Autowired
    public AdminController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping
    public String showTemplateForPostingNewBoardGame(){
        return "post_new";
    }

    @PostMapping
    public String postNewBoardGame(@RequestParam String name, @RequestParam String description,
                                  @RequestParam double price, @RequestParam int averageAge,
                                  @RequestParam int countPLayers, @RequestParam("file") MultipartFile file)
            throws IOException {
        BoardGame boardGame = new BoardGame(name, price);
        boardGame.setAverageAge(averageAge);
        boardGame.setCountPlayers(countPLayers);
        boardGame.setDescription(description);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            boardGame.setFilename(resultFilename);
        }
        boardGameService.save(boardGame);
        return "redirect:main";

    }
}
