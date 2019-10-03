package ru.ustinov;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.ustinov.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.ustinov.RestaurantTestData.KOMPOT;
import static ru.ustinov.RestaurantTestData.VILKA_LOSHKA;
import static ru.ustinov.UserTestData.*;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
public class VoteTestData {

    public static final int VOTE_1_ID = 100024;
    public static final int VOTE_2_ID = VOTE_1_ID + 1;
    public static final int VOTE_3_ID = VOTE_1_ID + 2;
    public static final int VOTE_4_ID = VOTE_1_ID + 3;
    public static final int VOTE_5_ID = VOTE_1_ID + 4;

    public static final LocalDate VOTE_DATE_2019_09_11 = LocalDate.of(2019, 9, 11);
    public static final LocalDate VOTE_DATE_2019_09_12 = LocalDate.of(2019, 9, 12);

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, VOTE_DATE_2019_09_11, USER_1, KOMPOT);
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, VOTE_DATE_2019_09_11, USER_2, KOMPOT);

    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, VOTE_DATE_2019_09_12, USER_1, VILKA_LOSHKA);
    public static final Vote VOTE_4 = new Vote(VOTE_4_ID, VOTE_DATE_2019_09_12, USER_2, VILKA_LOSHKA);
    public static final Vote VOTE_5 = new Vote(VOTE_5_ID, VOTE_DATE_2019_09_12, ADMIN, VILKA_LOSHKA);


    public static final List<Vote> ALL_VOTES = List.of(VOTE_1, VOTE_2, VOTE_3, VOTE_4, VOTE_5);
    public static final List<Vote> ALL_VOTES_AFTER_DELETE = List.of(VOTE_2, VOTE_3, VOTE_4, VOTE_5);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static ResultMatcher getVotesMatcher(Iterable<Vote> expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, Vote.class), expected);
    }

    public static ResultMatcher getVoteMatcher(Vote expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Vote.class), expected);
    }
}
