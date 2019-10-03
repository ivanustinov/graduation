import org.springframework.context.support.GenericXmlApplicationContext;
import ru.ustinov.repository.RestaurantRepository;
import ru.ustinov.repository.UserRepository;
import ru.ustinov.service.RestaurantService;

import javax.imageio.spi.RegisterableService;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 12.09.2019
 */
public class SpringMain {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            RestaurantService userRepository = appCtx.getBean(RestaurantService.class);
            System.out.println(userRepository.getWithVotesCountByIdAndDate(100003, LocalDate.of(2019, 9, 11)));

        }
    }
}
