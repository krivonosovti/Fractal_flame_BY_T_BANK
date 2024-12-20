package backend.academy;

import backend.academy.core.transformations.Transformation;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import backend.academy.model.FractalImage;
import backend.academy.model.ImageFormat;
import backend.academy.core.MultiThreadFractalGenerator;
import backend.academy.core.SingleThreadFractalGenerator;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

class CommandLineInterfaceTest {

    @Test
    void testGetInputWithValidationValid() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextInt()).thenReturn(10);

        // Проверяем, что метод возвращает корректное значение
        int result = CommandLineInterface.getInputWithValidation(scanner, "Введите количество итераций.", 1, 100);
        assertEquals(10, result);
    }

    @Test
    void testGetInputWithValidationInvalid() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextInt()).thenReturn(200).thenReturn(10);

        // Проверяем, что метод повторяет ввод, пока не введено корректное значение
        int result = CommandLineInterface.getInputWithValidation(scanner, "Введите количество итераций.", 1, 100);
        assertEquals(10, result);
    }

    @Test
    void testSelectTransformationsManual() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.next()).thenReturn("y"); // Пользователь хочет выбрать трансформации
        when(scanner.nextInt()).thenReturn(1); // Выбирает первую трансформацию

        // Мокаем доступные трансформации
        List<Transformation> transformations = CommandLineInterface.selectTransformations(scanner);

        // Проверяем, что одна трансформация была выбрана
        assertEquals(1, transformations.size());
    }

    @Test
    void testSelectTransformationsRandom() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.next()).thenReturn("n"); // Пользователь выбирает случайные трансформации
        when(scanner.nextInt()).thenReturn(3); // Генерация 3 случайных трансформаций

        // Мокаем доступные трансформации
        List<Transformation> transformations = CommandLineInterface.selectTransformations(scanner);

        // Проверяем, что было добавлено 3 случайные трансформации
        assertEquals(3, transformations.size());
    }
    @Test
    void testGenerateFractalSingleThread() {
        // Мокаем Configs
        Configs configs = mock(Configs.class);
        when(configs.getWidth()).thenReturn(800);
        when(configs.getHeight()).thenReturn(600);

        // Генерация с использованием SingleThreadFractalGenerator
        FractalImage image = SingleThreadFractalGenerator.getFractalImage(configs);

        // Проверяем, что изображение было сгенерировано
        assertNotNull(image);
    }

    @Test
    void testGenerateFractalMultiThread() {
        // Мокаем Configs
        Configs configs = mock(Configs.class);
        when(configs.getWidth()).thenReturn(800);
        when(configs.getHeight()).thenReturn(600);

        // Генерация с использованием MultiThreadFractalGenerator
        FractalImage image = MultiThreadFractalGenerator.getFractalImage(configs);

        // Проверяем, что изображение было сгенерировано
        assertNotNull(image);
    }

    @Test
    void testCorrectPathInput() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextLine()).thenReturn("default");

        // Проверяем, что возвращается дефолтный путь
        Path path = CommandLineInterface.correctPathInput(scanner);
        assertEquals(CommandLineInterface.DEFAULT_PATH_FOR_IMAGE, path);
    }

    @Test
    void testInputFormatWithValidation() {
        // Мокаем Scanner
        Scanner scanner = mock(Scanner.class);
        when(scanner.next()).thenReturn("png");

        // Проверяем, что формат изображения возвращается корректно
        ImageFormat format = CommandLineInterface.inputFormatWithValidation(scanner);
        assertEquals(ImageFormat.PNG, format);
    }
}
