package backend.academy.core;

import backend.academy.Configs;
import backend.academy.model.FractalImage;
import backend.academy.model.Point;
import backend.academy.model.Rect;
import org.junit.jupiter.api.Test;
import java.security.SecureRandom;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
class RenderTest {
    private static class TestRender extends Render {
        @Override
        protected FractalImage getImage(Configs fractal) {
            return FractalImage.create(fractal.getWidth(), fractal.getHeight());
        }
    }
    @Test
    void testRotate() {
        // Создаем объект Render (анонимный класс для теста)
        Render render = new Render() {
            @Override
            protected FractalImage getImage(Configs fractal) {
                return null;
            }
        };

        // Тестируем вращение на 90 градусов (симметрия = 4)
        Point point = new Point(1, 0);
        Point rotatedPoint = render.rotate(point, 4);  // Симметрия 4 = 90 градусов

        ;
        assertEquals(1, rotatedPoint.y(), "Y должно быть 1 после вращения на 90 градусов");
    }

    // Тест для метода random
    @Test
    void testRandom() {
        // Мокаем SecureRandom
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextDouble()).thenReturn(0.5);  // Возвращаем 0.5 для теста

        Render render = new Render() {
            @Override
            protected FractalImage getImage(Configs fractal) {
                return null;
            }
        };

        // Генерация случайного числа
        double randomValue = render.random(10, 20, random);

        // Ожидаем значение 15 (10 + (20 - 10) * 0.5 = 15)
        assertEquals(15.0, randomValue, "Сгенерированное случайное число должно быть 15");
    }

    // Тест для метода randomPoint
    @Test
    void testRandomPoint() {
        // Мокаем Configs и Bounds
        Configs configs = mock(Configs.class);
        Rect bounds = mock(Rect.class);
        when(configs.getBounds()).thenReturn(bounds);
        when(bounds.minX()).thenReturn(0.0);
        when(bounds.maxX()).thenReturn(10.0);
        when(bounds.minY()).thenReturn(0.0);
        when(bounds.maxY()).thenReturn(10.0);

        // Мокаем SecureRandom
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextDouble()).thenReturn(0.5);  // Возвращаем 0.5 для теста

        Render render = new Render() {
            @Override
            protected FractalImage getImage(Configs fractal) {
                return null;
            }
        };

        // Генерация случайной точки
        Point point = render.randomPoint(random, configs);

        // Проверяем, что сгенерированная точка лежит в пределах 0-10 для X и Y
        assertTrue(point.x() >= 0 && point.x() <= 10, "X должно быть в пределах от 0 до 10");
        assertTrue(point.y() >= 0 && point.y() <= 10, "Y должно быть в пределах от 0 до 10");
    }
}
