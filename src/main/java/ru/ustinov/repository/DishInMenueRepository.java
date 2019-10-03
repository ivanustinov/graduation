package ru.ustinov.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.DishInMenue;

import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 13.09.2019
 */
public interface DishInMenueRepository extends JpaRepository<DishInMenue, Integer> {

    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    Optional<DishInMenue> findById(Integer integer);

    @Override
    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<DishInMenue> findAll();

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    @OrderBy(value = "id")
    List<DishInMenue> findAllByDate(LocalDate date);

    @EntityGraph(attributePaths = {"dish"})
    List<DishInMenue> findAllByRestaurantId(int restaurant_id);

    @EntityGraph(attributePaths = {"dish", "restaurant"})
    List<DishInMenue> findAllByDateAndRestaurantId(LocalDate date, int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM DishInMenue m WHERE m.id=?1")
    int delete(int id);

}
