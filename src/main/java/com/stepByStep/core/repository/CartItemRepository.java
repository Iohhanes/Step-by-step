package com.stepByStep.core.repository;

import com.stepByStep.core.model.entity.BoardGame;
import com.stepByStep.core.model.entity.Cart;
import com.stepByStep.core.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByBoardGame(BoardGame boardGame);

    List<CartItem> findAllByBoardGame(BoardGame boardGame);

    List<CartItem> findByCart(Cart cart);

    void deleteAllByCart(Cart cart);

}
