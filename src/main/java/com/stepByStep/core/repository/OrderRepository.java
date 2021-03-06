package com.stepByStep.core.repository;

import com.stepByStep.core.model.entity.Order;
import com.stepByStep.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

    List<Order> findAllByCustomer(User customer);
}
