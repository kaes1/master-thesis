package pl.polsl.sobocik.datagenerator.generator;

import pl.polsl.sobocik.datagenerator.RandomSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public abstract class GeneratorBase {

    protected final Random random = RandomSource.random;
    private final LocalDate minDate = LocalDate.of(2015, 1, 1);
    private final LocalDate maxDate = LocalDate.of(2021, 9, 5);

    public int randomInt(int from, int to) {
        return RandomSource.randomInt(from, to);
    }
    protected LocalDateTime randomDateTime() {
        int difference = (int) (maxDate.toEpochDay() - minDate.toEpochDay());
        LocalDate date = minDate.plusDays(randomInt(0, difference));
        LocalTime time = LocalTime.of(randomInt(0, 23), randomInt(0, 59), randomInt(0, 59));
        return LocalDateTime.of(date, time);
    }
}
