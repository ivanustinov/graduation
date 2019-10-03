package ru.ustinov.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 15.09.2019
 */

public interface VoteRepository extends JpaRepository<Vote, Integer> {


    @Override
    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findAll();

    @Override
    @EntityGraph(attributePaths = {"user", "restaurant"})
    Optional<Vote> findById(Integer integer);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findAllByDate(LocalDate date);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    List<Vote> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @EntityGraph(attributePaths = {"user", "restaurant"})
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    Long countAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);
}
