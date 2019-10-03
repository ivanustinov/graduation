package ru.ustinov.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 09.09.2019
 */
@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {


    @Column(name = "price")
    @NotNull
    private Integer price;

    @Column(name = "actual")
    @NotNull
    private Boolean actual = true;

    public Dish() {
    }

    public Dish(@NotNull String name, @NotNull Integer price) {
        super(name);
        this.price = price;
    }

    public Dish(@NotNull Integer id, @NotNull String name, @NotNull Integer price) {
        super(id, name);
        this.price = price;
        this.actual = true;
    }

    public Dish(@NotNull Integer id, @NotNull String name, @NotNull Integer price, Boolean actual) {
        this(id, name, price);
        this.actual = actual;
    }


    public float getPrice() {
        return Float.valueOf(price) / 100;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", price=" + price +
                ", actual=" + actual +
                "}";
    }
}
