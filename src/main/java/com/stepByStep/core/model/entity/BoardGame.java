package com.stepByStep.core.model.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "board_games")
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_game_id", updatable = false, insertable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "age")
    private int age;

    @Column(name = "count_players")
    private int countPlayers;

    private String filename;

    public BoardGame() {

    }

    public BoardGame(String title, double price) {
        this.title = title;
        this.price = price;
    }

    @Builder
    public BoardGame(String title, double price, int age, int countPlayers, String description, String filename) {
        this.title = title;
        this.price = price;
        this.age = age;
        this.countPlayers = countPlayers;
        this.description = description;
        this.filename = filename;
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

    public int getAge() {
        return age;
    }

    public void setAge(int averageAge) {
        this.age = averageAge;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGame boardGame = (BoardGame) o;
        return Double.compare(boardGame.price, price) == 0 &&
                age == boardGame.age &&
                countPlayers == boardGame.countPlayers &&
                id.equals(boardGame.id) &&
                title.equals(boardGame.title) &&
                Objects.equals(description, boardGame.description) &&
                Objects.equals(filename, boardGame.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, age, countPlayers, filename);
    }
}
