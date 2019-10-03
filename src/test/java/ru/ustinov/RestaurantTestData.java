package ru.ustinov;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.model.Restaurant;
import ru.ustinov.model.Vote;
import ru.ustinov.to.RestaurantVoteCountTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustinov.DishesInMenueTestData.*;
import static ru.ustinov.VoteTestData.*;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
public class RestaurantTestData {
    public static final Integer VILKA_LOSHKA_ID = 100003;
    public static final Integer KOMPOT_ID = VILKA_LOSHKA_ID + 1;

    public static final Restaurant VILKA_LOSHKA = new Restaurant(VILKA_LOSHKA_ID, "Вилка-Ложка", "Кузнецова 2");

    public static final Restaurant KOMPOT = new Restaurant(KOMPOT_ID, "Компот", "Старых Большевиков 3");

    public static final RestaurantVoteCountTo VILKA_LOSHKA_TO = new RestaurantVoteCountTo(LocalDate.of(2019, 9, 11), VILKA_LOSHKA, 0L);

    public static final RestaurantVoteCountTo KOMPOT_TO = new RestaurantVoteCountTo(LocalDate.of(2019, 9, 11), KOMPOT, 2L);


    public static Restaurant getCreated() {
        return new Restaurant("New restaurant", "New address");
    }


    public static Restaurant getUpdated() {
        return new Restaurant(VILKA_LOSHKA_ID, "Updated restaurant", "Updated address");
    }
    public static Restaurant getRestaurantWithVotesByDate(Restaurant restaurant, Set<Vote> votes) {
        var restaurantWithVote = new Restaurant(restaurant);
        restaurantWithVote.setVotes(votes);
        return restaurantWithVote;
    }

    public static Restaurant getRestaurantWithDishesInMenueByDate(Restaurant restaurant, Set<DishInMenue> dishesInMenue) {
        var restaurantWithDishes = new Restaurant(restaurant);
        restaurantWithDishes.setMenues(dishesInMenue);
        return restaurantWithDishes;
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishesInMenue", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishesInMenue", "votes").isEqualTo(expected);
    }

    public static void assertMatchWithMenues(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes");
    }

    public static void assertMatchWithMenues(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes").isEqualTo(expected);
    }

    public static void assertMatchWithVotes(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishesInMenue");
    }

    public static void assertMatchWithVotes(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishesInMenue").isEqualTo(expected);
    }

    public static void assertMatchWithMunueAndVotes(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatchWithMunueAndVotes(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


    private static void assertMatchTo(RestaurantVoteCountTo actual, RestaurantVoteCountTo expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private static void assertMatchTo(Iterable<RestaurantVoteCountTo> actual, Iterable<RestaurantVoteCountTo> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher getRestaurantsMatcher(Iterable<Restaurant> expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantsWithDishesInMenuesMatcher(Iterable<Restaurant> expected) {
        return result -> assertMatchWithMenues(TestUtil.readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantsWithDishesInMenuesAndVotesMatcher(Iterable<Restaurant> expected) {
        return result -> assertMatchWithMunueAndVotes(TestUtil.readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantsWithVotesMatcher(Iterable<Restaurant> expected) {
        return result -> assertMatchWithVotes(TestUtil.readListFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantMatcherWithDishesInMenue(Restaurant expected) {
        return result -> assertMatchWithMenues(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantMatcherWithVotes(Restaurant expected) {
        return result -> assertMatchWithVotes(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantMatcherWithDishesInMenueAndVotes(Restaurant expected) {
        return result -> assertMatchWithMunueAndVotes(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }


    public static ResultMatcher getRestaurantsToMatcher(Iterable<RestaurantVoteCountTo> expected) {
        return result -> assertMatchTo(TestUtil.readListFromJsonMvcResult(result, RestaurantVoteCountTo.class), expected);
    }

    public static ResultMatcher getRestaurantToMatcher(RestaurantVoteCountTo expected) {
        return result -> assertMatchTo(TestUtil.readFromJsonMvcResult(result, RestaurantVoteCountTo.class), expected);
    }


    public static Restaurant getVilkaLoshkaRestWithMenues() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setMenues(VILKA_LOSHKA_ALL_MENUE);
        return vilkaLoshka;
    }

    public static Restaurant getCompotRestWithMenues() {
        Restaurant kompot = new Restaurant(KOMPOT);
        kompot.setMenues(KOMPOT_ALL_MENUE);
        return kompot;
    }

    public static List<Restaurant> getAllRestaurantsWithMenues() {
        return List.of(getVilkaLoshkaRestWithMenues(), getCompotRestWithMenues());
    }

    public static Restaurant getVilkaLoshkaRestWithVotes() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setVotes(Set.of(VOTE_3, VOTE_4, VOTE_5));
        return vilkaLoshka;
    }

    public static Restaurant getKompotRestWithVotes() {
        Restaurant kompot = new Restaurant(KOMPOT);
        kompot.setVotes(Set.of(VOTE_1, VOTE_2));
        return kompot;
    }


    public static List<Restaurant> getAllRestaurantsWithVotes() {
        return List.of(getVilkaLoshkaRestWithVotes(), getKompotRestWithVotes());
    }

    public static Restaurant getVilkaLoshkaRestWithMenuesAndVotes() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setMenues(VILKA_LOSHKA_ALL_MENUE);
        vilkaLoshka.setVotes(Set.of(VOTE_3, VOTE_4, VOTE_5));
        return vilkaLoshka;
    }

    public static Restaurant getKompotRestWithMenuesAndVotes() {
        Restaurant compot = new Restaurant(KOMPOT);
        compot.setMenues(KOMPOT_ALL_MENUE);
        compot.setVotes(Set.of(VOTE_1, VOTE_2));
        return compot;
    }


    public static List<Restaurant> getAllRestaurantsWithMenuesAndVotes() {
        return List.of(getVilkaLoshkaRestWithMenuesAndVotes(), getKompotRestWithMenuesAndVotes());
    }

    public static Restaurant getVilkaLoshkaRestWithMenues_2019_09_11() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setMenues(Set.of(
                VILKA_LOSHKA_BLINCHIKY_2019_09_11,
                VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
                VILKA_LOSHKA_SIBAS_2019_09_11));
        return vilkaLoshka;
    }

    public static Restaurant getVilkaLoshkaRestWithMenuesAfterDeleteDishInMenue_2019_09_11() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setMenues(Set.of(
                VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
                VILKA_LOSHKA_SIBAS_2019_09_11));
        return vilkaLoshka;
    }

    public static Restaurant getKompotRestWithMenues_2019_09_11() {
        Restaurant vilkaLoshka = new Restaurant(KOMPOT);
        vilkaLoshka.setMenues(Set.of(
                KOMPOT_PURE_2019_09_11,
                KOMPOT_SOLIANKA_2019_09_11,
                KOMPOT_VEGETABLES_2019_09_11));
        return vilkaLoshka;
    }

    public static Restaurant getVilkaLoshkaRestWithVotes_2019_09_11() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setVotes(Set.of());
        return vilkaLoshka;
    }

    public static Restaurant getKompotRestWithVotes_2019_09_11() {
        Restaurant kompot = new Restaurant(KOMPOT);
        kompot.setVotes(Set.of(VOTE_1, VOTE_2));
        return kompot;
    }

    public static Restaurant getVilkaLoshkaRestWithVotes_2019_09_12() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setVotes(Set.of(VOTE_3, VOTE_4, VOTE_5));
        return vilkaLoshka;
    }

    public static Restaurant getVilkaLoshkaRestWithMenuesAndVotes_2019_09_12() {
        Restaurant vilkaLoshka = new Restaurant(VILKA_LOSHKA);
        vilkaLoshka.setMenues(Set.of(
                VILKA_LOSHKA_MAKARONY_2019_09_12,
                VILKA_LOSHKA_KOMPOTT_2019_09_12,
                VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12));
        vilkaLoshka.setVotes(Set.of(VOTE_3, VOTE_4, VOTE_5));
        return vilkaLoshka;
    }
}
