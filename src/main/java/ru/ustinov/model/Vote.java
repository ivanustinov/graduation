package ru.ustinov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @since 09.09.2019
 */
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"DATE", "USER_ID"},
        name = "date_user_id")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "DATE")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    @NotNull
//    @JsonIgnore
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    @NotNull
    @JsonIgnoreProperties("votes")
    private Restaurant restaurant;


    public Vote() {
    }

    public Vote(Integer id, @NotNull LocalDate date, @NotNull User user, @NotNull Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(@NotNull Vote vote) {
        this(vote.getId(), vote.getDate(), vote.getUser(), vote.getRestaurant());
    }

    public Vote(LocalDate date, User user, Restaurant restaurant) {
        this(null, date, user, restaurant);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
