package ru.ustinov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Dish;

import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 14.09.2019
 */
public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findDishByActualOrderByName(boolean actual);

    Dish findDishByNameAndActual(String name, boolean actual);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1")
    int delete(int id);
}
