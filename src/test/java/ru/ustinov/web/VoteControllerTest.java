package ru.ustinov.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Vote;
import ru.ustinov.service.VoteService;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ustinov.RestaurantTestData.VILKA_LOSHKA;
import static ru.ustinov.TestUtil.readFromJsonResultActions;
import static ru.ustinov.TestUtil.userHttpBasic;
import static ru.ustinov.UserTestData.ADMIN;
import static ru.ustinov.UserTestData.USER_1;
import static ru.ustinov.VoteTestData.*;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 27.09.2019
 */
class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService service;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getVotesMatcher(ALL_VOTES))
                .andDo(print());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/votes/100024")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_1))
                .andDo(print());
    }

    @Test
    void testGetProfileVoteByDate() throws Exception {
        mockMvc.perform(get("/profile/vote?date=2019-09-11")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_1))
                .andDo(print());
    }

    @Test
    void testGetVoteByUserIdAndDate() throws Exception {
        mockMvc.perform(get("/users/100000/vote?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_1))
                .andDo(print());
    }


    //This test does not pass if the voting happen's after 11 o'clock.

//    @Test
//    void vote() throws Exception {
//        Vote created = new Vote(LocalDate.now(), USER_1, VILKA_LOSHKA);
//        ResultActions result = mockMvc.perform(put("/profile/vote?restaurantId=100003")
//                .with(userHttpBasic(USER_1)))
//                .andExpect(status().isCreated());
//
//        Vote returned = readFromJsonResultActions(result, Vote.class);
//        created.setId(returned.getId());
//
//        assertMatch(returned, created);
//        assertMatch(service.getAll(), VOTE_1, VOTE_2, VOTE_3, VOTE_4, VOTE_5, created);
//    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/votes/100024")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(), ALL_VOTES_AFTER_DELETE);
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get("/votes/1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete("/votes/1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void testWrongRole() throws Exception {
        mockMvc.perform(get("/votes")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isInternalServerError());
    }
}
