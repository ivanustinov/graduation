package ru.ustinov.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.model.Restaurant;
import ru.ustinov.service.DishInMenueService;
import ru.ustinov.to.DishInMenueTo;
import ru.ustinov.web.json.JsonUtil;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ustinov.DishesInMenueTestData.*;
import static ru.ustinov.TestUtil.readFromJsonResultActions;
import static ru.ustinov.TestUtil.userHttpBasic;
import static ru.ustinov.UserTestData.ADMIN;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 26.09.2019
 */
class DishInMenueControllerTest extends AbstractControllerTest {


     static final String REST_URL = DishInMenueController.REST_URL;
     static final String REST_URL_SLASH = DishInMenueController.REST_URL + "/";

    @Autowired
    DishInMenueService service;

    @Test
    void testCreate() throws Exception {
        DishInMenueTo createdTo = getCreatedTo();
        ResultActions result = mockMvc.perform(post(REST_URL)
                .content(JsonUtil.writeValue(createdTo))
                .contentType(APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andDo(print());
        DishInMenue returned = readFromJsonResultActions(result, DishInMenue.class);
        DishInMenue created = service.createNewFromTo(createdTo);
        created.setId(returned.getId());
        assertMatch(created, returned);
        assertMatch(service.getAll(), List.of(VILKA_LOSHKA_BLINCHIKY_2019_09_11,
                VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
                VILKA_LOSHKA_SIBAS_2019_09_11,
                KOMPOT_PURE_2019_09_11,
                KOMPOT_SOLIANKA_2019_09_11,
                KOMPOT_VEGETABLES_2019_09_11,
                VILKA_LOSHKA_MAKARONY_2019_09_12,
                VILKA_LOSHKA_KOMPOTT_2019_09_12,
                VILKA_LOSHKA_HARCHO_ACTUAL_2019_09_12, created));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getDishesInMenueMatcher(ALL_MENUE));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + VILKA_LOSHKA_MENUE_DISH_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getDishInMenueMatcher(VILKA_LOSHKA_BLINCHIKY_2019_09_11));
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getDishesInMenueMatcher(List.of(
                        VILKA_LOSHKA_BLINCHIKY_2019_09_11,
                        VILKA_LOSHKA_CHICKEN_WITH_POTATOW_2019_09_11,
                        VILKA_LOSHKA_SIBAS_2019_09_11,
                        KOMPOT_PURE_2019_09_11,
                        KOMPOT_SOLIANKA_2019_09_11,
                        KOMPOT_VEGETABLES_2019_09_11)));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL_SLASH + VILKA_LOSHKA_MENUE_DISH_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(), ALL_MENUE_AFTER_DELETE);
    }

    @Test
    void testUpdate() throws Exception {
        DishInMenueTo updated = getUpdatedTo();
        mockMvc.perform(put(REST_URL_SLASH + VILKA_LOSHKA_MENUE_DISH_ID_1)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)));
        assertMatch(service.getById(VILKA_LOSHKA_MENUE_DISH_ID_1), UPDATED);
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + 9999)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(get(REST_URL_SLASH + 9999)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

}