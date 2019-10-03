package ru.ustinov;

import ru.ustinov.model.Roles;
import ru.ustinov.model.User;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 18.09.2019
 */
public class UserTestData {
    public static final int USER_1_ID = 100000;
    public static final int USER_2_ID = USER_1_ID + 1;

    public static final int ADMIN_ID = USER_1_ID + 2;

    public static final User USER_1 = new User(USER_1_ID, "User1", "user1@yandex.ru", "password", Roles.ROLE_USER);
    public static final User USER_2 = new User(USER_2_ID, "User2", "user2@yandex.ru", "password", Roles.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Roles.ROLE_ADMIN);
}
