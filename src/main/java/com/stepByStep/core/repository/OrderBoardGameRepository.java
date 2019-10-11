package com.stepByStep.core.repository;

import com.stepByStep.core.model.entity.OrderBoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBoardGameRepository extends JpaRepository<OrderBoardGame,Long> {

}
