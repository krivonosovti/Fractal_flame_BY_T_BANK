package backend.academy.model;


import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
public class FractalImageTest {


    // Тест для метода create
    @Test
    void testCreate() {
        // Создаем FractalImage
        FractalImage image = FractalImage.create(10, 10);

        // Проверяем, что изображение создано с правильными размерами
        assertNotNull(image);
        assertEquals(10, image.width());
        assertEquals(10, image.height());

        // Проверяем, что все пиксели инициализированы значением (0, 0, 0, 0)
        for (Pixel pixel : image.data()) {
            assertEquals(0, pixel.r());
            assertEquals(0, pixel.g());
            assertEquals(0, pixel.b());
            assertEquals(0, pixel.hitCount());
        }
    }

    // Тест для метода contains
    @Test
    void testContains() {
        // Создаем FractalImage
        FractalImage image = FractalImage.create(10, 10);

        // Проверяем, что координаты внутри изображения возвращают true
        assertTrue(image.contains(5, 5));
        assertTrue(image.contains(0, 0));
        assertTrue(image.contains(9, 9));

        // Проверяем, что координаты за пределами изображения возвращают false
        assertFalse(image.contains(-1, 5));
        assertFalse(image.contains(5, -1));
        assertFalse(image.contains(10, 10));
    }

    // Тест для метода pixel
    @Test
    void testPixel() {
        // Создаем FractalImage
        FractalImage image = FractalImage.create(10, 10);

        // Проверяем, что мы можем получить пиксель по координатам
        Pixel pixel = image.pixel(5, 5);
        assertNotNull(pixel);
        assertEquals(0, pixel.r());
        assertEquals(0, pixel.g());
        assertEquals(0, pixel.b());
        assertEquals(0, pixel.hitCount());

        // Проверяем выброс исключения при выходе за пределы
        assertThrows(IllegalArgumentException.class, () -> image.pixel(10, 10));
        assertThrows(IllegalArgumentException.class, () -> image.pixel(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> image.pixel(0, -1));
    }

    // Тест для метода zoom
    @Test
    void testZoom() {
        // Создаем FractalImage
        FractalImage image = FractalImage.create(10, 10);

        // Создаем Rect с определенными границами
        Rect bounds = new Rect(0, 0, 10, 10);

        // Тестируем zoom
        Point point = new Point(5, 5);
        Point zoomedPoint = image.zoom(point, bounds);

        // Проверяем, что точка была правильно преобразована
        assertEquals(5.0, zoomedPoint.x());
        assertEquals(5.0, zoomedPoint.y());

        // Тестируем границы
        Point pointEdge = new Point(10, 10);
        Point zoomedEdge = image.zoom(pointEdge, bounds);

        assertEquals(10.0, zoomedEdge.x());
        assertEquals(10.0, zoomedEdge.y());
    }
}
