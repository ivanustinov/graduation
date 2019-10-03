package ru.ustinov.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.service.DishInMenueService;
import ru.ustinov.to.DishInMenueTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.ustinov.util.ValidationUtil.assureToIdConsistent;
import static ru.ustinov.util.ValidationUtil.checkNew;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 22.09.2019
 */

@RestController
@RequestMapping(DishInMenueController.REST_URL)
public class DishInMenueController {
    static final String REST_URL = "/menues";
    private final Logger log = LoggerFactory.getLogger(getClass());


    private final DishInMenueService dishInMenueService;

    public DishInMenueController(DishInMenueService dishInMenueService) {
        this.dishInMenueService = dishInMenueService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public List<DishInMenue> getAll() {
        log.info("get all dishInMenuees");
        return dishInMenueService.getAll();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public DishInMenue get(@PathVariable int id) {
        log.info("get dishInMenueTo with id={}", id);
        return dishInMenueService.getById(id);
    }

    @GetMapping(params = "date", produces = APPLICATION_JSON_VALUE)
    public List<DishInMenue> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get dishInMenuees by date {}", date);
        return dishInMenueService.getAllByDate(date);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void delete(@PathVariable int id) {
        log.info("delete dishInMenueTo {}", id);
        dishInMenueService.delete(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<DishInMenue> createWithNewUri(@Valid @RequestBody DishInMenueTo dishInMenueTo) {
        log.info("create {}", dishInMenueTo);
        checkNew(dishInMenueTo);
        DishInMenue created = dishInMenueService.create(dishInMenueTo);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public void update(@Valid @RequestBody DishInMenueTo dishInMenueTo, @PathVariable Integer id) {
        log.info("update {} with id={}", dishInMenueTo, id);
        assureToIdConsistent(dishInMenueTo, id);
        dishInMenueService.update(dishInMenueTo);
    }
}
