package ru.ustinov.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.ustinov.model.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 24.09.2019
 */
public class EntitiesUtil {
    //  Convert list of dishesInMenues to list of restaurants with dishesInMenues
    public static List<Restaurant> getRestaurantsWithMenue(List<DishInMenue> dishesInMenues) {
        Map<Restaurant, List<DishInMenue>> dishMap = new LinkedHashMap<>();
        dishesInMenues.forEach(
                m -> dishMap
                        .computeIfAbsent(m.getRestaurant(), d -> new ArrayList<>())
                        .add(m)
        );
        return dishMap.entrySet()
                .stream()
                .map(e -> new Restaurant(e.getKey(), new LinkedHashSet<>(e.getValue())))
                .collect(Collectors.toList());
    }

    public static Restaurant getRestaurantWithMenue(Restaurant restaurant, List<DishInMenue> dishesInMenues, int restaurantId) {
//      Check that found restaurant id is matching required:
        restaurant = ValidationUtil.checkIdMatch(new Restaurant(restaurant), restaurantId);
        restaurant.setMenues(new LinkedHashSet<>(dishesInMenues));
        return restaurant;
    }

    public static List<Restaurant> getRestaurantsWithVotes(List<Vote> votes) {
        Map<Restaurant, List<Vote>> votesMap = new LinkedHashMap<>();
        votes.forEach(
                m -> votesMap
                        .computeIfAbsent(m.getRestaurant(), d -> new ArrayList<>())
                        .add(m)
        );

        return votesMap.entrySet()
                .stream()
                .map(e -> {
                    Restaurant restaurant = new Restaurant(e.getKey());
                    restaurant.setVotes(new LinkedHashSet<>(e.getValue()));
                    return restaurant;
                })
                .collect(Collectors.toList());
    }


    public static Restaurant getRestaurantWithVotes(Restaurant restaurant, List<Vote> votes, int restaurantId) {
//      Check that found restaurant id is matching required:
        restaurant = ValidationUtil.checkIdMatch(new Restaurant(restaurant), restaurantId);
        restaurant.setVotes(new LinkedHashSet<>(votes));
        return restaurant;
    }

    public static Restaurant getRestaurantWithMenueAndVotes(Restaurant restaurant, List<DishInMenue> dishesInMenues,
                                                            List<Vote> votes, int restaurantId) {
        restaurant = ValidationUtil.checkIdMatch(new Restaurant(restaurant), restaurantId);
        restaurant.setMenues(new LinkedHashSet<>(dishesInMenues));
        restaurant.setVotes(new LinkedHashSet<>(votes));
        return restaurant;
    }

    public static User prepareUserToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
