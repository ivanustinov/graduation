package ru.ustinov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.Restaurant;
import ru.ustinov.model.User;
import ru.ustinov.model.Vote;
import ru.ustinov.repository.RestaurantRepository;
import ru.ustinov.repository.UserRepository;
import ru.ustinov.repository.VoteRepository;
import ru.ustinov.util.exeption.VotingTimeIsOutException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.ustinov.util.ValidationUtil.checkNotFoundWithId;
import static ru.ustinov.util.ValidationUtil.checkNotFoundWithIdAndDate;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 24.09.2019
 */

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    private static final LocalTime VOTE_END_TIME = LocalTime.of(11, 0, 0);

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public List<Vote> getAll() {
        return voteRepository.findAll();
    }


    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.findById(id).orElse(null), id);
    }


    public Vote getByUserAndDate(int userId, LocalDate date) {
        return checkNotFoundWithIdAndDate(voteRepository.findByUserIdAndDate(userId, date).orElse(null), userId, date);
    }


    public Long countAllByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return voteRepository.countAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Transactional
    public Vote save(LocalDateTime dateTime, int userId, int restaurantId) {
        Optional<Vote> voteOptional = voteRepository.findByUserIdAndDate(userId, dateTime.toLocalDate());
        if (dateTime.toLocalTime().isBefore(VOTE_END_TIME)) {
            Vote vote = voteOptional.isPresent() ? update(voteOptional.get(), restaurantId) : create(dateTime.toLocalDate(), userId, restaurantId);
            return initLazyObj(vote);
        } else {
            throw new VotingTimeIsOutException("Vote time: " + VOTE_END_TIME + " is over.");
        }
    }

    //init lazy restaurant and user after saving
    private Vote initLazyObj(Vote vote) {
        vote.getRestaurant().toString();
        vote.getUser().toString();
        return vote;
    }

    private Vote update(Vote vote, int restaurantId) {
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    private Vote create(LocalDate date, int userId, int restaurantId) {
        User user = userRepository.getOne(userId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        return voteRepository.save(new Vote(date, user, restaurant));
    }


    public void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }
}
