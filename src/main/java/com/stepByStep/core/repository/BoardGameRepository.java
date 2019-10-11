package com.stepByStep.core.repository;

import com.stepByStep.core.model.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame,Long> {

    List<BoardGame> findByPrice(double price);

    List<BoardGame> findByAverageAge(int averageAge);

    List<BoardGame> findByCountPlayers(int countPlayers);

}
