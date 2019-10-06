package ru.ustinov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ustinov.AuthorizedUser;
import ru.ustinov.model.User;
import ru.ustinov.repository.UserRepository;
import ru.ustinov.to.UserTo;
import ru.ustinov.util.ToUtil;

import java.util.List;

import static ru.ustinov.util.EntitiesUtil.prepareUserToSave;
import static ru.ustinov.util.ValidationUtil.*;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 25.09.2019
 */
@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getAll() {
        return repository.findAll();
    }


    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }



    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }


    public User getWithVotes(int id) {
        return checkNotFoundWithId(repository.findByIdWithVotes(id), id);
    }


    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareUserToSave(user, passwordEncoder));
    }


    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }


    public void update(UserTo userTo) {
        Assert.notNull(userTo, "user must not be null");
        User user = ToUtil.updateFromTo(get(userTo.getId()), userTo);
        repository.save(user);
    }

    //  Custom update method for user updating his profile (do not change roles and enabled):
    public void updateProfile(UserTo userTo, int id) {
        assureToIdConsistent(userTo, id);
        User user = get(userTo.getId());
        user.setEmail(userTo.getEmail());
        user.setName(userTo.getName());
        repository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return new AuthorizedUser(user);
    }
}
