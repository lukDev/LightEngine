package lightEngine.util.math;

import java.util.Random;
import java.util.UUID;

public class Randomizer {

    private static Random random = new Random();

    public static int getRandomInt(int min, int max) {

        return random.nextInt(max + 1 - min) + min;

    }

    public static String getRandomUUID() {

        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();

    }

}
