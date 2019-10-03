package ru.ustinov.to;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 23.09.2019
 */
public class DishInMenueTo extends AbstractTo {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    private Integer restaurantId;

    @NotNull
    private Integer dishId;

    public DishInMenueTo() {
    }

    public DishInMenueTo(LocalDate date) {
        this.date = date;
    }

    public DishInMenueTo(Integer id, LocalDate date, Integer restaurantId, Integer dishId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    @Override
    public String toString() {
        return "DishInMenueTo{" +
                "date=" + date +
                ", restaurantId=" + restaurantId +
                ", dishId=" + dishId +
                '}';
    }
}
