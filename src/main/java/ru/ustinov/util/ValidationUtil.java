package ru.ustinov.util;

import org.slf4j.Logger;
import org.springframework.util.Assert;
import ru.ustinov.model.AbstractBaseEntity;
import ru.ustinov.model.DishInMenue;
import ru.ustinov.to.AbstractTo;
import ru.ustinov.to.DishInMenueTo;
import ru.ustinov.util.exeption.IllegalRequestDataException;
import ru.ustinov.util.exeption.NotFoundException;

import javax.lang.model.type.ErrorType;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 20.09.2019
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFoundWithIdAndDate(T object, int id, LocalDate date) {
        return checkNotFound(object, "id=" + id + " date=" + date);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void checkNew(AbstractTo to) {
        if (!to.isNew()) {
            throw new IllegalRequestDataException(to + ", transfer object id must be null");

        }
    }


    public static void assureEntityIdConsistent(AbstractBaseEntity entity, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalRequestDataException(entity + " must be with id=" + id);
        }
    }


    public static void assureToIdConsistent(AbstractTo to, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (to.isNew()) {
            to.setId(id);
        } else if (to.getId() != id) {
            throw new IllegalRequestDataException(to + " must be with id=" + id);
        }
    }


    public static <T extends AbstractBaseEntity> T checkIdMatch(T entity, int id) {
        if (entity.getId() != id) {
            throw new IllegalRequestDataException(entity + " expected to be with id=" + id);
        }
        return entity;
    }

    public static void validateDishInMenue(DishInMenue dishInMenue) {
        Assert.notNull(dishInMenue, "menuDishTo must not be null");
        Assert.notNull(dishInMenue.getId(), "menuDishTo.dishId must not be null");
        Assert.notNull(dishInMenue.getRestaurant().getId(), "menuDishTo.restaurantId must not be null");
    }

    public static void validateDishInMenueTo(DishInMenueTo dishInMenueTo) {
        Assert.notNull(dishInMenueTo, "dishInMenueTo must not be null");
        Assert.notNull(dishInMenueTo.getDishId(), "dishInMenueTo.dishId must not be null");
        Assert.notNull(dishInMenueTo.getRestaurantId(), "dishInMenueTo.restaurantId must not be null");
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logException) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error("Error at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("at request  {}: {}", req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }
}
