package ru.ustinov.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ustinov.model.Dish;
import ru.ustinov.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.ustinov.util.ValidationUtil.assureEntityIdConsistent;
import static ru.ustinov.util.ValidationUtil.checkNew;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 25.09.2019
 */

@RestController
@RequestMapping(DishController.REST_URL)
@Secured("ROLE_ADMIN")
public class DishController {
    static final String REST_URL = "/dishes";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService service;

    @Autowired
    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping(value = "alldishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        log.info("get all dishes");
        return service.getAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllActualDishes() {
        log.info("get all dishes with actual price");
        return service.getAllActualDishes();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable Integer id) {
        log.info("get dish with id={}", id);
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete dish with id={}", id);
        service.delete(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithNewUri(@Valid @RequestBody Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = service.create(dish);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable Integer id) {
        log.info("update {} with id={}", dish, id);
        assureEntityIdConsistent(dish, id);
        service.update(dish);
    }
}
