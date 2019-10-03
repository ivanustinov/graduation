package ru.ustinov;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.to.DishInMenueTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustinov.DishTestData.*;
import static ru.ustinov.RestaurantTestData.KOMPOT;
import static ru.ustinov.RestaurantTestData.VILKA_LOSHKA;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
public class DishesInMenueTestData {

    public static final LocalDate DATE_2019_09_11 = LocalDate.of(2019, 9, 11);

    public static final LocalDate DATE_2019_09_12 = LocalDate.of(2019, 9, 12);

    public static final int VILKA_LOSHKA_MENUE_DISH_ID_1 = 100015;
    public static final int VILKA_LOSHKA_MENUE_DISH_ID_2 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 1;
    public static final int VILKA_LOSHKA_MENUE_DISH_ID_3 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 2;
    public static final int KOMPOT_MENUE_DISH_ID_1 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 3;
    public static final int KOMPOT_MENUE_DISH_ID_2 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 4;
    public static final int KOMPOT_MENUE_DISH_ID_3 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 5;
    public static final int VILKA_LOSHKA_MENUE_DISH_ID_4 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 6;
    public static final int VILKA_LOSHKA_MENUE_DISH_ID_5 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 7;
    public static final int VILKA_LOSHKA_MENUE_DISH_ID_6 = VILKA_LOSHKA_MENUE_DISH_ID_1 + 8;

    
    public static final DishInMenue CREATED = new DishInMenue(100029, LocalDate.of(2019, 1, 1), BLINCHICKY, VILKA_LOSHKA);
    public static final DishInMenue UPDATED = new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_1, LocalDate.of(2019, 1, 1), BLINCHICKY, VILKA_LOSHKA);



    public static DishInMenueTo getUpdatedTo() {
        return new DishInMenueTo(VILKA_LOSHKA_MENUE_DISH_ID_1, LocalDate.of(2019, 1, 1), 100003, 100005);
    }

    public static DishInMenueTo getCreatedTo() {
        return new DishInMenueTo(null, LocalDate.of(2019, 1, 1), 100003, 100005);
    }


    public static final DishInMenue VILKA_LOSHKA_BLINCHIKY_2019_09_11 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_1, DATE_2019_09_11, BLINCHICKY, VILKA_LOSHKA);

    public static final DishInMenue VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_2, DATE_2019_09_11, CHICKEN_WITH_POTATOW, VILKA_LOSHKA);

    public static final DishInMenue VILKA_LOSHKA_SIBAS_2019_09_11 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_3, DATE_2019_09_11, SIBAS, VILKA_LOSHKA);

    public static final DishInMenue KOMPOT_PURE_2019_09_11 =
            new DishInMenue(KOMPOT_MENUE_DISH_ID_1, DATE_2019_09_11, PURE, KOMPOT);

    public static final DishInMenue KOMPOT_SOLIANKA_2019_09_11 =
            new DishInMenue(KOMPOT_MENUE_DISH_ID_2, DATE_2019_09_11, SOLIANKA, KOMPOT);

    public static final DishInMenue KOMPOT_VEGETABLES_2019_09_11 =
            new DishInMenue(KOMPOT_MENUE_DISH_ID_3, DATE_2019_09_11, VEGETABLES, KOMPOT);

    public static final DishInMenue VILKA_LOSHKA_MAKARONY_2019_09_12 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_4, DATE_2019_09_12, MAKARONY, VILKA_LOSHKA);

    public static final DishInMenue VILKA_LOSHKA_KOMPOTT_2019_09_12 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_5, DATE_2019_09_12, KOMPOTT, VILKA_LOSHKA);

    public static final DishInMenue VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12 =
            new DishInMenue(VILKA_LOSHKA_MENUE_DISH_ID_6, DATE_2019_09_12, HARCHO_ACTUAL, VILKA_LOSHKA);


    public static final List<DishInMenue> ALL_MENUE = List.of(
            VILKA_LOSHKA_BLINCHIKY_2019_09_11,
            VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
            VILKA_LOSHKA_SIBAS_2019_09_11,
            KOMPOT_PURE_2019_09_11,
            KOMPOT_SOLIANKA_2019_09_11,
            KOMPOT_VEGETABLES_2019_09_11,
            VILKA_LOSHKA_MAKARONY_2019_09_12,
            VILKA_LOSHKA_KOMPOTT_2019_09_12,
            VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12);

    public static final List<DishInMenue> ALL_MENUE_AFTER_DELETE = List.of(
            VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
            VILKA_LOSHKA_SIBAS_2019_09_11,
            KOMPOT_PURE_2019_09_11,
            KOMPOT_SOLIANKA_2019_09_11,
            KOMPOT_VEGETABLES_2019_09_11,
            VILKA_LOSHKA_MAKARONY_2019_09_12,
            VILKA_LOSHKA_KOMPOTT_2019_09_12,
            VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12);


    public static final Set<DishInMenue> VILKA_LOSHKA_ALL_MENUE = Set.of(
            VILKA_LOSHKA_BLINCHIKY_2019_09_11,
            VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
            VILKA_LOSHKA_SIBAS_2019_09_11,
            VILKA_LOSHKA_MAKARONY_2019_09_12,
            VILKA_LOSHKA_KOMPOTT_2019_09_12,
            VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12);

    public static final Set<DishInMenue> KOMPOT_ALL_MENUE = Set.of(
            KOMPOT_PURE_2019_09_11,
            KOMPOT_SOLIANKA_2019_09_11,
            KOMPOT_VEGETABLES_2019_09_11);

    public static void assertMatch(DishInMenue actual, DishInMenue expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<DishInMenue> actual, Iterable<DishInMenue> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher getDishesInMenueMatcher(Iterable<DishInMenue> expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, DishInMenue.class), expected);
    }

    public static ResultMatcher getDishInMenueMatcher(DishInMenue expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, DishInMenue.class), expected);
    }

}
