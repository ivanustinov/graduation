package ru.ustinov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ustinov.model.Dish;
import ru.ustinov.repository.DishRepository;

import java.util.List;

import static ru.ustinov.util.ValidationUtil.checkNew;
import static ru.ustinov.util.ValidationUtil.checkNotFoundWithId;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 20.09.2019
 */

@Service
public class DishService {

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public List<Dish> getAll() {
        return repository.findAll();
    }

    public List<Dish> getAllActualDishes() {
        return repository.findDishByActualOrderByName(true);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public Dish create(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        checkNew(dish);
        Dish oldActual = repository.findDishByNameAndActual(dish.getName(), true);
        if (oldActual != null) {
            oldActual.setActual(false);
            repository.save(oldActual);
        }
        return checkNotFoundWithId(repository.save(dish), dish.getId());
    }


    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must not be null");
        Integer id = dish.getId();
        checkNotFoundWithId(get(id), id);
        repository.save(dish);
    }


    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

}
