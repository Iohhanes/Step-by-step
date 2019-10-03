package com.stepByStep.core.model.entity;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "board_games")
@EqualsAndHashCode
@ToString
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    private Double price;
    @Column(name = "average_age")
    private Integer averageAge;
    @Column(name = "count_players")
    private Integer countPlayers;
    private String filename;

    public BoardGame(){

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

    public Integer getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(Integer averageAge) {
        this.averageAge = averageAge;
    }

    public Integer getCountPlayers() {
        return countPlayers;
    }

    public void setCountPlayers(Integer countPlayers) {
        this.countPlayers = countPlayers;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
