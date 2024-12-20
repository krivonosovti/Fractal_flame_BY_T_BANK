package backend.academy.core;

import backend.academy.model.FractalImage;
import backend.academy.model.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FractalCorrectionTest {
    private static final int RGB_BOUND = 255;
    @Test
    void testApplyIntensityCorrection() {
        // Устанавливаем размеры изображения
        int width = 3;
        int height = 3;

        // Создаем тестовый объект FractalImage
        FractalImage image = FractalImage.create(width, height);

        // Заполняем тестовое изображение пикселями
        image.data()[0] = new Pixel(100, 150, 200, 10);
        image.data()[1] = new Pixel(50, 75, 100, 20);
        image.data()[2] = new Pixel(25, 50, 75, 0);  // hitCount == 0
        image.data()[3] = new Pixel(0, 0, 0, 5);
        image.data()[4] = new Pixel(RGB_BOUND, RGB_BOUND, RGB_BOUND, 30);
        image.data()[5] = new Pixel(10, 20, 30, 15);
        image.data()[6] = new Pixel(5, 5, 5, 0);     // hitCount == 0
        image.data()[7] = new Pixel(100, 100, 100, 25);
        image.data()[8] = new Pixel(200, 200, 200, 40);

        // Применяем гамма-коррекцию
        double gamma = 2.2;
        FractalCorrection.applyIntensityCorrection(image, gamma);

        // Проверяем ограничение значений RGB (0–RGB_BOUND)
        for (Pixel pixel : image.data()) {
            assertTrue(pixel.r() >= 0 && pixel.r() <= RGB_BOUND, "Red channel out of bounds");
            assertTrue(pixel.g() >= 0 && pixel.g() <= RGB_BOUND, "Green channel out of bounds");
            assertTrue(pixel.b() >= 0 && pixel.b() <= RGB_BOUND, "Blue channel out of bounds");
        }

        // Проверяем, что пиксели с hitCount == 0 не изменились
        assertEquals(new Pixel(25, 50, 75, 0), image.data()[2]);
        assertEquals(new Pixel(5, 5, 5, 0), image.data()[6]);

        // Проверяем изменения пикселей с ненулевым hitCount
        Pixel p1 = image.data()[0];
        assertNotEquals(100, p1.r());
        assertNotEquals(150, p1.g());
        assertNotEquals(200, p1.b());
    }

    @Test
    void testGammaCorrectionBoundary() {
        // Создаем тестовое изображение с одним пикселем
        FractalImage image = FractalImage.create(1, 1);
        image.data()[0] = new Pixel(200, 100, 50, 10);

        // Применяем гамма-коррекцию с крайними значениями gamma
        FractalCorrection.applyIntensityCorrection(image, 1.0); // Гамма 1.0 (без изменений)
        assertEquals(new Pixel(200, 100, 50, 10), image.data()[0]);

        FractalCorrection.applyIntensityCorrection(image, 10.0); // Высокая гамма
        Pixel corrected = image.data()[0];
        assertTrue(corrected.r() == 200, "Red channel should decrease with high gamma");
        assertTrue(corrected.g() == 100, "Green channel should decrease with high gamma");
        assertTrue(corrected.b() == 50, "Blue channel should decrease with high gamma");
    }

    @Test
    void testEmptyImage() {
        // Создаем пустое изображение
        FractalImage image = FractalImage.create(0, 0);

        // Применяем гамма-коррекцию
        FractalCorrection.applyIntensityCorrection(image, 2.2);

        // Проверяем, что данные изображения остаются пустыми
        assertEquals(0, image.data().length, "Empty image should remain empty");
    }
}
