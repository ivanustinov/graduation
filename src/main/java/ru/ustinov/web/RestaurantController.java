package ru.ustinov.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ustinov.model.Restaurant;
import ru.ustinov.service.RestaurantService;
import ru.ustinov.to.RestaurantVoteCountTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.ustinov.util.ValidationUtil.assureEntityIdConsistent;
import static ru.ustinov.util.ValidationUtil.checkNew;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
@RestController
@RequestMapping(RestaurantController.REST_URL)
//@CrossOrigin("*")
public class RestaurantController {

    static final String REST_URL = "/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/votes", produces = APPLICATION_JSON_VALUE)
//    @JsonView(View.JsonRestaurantsWithVote.class)
    @Secured("ROLE_ADMIN")
    public List<Restaurant> getAllWithVotes() {
        log.info("get all restaurants with votes");
        return restaurantService.getAllWithVotes();
    }

    @GetMapping(value = "/menues", produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishesInMenue() {
        log.info("get all restaurants with dishesInMenue");
        return restaurantService.getAllWithDishesInMenue();
    }

    @GetMapping(value = "/menues",  params = "date", produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishesInMenueByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurants with dishesInMenue by date {}", date);
        return restaurantService.getWithDishesInMenueByDate(date);
    }

    @GetMapping(value = "/votes", params = "date", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public List<Restaurant> getAllWithVotesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get all restaurants with votes in certain date");
        return restaurantService.getWithVotesByDate(date);
    }

    @GetMapping(value = "/menues-and-votes")
    public List<Restaurant> getAllWithMenuDishesAndVotes() {
        log.info("get all restaurants with dishesInMenue and votes");
        return restaurantService.getAllWithDishesInMenueAndVotes();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Restaurant getById(@PathVariable Integer id) {
        log.info("get restaurant with id={}", id);
        return restaurantService.get(id);
    }

    @GetMapping(value = "/{id}/menues", produces = APPLICATION_JSON_VALUE)
    public Restaurant getByIdWithAllMenues(@PathVariable Integer id) {
        log.info("get restaurant with id={} with dishesInMenue", id);
        return restaurantService.getWithDishesInMenueById(id);
    }

    @GetMapping(value = "/{id}/votes", produces = APPLICATION_JSON_VALUE)
    public Restaurant getByIdWithAllVotes(@PathVariable Integer id) {
        log.info("get restaurant with id={} with votes", id);
        return restaurantService.getWithVotesById(id);
    }

    @GetMapping(value = "/{id}/menues-and-votes", produces = APPLICATION_JSON_VALUE)
    public Restaurant getByIdWithMenuesAndVotes(@PathVariable Integer id) {
        log.info("get restaurant with id={} with menues and votes", id);
        return restaurantService.getWithDishesInMenueAndVotesById(id);
    }

    //Get restaurants (without dishesInMenue) that have menue for date
    @GetMapping(value = "/haveMenue", params = "date", produces = APPLICATION_JSON_VALUE)
    public List<Restaurant> getHaveMenueByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurants that have menue by date {}", date);
        return restaurantService.getByMenueDate(date);
    }



    @GetMapping(value = "/{id}/menues", params = "date", produces = APPLICATION_JSON_VALUE)
    public Restaurant getWithMenueByIdAndDate(@PathVariable("id") Integer id,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        log.info("get restaurant with id={} with dishesInMenue by date {}", id, date);
        return restaurantService.getWithDishesInMenueByIdAndDate(id, date);
    }

    @GetMapping(value = "/{id}/votes", params = "date", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public Restaurant getWithVotesByIdAndDate(@PathVariable Integer id,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        log.info("get restaurant with id={} with votes by date {}", id, date);
        return restaurantService.getWithVotesByIdAndDate(id, date);
    }

    @GetMapping(value = "/{id}/menues-and-votes", params = "date", produces = APPLICATION_JSON_VALUE)
//    @JsonView(View.JsonRestaurantsWithMenuAndVotes.class)
    @Secured("ROLE_ADMIN")
    public Restaurant getWithMenueAndVotesByIdAndDate(@PathVariable Integer id,
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        log.info("get restaurant with id={} with dishesInMenue and votes by date {}", id, date);
        return restaurantService.getWithDishesInMenueAndVotesByIdAndDate(id, date);
    }

    @GetMapping(value = "/{id}/votes-count", params = "date", produces = APPLICATION_JSON_VALUE)
    public RestaurantVoteCountTo getWithVotesCountByIdAndDate(@PathVariable Integer id,
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        log.info("get restaurant with id={} with votes count by date {}", id, date);
        return restaurantService.getWithVotesCountByIdAndDate(id, date);
    }

    @GetMapping(value = "/votes-count", params = "date", produces = APPLICATION_JSON_VALUE)
    public List<RestaurantVoteCountTo> getWithVotesCountByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("date") LocalDate date) {
        log.info("get restaurants with votes count by date {}", date);
        return restaurantService.getWithVotesCountByDate(date);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Restaurant> createWithNewUri(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable Integer id) {
        log.info("update {} with id={}", restaurant, id);
        assureEntityIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

}
