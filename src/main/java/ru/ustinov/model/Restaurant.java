package ru.ustinov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 09.09.2019
 */
@Entity
@Table(name = "RESTAURANTS", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "ADDRESS"},
        name = "restaurant_name_adress")})
public class Restaurant extends AbstractNamedEntity {


    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnoreProperties(value = "restaurant", allowSetters = true)
    private Set<DishInMenue> dishesInMenue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnoreProperties(value = "restaurant", allowSetters = true)
    private Set<Vote> votes;


    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        super(name);
        this.address = address;
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
    }

    public Restaurant(Restaurant restaurant, Set<DishInMenue> dishesInMenue) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
        setMenues(dishesInMenue);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<DishInMenue> getDishesInMenue() {
        return dishesInMenue;
    }

    public void setMenues(Set<DishInMenue> dishesInMenues) {
        if (dishesInMenues.isEmpty())
            this.dishesInMenue = Collections.emptySet();
        this.dishesInMenue = dishesInMenues;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        if (votes.isEmpty())
            this.votes = Collections.emptySet();
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
//                ", dishes=" + dishesInMenue +
//                ", votes=" + votes.size() +
                '}';
    }
}
