package ru.ustinov.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.ustinov.model.Restaurant;
import ru.ustinov.service.RestaurantService;
import ru.ustinov.web.json.JsonUtil;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ustinov.RestaurantTestData.*;
import static ru.ustinov.TestUtil.readFromJsonResultActions;
import static ru.ustinov.TestUtil.userHttpBasic;
import static ru.ustinov.UserTestData.ADMIN;
import static ru.ustinov.UserTestData.USER_1;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL;
    private final String REST_URL_SLASH = RestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantService service;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsMatcher(List.of(VILKA_LOSHKA, KOMPOT)));
    }

    @Test
    void testGetAllWithMenueDishes() throws Exception {
        mockMvc.perform(get(REST_URL + "/menues")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithDishesInMenuesMatcher(getAllRestaurantsWithMenues()))
                .andDo(print());
    }

    @Test
    void testGetAllWithVotes() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + "votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithVotesMatcher(getAllRestaurantsWithVotes()))
                .andDo(print());
    }

    @Test
    void getAllWithDishesInMenueAndVotes() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + "menues-and-votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithDishesInMenuesAndVotesMatcher(getAllRestaurantsWithMenuesAndVotes()))
                .andDo(print());
    }

    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + KOMPOT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(KOMPOT))
                .andDo(print());
    }

    @Test
    void testGetByIdWithAllMenues() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + VILKA_LOSHKA_ID + "/menues")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithDishesInMenue(getVilkaLoshkaRestWithMenues()))
                .andDo(print());
    }

    @Test
    void testGetByIdWithAllVotes() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + KOMPOT_ID + "/votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithVotes(getKompotRestWithVotes()))
                .andDo(print());
    }

    @Test
    void testGetByIdWithMenuesAndVotes() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + KOMPOT_ID + "/menues-and-votes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithDishesInMenueAndVotes(getKompotRestWithMenuesAndVotes()))
                .andDo(print());
    }

    @Test
    void testGetHaveMenuByDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + "haveMenue?date=2019-09-12")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsMatcher(List.of(VILKA_LOSHKA)))
                .andDo(print());
    }



    @Test
    void testGetAllWithVotesByDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + "votes?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithVotesMatcher(List.of(getKompotRestWithVotes_2019_09_11())))
                .andDo(print());
    }

    @Test
    void testGetWithMenueByIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + VILKA_LOSHKA_ID + "/menues?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithDishesInMenue(getVilkaLoshkaRestWithMenues_2019_09_11()))
                .andDo(print());
    }

    @Test
    void testGetWithVotesByIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + VILKA_LOSHKA_ID + "/votes?date=2019-09-12")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithVotes(getVilkaLoshkaRestWithMenuesAndVotes_2019_09_12()))
                .andDo(print());
    }

    @Test
    void testGetWithMenueAndVotesByIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + VILKA_LOSHKA_ID + "/menues-and-votes?date=2019-09-12")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantMatcherWithDishesInMenueAndVotes(getVilkaLoshkaRestWithMenuesAndVotes_2019_09_12()))
                .andDo(print());
    }

    @Test
    void testGetWithVotesCountByIdAndDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + KOMPOT_ID + "/votes-count?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantToMatcher(KOMPOT_TO))
                .andDo(print());
    }

    @Test
    void testGetWithVotesCountByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes-count?date=2019-09-11")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsToMatcher(List.of(KOMPOT_TO, VILKA_LOSHKA_TO)))
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL_SLASH + KOMPOT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(service.getAll(), List.of(VILKA_LOSHKA));
    }

    @Test
    void testCreate() throws Exception {
        Restaurant created = getCreated();
        ResultActions result = mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJsonResultActions(result, Restaurant.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(), List.of(created, VILKA_LOSHKA, KOMPOT));
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = getUpdated();
        mockMvc.perform(put(REST_URL_SLASH + VILKA_LOSHKA_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());
        assertMatch(service.get(VILKA_LOSHKA_ID), updated);
    }


    @Test
    void testGetAllWithMenueByDate() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + "menues?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithDishesInMenuesMatcher(
                        List.of(getVilkaLoshkaRestWithMenues_2019_09_11(), getKompotRestWithMenues_2019_09_11())))
                .andDo(print());
    }

    @Test
    void testCreateWithException() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content("{\"a\":null, \"b\":\"super\"}")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL_SLASH + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

}