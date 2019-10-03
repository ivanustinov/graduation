package ru.ustinov.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.model.Restaurant;
import ru.ustinov.model.Vote;
import ru.ustinov.repository.DishInMenueRepository;
import ru.ustinov.repository.RestaurantRepository;
import ru.ustinov.repository.VoteRepository;
import ru.ustinov.to.RestaurantVoteCountTo;
import ru.ustinov.util.EntitiesUtil;
import ru.ustinov.util.ValidationUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.ustinov.util.ValidationUtil.checkNew;
import static ru.ustinov.util.ValidationUtil.checkNotFoundWithId;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 24.09.2019
 */
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishInMenueRepository menueRepository;
    private final VoteRepository voteRepository;


    public RestaurantService(RestaurantRepository restaurantRepository, DishInMenueRepository menueRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menueRepository = menueRepository;
        this.voteRepository = voteRepository;
    }


    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithDishesInMenue() {
        return restaurantRepository.findAllWithDishesInMenue();
    }

    public List<Restaurant> getAllWithVotes() {
        return restaurantRepository.findAllWithVotes();
    }

    public List<Restaurant> getAllWithDishesInMenueAndVotes() {
        return restaurantRepository.findAllWithDishesInMenueAndVotes();
    }

    public Restaurant get(Integer id) {
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    //  Find only restaurants that have menue for this date
    public List<Restaurant> getByMenueDate(LocalDate menueDate) {
        return restaurantRepository.findByDate(menueDate);
    }

    public List<RestaurantVoteCountTo> getWithVotesCountByDate(LocalDate date) {
        List<Restaurant> restaurants = restaurantRepository.getWithCountOfVotesByDate(date);
        return restaurants.stream()
                .map(r -> {
                    Set<Vote> votes = r.getVotes();
                    long count = votes.size();
                    votes.clear();
                    return new RestaurantVoteCountTo(date, r, count);
                }).sorted(Comparator.comparingLong(RestaurantVoteCountTo::getVotesCount).reversed()).collect(Collectors.toList());
    }


    public Restaurant getWithDishesInMenueById(int id) {
        return checkNotFoundWithId(restaurantRepository.findByIdWithDishesInMenue(id), id);
    }

    public Restaurant getWithVotesById(int id) {
        return checkNotFoundWithId(restaurantRepository.findByIdWithVotes(id), id);
    }

    public Restaurant getWithDishesInMenueAndVotesById(int id) {
        return checkNotFoundWithId(restaurantRepository.findByIdWithDishesInMenueAndVotes(id), id);
    }

    public Restaurant getWithDishesInMenueByIdAndDate(int id, LocalDate date) {
        List<DishInMenue> dishesInMenue = menueRepository.findAllByDateAndRestaurantId(date, id);
        if (!dishesInMenue.isEmpty()) {
            return EntitiesUtil.getRestaurantWithMenue(dishesInMenue.get(0).getRestaurant(), dishesInMenue, id);
        }
//      Find restaurant from repository only if there was no dishesImMenue by this id and date:
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        return EntitiesUtil.getRestaurantWithMenue(restaurant, Collections.emptyList(), id);
    }

    public Restaurant getWithVotesByIdAndDate(int id, LocalDate date) {
        List<Vote> votes = voteRepository.findByRestaurantIdAndDate(id, date);
        if (!votes.isEmpty()) {
            return EntitiesUtil.getRestaurantWithVotes(votes.get(0).getRestaurant(), votes, id);
        }
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById((id)).orElse(null), id);
        return EntitiesUtil.getRestaurantWithVotes(restaurant, Collections.emptyList(), id);
    }


    public RestaurantVoteCountTo getWithVotesCountByIdAndDate(int id, LocalDate date) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById((id)).orElse(null), id);
        Long voteCount = voteRepository.countAllByRestaurantIdAndDate(id, date);
        return new RestaurantVoteCountTo(date, restaurant, voteCount);
    }

    public Restaurant getWithDishesInMenueAndVotesByIdAndDate(int id, LocalDate date) {
        Restaurant restaurant = null;

        List<DishInMenue> dishesInMenue = menueRepository.findAllByDateAndRestaurantId(date, id);
        if (!dishesInMenue.isEmpty()) {
            restaurant = dishesInMenue.get(0).getRestaurant();
        } else {
            dishesInMenue = Collections.emptyList();
        }

        List<Vote> votes = voteRepository.findByRestaurantIdAndDate(id, date);
        if (!votes.isEmpty() && restaurant == null) {
            restaurant = votes.get(0).getRestaurant();
        } else if (votes.isEmpty()) {
            votes = Collections.emptyList();
        }

        if (restaurant == null) {
            restaurant = checkNotFoundWithId(restaurantRepository.findById((id)).orElse(null), id);
        } else {
            ValidationUtil.checkIdMatch(restaurant, id);
        }

        return EntitiesUtil.getRestaurantWithMenueAndVotes(restaurant, dishesInMenue, votes, id);
    }

    @Cacheable(value = "restaurants", key = "#date")
    public List<Restaurant> getWithDishesInMenueByDate(LocalDate date) {
        List<DishInMenue> dishesInMenue = menueRepository.findAllByDate(date);

        if (dishesInMenue.isEmpty())
            return Collections.emptyList();

        return EntitiesUtil.getRestaurantsWithMenue(dishesInMenue);
    }

    public List<Restaurant> getWithVotesByDate(LocalDate date) {
        List<Vote> votes = voteRepository.findAllByDate(date);
        if (votes.isEmpty())
            return Collections.emptyList();

        return EntitiesUtil.getRestaurantsWithVotes(votes);
    }


    @CacheEvict("restaurants")
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        Integer id = restaurant.getId();
        checkNotFoundWithId(get(id), id);
        restaurantRepository.save(restaurant);
    }


    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must not be null");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict("restaurants")
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }
}
