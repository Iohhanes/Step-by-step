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
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_game_id")
    private BoardGame boardGame;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone", nullable = false, length = 7)
    private String customerPhone;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public Order() {
        init();
    }

    @Builder
    public Order(User customer, BoardGame boardGame, String customerEmail, String customerPhone, String customerName,
                 int quantity) {
        this.customer = customer;
        this.boardGame = boardGame;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.quantity = quantity;
        init();
    }

    private void init() {
        dateCreated = new Date();
        status = OrderStatus.WAITED_DELIVERY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity &&
                id.equals(order.id) &&
                customer.equals(order.customer) &&
                boardGame.equals(order.boardGame) &&
                customerEmail.equals(order.customerEmail) &&
                customerPhone.equals(order.customerPhone) &&
                customerName.equals(order.customerName) &&
                dateCreated.equals(order.dateCreated) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, boardGame, customerEmail, customerPhone, customerName, quantity, dateCreated,
                status);
    }
}
