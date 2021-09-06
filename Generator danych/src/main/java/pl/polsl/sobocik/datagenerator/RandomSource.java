package pl.polsl.sobocik.datagenerator;

import java.util.Random;

public class RandomSource {
    public final static Random random = new Random(123);

    public static int randomInt(int from, int to) {
        return from + random.nextInt(to + 1);
    }
}
