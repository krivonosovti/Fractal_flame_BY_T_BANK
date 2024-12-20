package backend.academy.core;

import backend.academy.Configs;
import backend.academy.core.transformations.CrossTransformation;
import backend.academy.core.transformations.DiskTransformation;
import backend.academy.core.transformations.HeartTransformation;
import backend.academy.core.transformations.HorseshoeTransformation;
import backend.academy.core.transformations.HyperbolicTransformation;
import backend.academy.core.transformations.LinearTransformation;
import backend.academy.core.transformations.PolarTransformation;
import backend.academy.core.transformations.SphericalTransformation;
import backend.academy.core.transformations.SpiralTransformation;
import backend.academy.core.transformations.SwirlTransformation;
import backend.academy.core.transformations.Transformation;
import backend.academy.model.FractalImage;
import backend.academy.model.Rect;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SingleThreadFractalGeneratorTest {

    private static final Transformation[] AVAILABLE_TRANSFORMATIONS = {
        new DiskTransformation(),
        new HeartTransformation(),
    };
    @Test
    void testFractalImageGeneration() {
        Configs configs = Configs.builder()
            .width(500)
            .height(501)
            .atracrotsAmount(300)
            .iterationCount(100)
            .affineCount(1)
            .gamma(2.2)
            .symmetry(2)
            .addTransformations(AVAILABLE_TRANSFORMATIONS)
            .build();

        // Генерация изображения
        FractalImage image = SingleThreadFractalGenerator.getFractalImage(configs);

        // Проверяем размер изображения
        assertNotNull(image);
        assertEquals(500, image.width());
        assertEquals(501, image.height());
    }

    @Test
    void testSingleThreadExecution() {
        // Создаем mock Configs
        Configs configs = mock(Configs.class);
        when(configs.getWidth()).thenReturn(800);
        when(configs.getHeight()).thenReturn(600);
        when(configs.getAtracrotsAmount()).thenReturn(4);
        when(configs.getGamma()).thenReturn(2.2);
        when(configs.getTransformations()).thenReturn(List.of(new Transformation[0]));

        // Генерация изображения
        FractalImage image = MultiThreadFractalGenerator.getFractalImage(configs);

        // Проверяем размер изображения
        assertNotNull(image);
        assertEquals(800, image.width());
        assertEquals(600, image.height());
    }
}
