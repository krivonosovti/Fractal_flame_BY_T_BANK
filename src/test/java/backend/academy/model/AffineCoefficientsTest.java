package backend.academy.model;


import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class AffineCoefficientsTest {

    // Тест для метода create, проверяем создание коэффициентов
    @Test
    void testCreate() {
        // Создаем объект AffineCoefficients с использованием метода create
        AffineCoefficients coeffs = AffineCoefficients.create();

        // Проверяем, что коэффициенты не равны нулю
        assertNotNull(coeffs);
        assertNotEquals(0.0, coeffs.a());
        assertNotEquals(0.0, coeffs.b());
        assertNotEquals(0.0, coeffs.c());
        assertNotEquals(0.0, coeffs.d());
        assertNotEquals(0.0, coeffs.e());
        assertNotEquals(0.0, coeffs.f());
    }

    // Тест для метода transformPoint, проверяем преобразование точки
    @Test
    void testTransformPoint() {
        // Создаем объект AffineCoefficients
        AffineCoefficients coeffs = AffineCoefficients.create();

        // Тестируем преобразование точки
        Point point = new Point(2.0, 3.0);
        Point transformedPoint = coeffs.transformPoint(point);

        // Проверяем, что результат преобразования соответствует ожидаемому
        assertNotNull(transformedPoint);
        assertNotEquals(point.x(), transformedPoint.x());
        assertNotEquals(point.y(), transformedPoint.y());
    }

    // Тест для метода hitPixel, проверяем изменение пикселя
    @Test
    void testHitPixel() {
        // Создаем объект AffineCoefficients
        AffineCoefficients coeffs = AffineCoefficients.create();

        // Мокаем Pixel
        Pixel pixel = mock(Pixel.class);
        when(pixel.r()).thenReturn(100);
        when(pixel.g()).thenReturn(150);
        when(pixel.b()).thenReturn(200);
        when(pixel.hitCount()).thenReturn(2);

        // Применяем hitPixel
        Pixel newPixel = coeffs.hitPixel(pixel);

        // Проверяем, что пиксель обновляется корректно
        assertNotNull(newPixel);
        assertEquals((100 + coeffs.red()) / 2, newPixel.r());
        assertEquals((150 + coeffs.green()) / 2, newPixel.g());
        assertEquals((200 + coeffs.blue()) / 2, newPixel.b());
        assertEquals(pixel.hitCount() + 1, newPixel.hitCount());
    }

    // Тест для метода coefficientsIsValid, проверяем валидность коэффициентов
    @Test
    void testCoefficientsIsValid() {
        // Тестируем валидные коэффициенты
        assertTrue(AffineCoefficients.coefficientsIsValid(0.5, 0.5, 0.5, 0.4));

        // Тестируем невалидные коэффициенты
        assertFalse(AffineCoefficients.coefficientsIsValid(1.0, 1.0, 1.0, 1.0));
    }

    // Тест для randomValue, проверяем корректность генерации случайных значений
    @Test
    void testRandomValue() {
        // Тестируем генерацию случайных значений
        double value = AffineCoefficients.randomValue(AffineCoefficients.RANGE_LINEAR);
        assertTrue(value >= -AffineCoefficients.RANGE_LINEAR && value <= AffineCoefficients.RANGE_LINEAR);

        value = AffineCoefficients.randomValue(AffineCoefficients.RANGE_SHIFT);
        assertTrue(value >= -AffineCoefficients.RANGE_SHIFT && value <= AffineCoefficients.RANGE_SHIFT);
    }
}
