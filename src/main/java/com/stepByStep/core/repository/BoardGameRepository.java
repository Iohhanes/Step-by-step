package com.stepByStep.core.repository;

import com.stepByStep.core.model.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    List<BoardGame> findByTitle(String title);

    List<BoardGame> findByPrice(double price);

    List<BoardGame> findByAge(int age);

    List<BoardGame> findByCountPlayers(int countPlayers);

    @Query(value = "select \n" +
            "       *\n" +
            "from    \n" +
            "        board_games\n" +
            "where \n" +
            "        (title = case when ?1 <> '' then ?1 else title end)\n" +
            "        AND (price = COALESCE(?2, price))\n" +
            "        AND (count_players = COALESCE(?3, count_players))\n" +
            "        AND (age = COALESCE(?4, age))", nativeQuery = true)
    List<BoardGame> findByFilters(String title, Double price, Integer countPlayers, Integer age);
}
