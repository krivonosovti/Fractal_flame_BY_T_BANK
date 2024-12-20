package backend.academy.core;

import backend.academy.Config;
import backend.academy.Configs;
import backend.academy.core.transformations.DiskTransformation;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Comparison implements Config {
    @Test
    void minBoundTest() {
        Configs configs = Configs.builder()
            .width(MIN_IMAGE_SIZE)
            .height(MIN_IMAGE_SIZE)
            .atracrotsAmount(POINTS)
            .iterationCount(MIN_ITERATIONS)
            .affineCount(MIN_AFFINE_COUNT)
            .gamma(MIN_GAMMA)
            .symmetry(MIN_SYMMETRY)
            .addTransformations(new DiskTransformation())
            .build();

        long startTime = System.nanoTime();
        SingleThreadFractalGenerator.getFractalImage(configs);
        long singleTime = System.nanoTime()- startTime;
        MultiThreadFractalGenerator.getFractalImage(configs);
        long MultiTime = singleTime - System.nanoTime();
        assertTrue(MultiTime < singleTime, "MultiTime > singleTime");
    }

    @Test
    void maxBoundTest() {
        Configs configs = Configs.builder()
            .width(MAX_WIDTH)
            .height(MAX_HEIGHT)
            .atracrotsAmount(POINTS)
            .iterationCount(MAX_ITERATIONS)
            .affineCount(MAX_AFFINE_COUNT)
            .gamma(MAX_GAMMA)
            .symmetry(MAX_SYMMETRY)
            .addTransformations(AVAILABLE_TRANSFORMATIONS)
            .build();

        long startTime = System.nanoTime();
        SingleThreadFractalGenerator.getFractalImage(configs);
        long singleTime = System.nanoTime()- startTime;
        MultiThreadFractalGenerator.getFractalImage(configs);
        long MultiTime = singleTime - System.nanoTime();
        assertTrue(MultiTime < singleTime, "MultiTime > singleTime");
    }
}
