package ru.ustinov.util.exeption;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 02.10.2019
 */
public class ErrorInfo {
    private final String url;
    private final String[] details;

    public ErrorInfo(CharSequence url, String[] details) {
        this.url = url.toString();
        this.details = details;
    }
}
