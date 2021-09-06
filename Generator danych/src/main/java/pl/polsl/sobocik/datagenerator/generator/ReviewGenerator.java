package pl.polsl.sobocik.datagenerator.generator;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.sobocik.datagenerator.model.Customer;
import pl.polsl.sobocik.datagenerator.model.Product;
import pl.polsl.sobocik.datagenerator.model.Purchase;
import pl.polsl.sobocik.datagenerator.model.Review;
import pl.polsl.sobocik.datagenerator.source.DataSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReviewGenerator extends GeneratorBase {

    private final DataSource dataSource;
    private Integer idCounter = 1;
    Lorem lorem = LoremIpsum.getInstance();

    public Review getReview() {
        Review review = new Review();
        review.setReviewId(idCounter++);
        review.setScore(randomInt(1, 5));
        review.setContent(randomContent());
        review.setAuthor(randomAuthor());
        review.setCreated(randomDateTime());
        return review;
    }

    private String randomAuthor() {
        StringBuilder authorBuilder = new StringBuilder();
        String firstName = dataSource.getFirstNames().get(random.nextInt(dataSource.getFirstNames().size()));
        String lastName = dataSource.getLastNames().get(random.nextInt(dataSource.getLastNames().size()));

        String firstPart = firstName.substring(0, Math.min(firstName.length(), 4));
        String secondPart = lastName.substring(0, Math.min(lastName.length(), 3));

        authorBuilder.append(firstPart).append(secondPart);

        if (random.nextBoolean()) {
            Integer number = randomInt(1, 200);
            authorBuilder.append(number);
        }

        return authorBuilder.toString();
    }

    private String randomContent() {
        String content = lorem.getWords(5, 50);
        if (content.length() > 400) {
            content = content.substring(0, 400);
        }
        return content;
    }
}
