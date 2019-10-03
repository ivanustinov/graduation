package ru.ustinov.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import ru.ustinov.model.Restaurant;
import ru.ustinov.to.DishInMenueTo;
import ru.ustinov.web.json.JsonUtil;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ustinov.DishesInMenueTestData.VILKA_LOSHKA_MENUE_DISH_ID_1;
import static ru.ustinov.DishesInMenueTestData.getCreatedTo;
import static ru.ustinov.RestaurantTestData.*;
import static ru.ustinov.TestUtil.userHttpBasic;
import static ru.ustinov.UserTestData.ADMIN;
import static ru.ustinov.web.DishInMenueController.REST_URL;
import static ru.ustinov.web.DishInMenueControllerTest.REST_URL_SLASH;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 01.10.2019
 */
public class CacheTest extends AbstractControllerTest {

    @Test
    public void testCache() throws Exception {
        for (int i = 0; i < 2; i++) {
            getRestaurantsWithDishesInMenue(getVilkaLoshkaRestWithMenues_2019_09_11(), getKompotRestWithMenues_2019_09_11());
        }
        deleteDishInMenue();
        getRestaurantsWithDishesInMenue(getVilkaLoshkaRestWithMenuesAfterDeleteDishInMenue_2019_09_11(), getKompotRestWithMenues_2019_09_11());

    }

    public void getRestaurantsWithDishesInMenue( Restaurant...restaurants) throws Exception {
        mockMvc.perform(get(RestaurantController.REST_URL + "/menues?date=2019-09-11")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(getRestaurantsWithDishesInMenuesMatcher(List.of(restaurants)))
                .andDo(print());
    }

    public void deleteDishInMenue() throws Exception {
        mockMvc.perform(delete(REST_URL_SLASH + VILKA_LOSHKA_MENUE_DISH_ID_1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
