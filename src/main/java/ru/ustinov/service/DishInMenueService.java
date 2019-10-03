package ru.ustinov.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Dish;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.model.Restaurant;
import ru.ustinov.repository.DishInMenueRepository;
import ru.ustinov.repository.DishRepository;
import ru.ustinov.repository.RestaurantRepository;
import ru.ustinov.to.DishInMenueTo;
import ru.ustinov.util.ValidationUtil;

import java.time.LocalDate;
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
public class DishInMenueService {


    private final RestaurantRepository restaurantRepository;

    private final DishInMenueRepository dishInMenueRepository;

    private final DishRepository dishRepository;

    public DishInMenueService(DishRepository dishRepository, DishInMenueRepository dishInMenueRepository, RestaurantRepository restaurantRepository) {
        this.dishInMenueRepository = dishInMenueRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public DishInMenue createNewFromTo(DishInMenueTo dishInMenueTo) {
        return new DishInMenue(dishInMenueTo.getDate(), dishRepository.getOne(dishInMenueTo.getDishId()), restaurantRepository.getOne(dishInMenueTo.getRestaurantId()));
    }

    public List<DishInMenue> getAll() {
        return dishInMenueRepository.findAll();
    }


    public List<DishInMenue> getAllByDate(LocalDate date) {
        return dishInMenueRepository.findAllByDate(date);
    }

    public DishInMenue getById(int id) {
        return checkNotFoundWithId(dishInMenueRepository.findById(id).orElse(null), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(DishInMenueTo dishInMenueTo) {
        ValidationUtil.validateDishInMenueTo(dishInMenueTo);
        Integer menuId = dishInMenueTo.getId();
        DishInMenue dishInMenue = checkNotFoundWithId(getById(menuId), menuId);
        Dish dish = dishRepository.getOne(dishInMenueTo.getDishId());
        Restaurant restaurant = restaurantRepository.getOne(dishInMenueTo.getRestaurantId());
        dishInMenue.setDish(dish);
        dishInMenue.setRestaurant(restaurant);
        dishInMenue.setDate(dishInMenueTo.getDate());
        dishInMenueRepository.save(dishInMenue);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public DishInMenue create(DishInMenueTo dishInMenueTo) {
        ValidationUtil.validateDishInMenueTo(dishInMenueTo);
        checkNew(dishInMenueTo);
        Dish dish = dishRepository.getOne(dishInMenueTo.getDishId());
        Restaurant restaurant = restaurantRepository.getOne(dishInMenueTo.getRestaurantId());
        return initLazyObj(dishInMenueRepository.save(new DishInMenue(dishInMenueTo.getDate(), dish, restaurant)));
    }

    //init lazy restaurant and dish after saving
    private DishInMenue initLazyObj(DishInMenue dishInMenue) {
        dishInMenue.getDish().toString();
        dishInMenue.getRestaurant().toString();
        return dishInMenue;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(dishInMenueRepository.delete(id) != 0, id);
    }

}
