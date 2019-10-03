package ru.ustinov.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.model.User;

import java.util.List;
import java.util.Optional;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 12.09.2019
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(Integer integer);

    @Override
    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

    @Transactional
    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.votes v " +
            "LEFT JOIN FETCH v.restaurant " +
            "WHERE u.id=?1")
    User findByIdWithVotes(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    int delete(int id);
}
