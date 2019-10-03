package ru.ustinov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 13.09.2019
 */
@Entity
@Table(name = "dishes_in_menue")
public class DishInMenue extends AbstractBaseEntity {

    @Column(name = "DATE")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dish_id")
    @NotNull
    private Dish dish;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @JsonIgnoreProperties("dishesInMenue")
    private Restaurant restaurant;

    public DishInMenue() {
    }

    public DishInMenue(@NotNull LocalDate date, @NotNull Dish dish, @NotNull Restaurant restaurant) {
        this.date = date;
        this.dish = dish;
        this.restaurant = restaurant;

    }

    public DishInMenue(@NotNull Integer id, @NotNull LocalDate date, @NotNull Dish dish, @NotNull Restaurant restaurant) {
        super(id);
        this.date = date;
        this.dish = dish;
        this.restaurant = restaurant;

    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "DishInMenue{id=" + id +
                ", date=" + date +
                ", " + dish +
                "}";
    }
}
