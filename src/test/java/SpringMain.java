import org.springframework.context.support.GenericXmlApplicationContext;
import ru.ustinov.model.Restaurant;
import ru.ustinov.model.User;
import ru.ustinov.model.Vote;
import ru.ustinov.repository.RestaurantRepository;
import ru.ustinov.repository.UserRepository;
import ru.ustinov.repository.VoteRepository;

import java.time.LocalDate;

import static ru.ustinov.RestaurantTestData.VILKA_LOSHKA;
import static ru.ustinov.UserTestData.USER_1;

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

//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            VoteRepository voteRepository = appCtx.getBean(VoteRepository.class);
            RestaurantRepository restaurantRepository = appCtx.getBean(RestaurantRepository.class);
            UserRepository userRepository = appCtx.getBean(UserRepository.class);
            User user = userRepository.getOne(100000);
            Restaurant restaurant = restaurantRepository.getOne(100003);
            voteRepository.save(new Vote(LocalDate.now(), user, restaurant));
            var vote = voteRepository.findById(100029).get();
            System.out.println(vote);
        }
    }
}
