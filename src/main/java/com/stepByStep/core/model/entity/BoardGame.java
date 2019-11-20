package com.stepByStep.core.model.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "board_games")
@EqualsAndHashCode
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_game_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    private String description;

    @Column(nullable = false)
    private double price;
    @Column(name = "average_age")
    private int averageAge;
    @Column(name = "count_players")
    private int countPlayers;
    private String filename;

    public BoardGame() {

    }

    public BoardGame(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(int averageAge) {
        this.averageAge = averageAge;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public void setCountPlayers(int countPlayers) {
        this.countPlayers = countPlayers;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
