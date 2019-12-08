package com.stepByStep.core.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    public static final int DEFAULT_TOTAL_COUNT_ITEMS = 0;
    public static final double DEFAULT_TOTAL_COST = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartItem> items;

    @Column(name = "total_count_items")
    private int totalCountItems;

    @Column(name = "total_cost")
    private double totalCost;

    public Cart() {
        init();
    }

    public Cart(User user) {
        this.user = user;
        init();
    }

    private void init() {
        this.items = new ArrayList<>();
        this.totalCountItems = DEFAULT_TOTAL_COUNT_ITEMS;
        this.totalCost = DEFAULT_TOTAL_COST;
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

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalCountItems() {
        return totalCountItems;
    }

    public void setTotalCountItems(int totalCountItems) {
        this.totalCountItems = totalCountItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return totalCountItems == cart.totalCountItems &&
                Double.compare(cart.totalCost, totalCost) == 0 &&
                id.equals(cart.id) &&
                Objects.equals(user, cart.user) &&
                items.equals(cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, items, totalCountItems, totalCost);
    }
}
