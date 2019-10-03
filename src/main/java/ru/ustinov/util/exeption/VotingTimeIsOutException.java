package ru.ustinov.util.exeption;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 24.09.2019
 */
public class VotingTimeIsOutException extends RuntimeException {
    public VotingTimeIsOutException(String message) {
        super(message);
    }
}
