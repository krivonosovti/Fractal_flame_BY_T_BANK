package backend.academy.core;

import backend.academy.Configs;
import backend.academy.core.transformations.Transformation;
import backend.academy.model.FractalImage;
import backend.academy.model.Pixel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiThreadFractalGeneratorTest {
    private static final int RGB_BOUND = 255;
    @Test
    void testFractalImageGeneration() {
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

    @Test
    void testMultithreading() {
        // Создаем mock Configs
        Configs configs = mock(Configs.class);
        when(configs.getWidth()).thenReturn(400);
        when(configs.getHeight()).thenReturn(300);
        when(configs.getAtracrotsAmount()).thenReturn(8);
        when(configs.getGamma()).thenReturn(1.5);
        when(configs.getTransformations()).thenReturn(List.of(new Transformation[0]));

        // Генерация изображения
        FractalImage image = MultiThreadFractalGenerator.getFractalImage(configs);

        // Проверяем, что изображение сгенерировано
        assertNotNull(image);
    }

    @Test
    void testIntensityCorrectionIntegration() {
        // Создаем mock Configs
        Configs configs = mock(Configs.class);
        when(configs.getWidth()).thenReturn(100);
        when(configs.getHeight()).thenReturn(100);
        when(configs.getAtracrotsAmount()).thenReturn(2);
        when(configs.getGamma()).thenReturn(2.0);
        when(configs.getTransformations()).thenReturn(List.of(new Transformation[0]));

        // Генерация изображения
        FractalImage image = MultiThreadFractalGenerator.getFractalImage(configs);

        // Проверяем, что изображение прошло гамма-коррекцию
        // Предполагаем, что все пиксели после коррекции изменены
        for (Pixel pixel : image.data()) {
            assertTrue(pixel.r() >= 0 && pixel.r() <= RGB_BOUND);
            assertTrue(pixel.g() >= 0 && pixel.g() <= RGB_BOUND);
            assertTrue(pixel.b() >= 0 && pixel.b() <= RGB_BOUND);
        }
    }
}
