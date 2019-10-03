package ru.ustinov;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ustinov.model.Dish;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
public class DishTestData {
    public static final int DISH_1_ID = 100005;
    public static final int DISH_2_ID = DISH_1_ID + 1;
    public static final int DISH_3_ID = DISH_1_ID + 2;
    public static final int DISH_4_ID = DISH_1_ID + 3;
    public static final int DISH_5_ID = DISH_1_ID + 4;
    public static final int DISH_6_ID = DISH_1_ID + 5;
    public static final int DISH_7_ID = DISH_1_ID + 6;
    public static final int DISH_8_ID = DISH_1_ID + 7;
    public static final int DISH_9_ID = DISH_1_ID + 8;
    public static final int DISH_10_ID = DISH_1_ID + 9;


    public static final Dish BLINCHICKY = new Dish(DISH_1_ID, "Блинчики", 5000);
    public static final Dish CHICKEN_WITH_POTATOW = new Dish(DISH_2_ID, "Курица с картофелем", 20000);
    public static final Dish SIBAS = new Dish(DISH_3_ID, "Сибас", 25000);
    public static final Dish PURE = new Dish(DISH_4_ID, "Пюре", 10000);
    public static final Dish SOLIANKA = new Dish(DISH_5_ID, "Солянка", 20000);
    public static final Dish VEGETABLES = new Dish(DISH_6_ID, "Овощи на гриле", 18000);
    public static final Dish MAKARONY = new Dish(DISH_7_ID, "Макароны с котлетой", 25000);
    public static final Dish HARCHO_HISTORY = new Dish(DISH_8_ID, "Суп Харчо", 20000);
    public static final Dish KOMPOTT = new Dish(DISH_9_ID, "Компот", 4000);
    public static final Dish HARCHO_ACTUAL = new Dish(DISH_10_ID, "Суп Харчо", 25000);
    public static final Dish HARCHO_NO_ACTUAL = new Dish(DISH_10_ID, "Суп Харчо", 25000);

    static {
        HARCHO_HISTORY.setActual(false);
    }

    public static final List<Dish> ALL_DISHES = List.of(
            BLINCHICKY, CHICKEN_WITH_POTATOW, SIBAS,
            PURE, SOLIANKA, VEGETABLES,
            MAKARONY, HARCHO_HISTORY,
            KOMPOTT, HARCHO_ACTUAL);

    public static final List<Dish> ALL_DISHES_ACTUAL_ORDERED_BY_NAME = List.of(
            BLINCHICKY, KOMPOTT, CHICKEN_WITH_POTATOW,
            MAKARONY, VEGETABLES, PURE, SIBAS,
            SOLIANKA, HARCHO_ACTUAL);

    public static final List<Dish> ALL_DISHES_AFTER_DELETE = List.of(
            CHICKEN_WITH_POTATOW, SIBAS,
            PURE, SOLIANKA, VEGETABLES,
            MAKARONY, HARCHO_HISTORY,
            KOMPOTT, HARCHO_ACTUAL);


    public static Dish getCreated() {
        HARCHO_NO_ACTUAL.setActual(false);
        return new Dish(null, "Суп Харчо", 28000);
    }

    public static Dish getUpdated() {
        return new Dish(DISH_1_ID, "Updated dish", 8000);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher getDishesMatcher(Iterable<Dish> expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, Dish.class), expected);
    }

    public static ResultMatcher getDishMatcher(Dish expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Dish.class), expected);
    }

}
