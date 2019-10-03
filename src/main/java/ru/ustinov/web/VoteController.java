package ru.ustinov.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ustinov.AuthorizedUser;
import ru.ustinov.model.Vote;
import ru.ustinov.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 25.09.2019
 */
@RestController
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/votes", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public List<Vote> getAll() {
        log.info("get all votes");
        return service.getAll();
    }

    @GetMapping(value = "/votes/{id}", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public Vote get(@PathVariable Integer id) {
        log.info("get vote with id={}", id);
        return service.get(id);
    }

    @GetMapping(value = "/profile/vote", params = "date", produces = APPLICATION_JSON_VALUE)
    public Vote getProfileVoteByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get profile vote by date {}", date);
        return service.getByUserAndDate(authUser.getId(), date);
    }

    @GetMapping(value = "/users/{userId}/vote", params = "date", produces = APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public Vote getVoteByUserIdAndDate(@PathVariable Integer userId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get vote by user {} and date {}", userId, date);
        return service.getByUserAndDate(userId, date);
    }

    @GetMapping(value = "/votes/count", params = {"id", "date"}, produces = APPLICATION_JSON_VALUE)
    public Long getVotesCountByRestaurantIdAndDate(@RequestParam Integer id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get votes count by restaurant {} and date {}", id, date);
        return service.countAllByRestaurantIdAndDate(id, date);
    }

    //  Vote for restaurant by authorized user
    @PutMapping(value = "/profile/vote", params = "restaurantId", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@Param("restaurantId") Integer restaurantId,
                                     @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);

        Vote created = service.save(LocalDateTime.now(), userId, restaurantId);
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/votes" + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @DeleteMapping(value = "/votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete vote with id={}", id);
        service.delete(id);
    }
}
