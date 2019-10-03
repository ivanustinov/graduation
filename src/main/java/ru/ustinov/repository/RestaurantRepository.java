package ru.ustinov.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Restaurant;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 12.09.2019
 */

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {


//    @EntityGraph(attributePaths = {"dishesInMenue"})
//    @Query("SELECT DISTINCT r from Restaurant r " +
//            "JOIN FETCH r.dishesInMenue d " +
//            "JOIN FETCH d.dish di " +
//            "WHERE d.date=?1 ORDER BY r.name")
//    List<Restaurant> findAllWithDishesInMenueByDate(LocalDate localDate);

    @Query("SELECT  r FROM Restaurant r " +
            "ORDER BY r.name")
    List<Restaurant> findAll();


    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "JOIN FETCH r.dishesInMenue d " +
            "JOIN FETCH d.dish " +
            "ORDER BY r.name")
    List<Restaurant> findAllWithDishesInMenue();


    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "ORDER BY size(r.votes) DESC ")
    List<Restaurant> findAllWithVotes();


    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "JOIN FETCH r.dishesInMenue d " +
            "JOIN FETCH d.dish " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "ORDER BY size(r.votes) DESC")
    List<Restaurant> findAllWithDishesInMenueAndVotes();


    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.dishesInMenue d " +
            "JOIN FETCH d.dish di " +
            "WHERE r.id=?1 ")
    Restaurant findByIdWithDishesInMenue(int id);


    @Query("SELECT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user " +
            "WHERE r.id=?1 ")
    Restaurant findByIdWithVotes(int id);


    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "JOIN FETCH r.dishesInMenue d " +
            "LEFT JOIN FETCH d.dish " +
            "LEFT JOIN FETCH r.votes v " +
            "LEFT JOIN FETCH v.user WHERE r.id=?1")
    Restaurant findByIdWithDishesInMenueAndVotes(int id);

    @Query("SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN r.dishesInMenue d " +
            "WHERE d.date=?1 ORDER BY r.name")
    List<Restaurant> findByDate(LocalDate date);

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT DISTINCT r from Restaurant r " +
            "JOIN r.dishesInMenue  d " +
            "ON d.date=?1 " +
            "LEFT JOIN r.votes v " +
            "ON v.date=?1 ")
    List<Restaurant> getWithCountOfVotesByDate(LocalDate localDate);


    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int delete(int id);

}
