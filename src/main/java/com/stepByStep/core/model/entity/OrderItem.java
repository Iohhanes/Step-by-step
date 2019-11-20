package com.stepByStep.core.model.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@EqualsAndHashCode
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_order_id", unique = true, nullable = false)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "board_game_id", nullable = false)
    private BoardGame boardGame;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem() {

    }

    public OrderItem(Order order, BoardGame boardGame, int quantity) {
        this.order = order;
        this.boardGame = boardGame;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
