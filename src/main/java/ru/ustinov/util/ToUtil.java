package ru.ustinov.util;

import ru.ustinov.model.DishInMenue;
import ru.ustinov.model.Roles;
import ru.ustinov.model.User;
import ru.ustinov.to.DishInMenueTo;
import ru.ustinov.to.UserTo;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 25.09.2019
 */
public class ToUtil {

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), Roles.ROLE_USER);
    }


    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setRole(userTo.getRole());
        user.setEnabled(userTo.isEnabled());
        return user;
    }
}
