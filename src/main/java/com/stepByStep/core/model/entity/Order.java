package com.stepByStep.core.model.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_game_id")
    private BoardGame boardGame;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 7)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public Order() {

    }

    @Builder
    public Order(User user, BoardGame boardGame, String email, String phone, String name, int quantity) {
        this.user = user;
        this.boardGame = boardGame;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.quantity = quantity;
        this.dateCreated = new Date();
        this.status = OrderStatus.WAITED_DELIVERY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity &&
                id.equals(order.id) &&
                user.equals(order.user) &&
                boardGame.equals(order.boardGame) &&
                email.equals(order.email) &&
                phone.equals(order.phone) &&
                name.equals(order.name) &&
                dateCreated.equals(order.dateCreated) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, boardGame, email, phone, name, quantity, dateCreated, status);
    }
}
