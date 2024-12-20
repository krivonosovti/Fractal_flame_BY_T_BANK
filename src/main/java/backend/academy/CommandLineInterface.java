package backend.academy;

import backend.academy.core.MultiThreadFractalGenerator;
import backend.academy.core.SingleThreadFractalGenerator;
import backend.academy.core.transformations.CrossTransformation;
import backend.academy.core.transformations.DiamondTransformation;
import backend.academy.core.transformations.DiskTransformation;
import backend.academy.core.transformations.HeartTransformation;
import backend.academy.core.transformations.HorseshoeTransformation;
import backend.academy.core.transformations.HyperbolicTransformation;
import backend.academy.core.transformations.LinearTransformation;
import backend.academy.core.transformations.PolarTransformation;
import backend.academy.core.transformations.SinusoidalTransformation;
import backend.academy.core.transformations.SphericalTransformation;
import backend.academy.core.transformations.SpiralTransformation;
import backend.academy.core.transformations.SwirlTransformation;
import backend.academy.core.transformations.Transformation;
import backend.academy.model.FractalImage;
import backend.academy.model.ImageFormat;
import backend.academy.utils.ImageUtils;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CommandLineInterface implements Config {
    private static final PrintStream PRINT_STREAM = System.out;
    private static final Transformation[] AVAILABLE_TRANSFORMATIONS = {
        new DiskTransformation(),
        new HeartTransformation(),
        new HorseshoeTransformation(),
        new HyperbolicTransformation(),
        new PolarTransformation(),
        new SinusoidalTransformation(),
        new SphericalTransformation(),
        new SwirlTransformation(),
        new LinearTransformation(),
        new CrossTransformation(),
        new SpiralTransformation(),
        new DiamondTransformation()
    };
    private static final double NANO = 1_000_000_000.0;
    private static  final int BYTE = 1024;
    private static final String LOADING_STATUS = "\nLoading complete! Time: %.2f s.\n";
    public static final Path DEFAULT_PATH_FOR_IMAGE = Paths.get("images");
    private static final Logger LOGGER = Logger.getLogger(CommandLineInterface.class.getName());

    private CommandLineInterface() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    //CHECKSTYLE:OFF
    public static void main(String[] args) {
        //CHECKSTYLE:ON
        Scanner scanner = new Scanner(System.in);


        int iterations = getInputWithValidation(scanner, "Введите количество итераций.",
            MIN_ITERATIONS, MAX_ITERATIONS);
        int width = getInputWithValidation(scanner, "Введите ширину изображения.", MIN_IMAGE_SIZE, MAX_WIDTH);
        int height = getInputWithValidation(scanner, "Введите высоту изображения.", MIN_IMAGE_SIZE, MAX_HEIGHT);
        int affineCount = getInputWithValidation(scanner,
            "Введите количество аффинных преобразований.", MIN_AFFINE_COUNT, MAX_AFFINE_COUNT);
        double gamma = getInputWithValidation(scanner,
            "Введите значение гаммы (например: 2,2).", MIN_GAMMA, MAX_GAMMA);
        int symmetry = getInputWithValidation(scanner, "Введите симметрию.", MIN_SYMMETRY, MAX_SYMMETRY);
        Path path = correctPathInput(scanner);
        ImageFormat imageFormat = inputFormatWithValidation(scanner);
        printAvailableTransformations();
        // Позволяем пользователю выбрать трансформации
        List<Transformation> selectedTransformations = selectTransformations(scanner);
        printRanableStatus();

        int isMultiThread = getInputWithValidation(scanner,
            "Если хотите использовать многопоточный режим введите 1, иначе 0.", 0, 1);
        // Строим конфиг
        Configs configs = Configs.builder()
            .width(width)
            .height(height)
            .atracrotsAmount(POINTS)
            .iterationCount(iterations)
            .affineCount(affineCount)
            .gamma(gamma)
            .symmetry(symmetry)
            .addTransformations(selectedTransformations.toArray(new Transformation[0]))
            .build();

        printSystemProperties();

        if (isMultiThread == 1) {
            processFractalGeneration(String.format("Multi Thread (%d threads)",
                    Runtime.getRuntime().availableProcessors()),
                () -> MultiThreadFractalGenerator.getFractalImage(configs),
                path, imageFormat);
        } else {
            processFractalGeneration("Single thread",
                () -> SingleThreadFractalGenerator.getFractalImage(configs),
                path, imageFormat);
        }
    }

    private static void printRanableStatus() {
        PRINT_STREAM.println("Генерация изображения...");
    }

    /**
     * Проверка корректности введенных данных для целых чисел
     */
    protected static int getInputWithValidation(Scanner scanner, String prompt, int minValue, int maxValue) {
        int input = -1;
        while (true) {
            try {
                PRINT_STREAM.print(prompt);
                PRINT_STREAM.printf(" В диапазоне от %d до %d: ", minValue, maxValue);
                input = scanner.nextInt();
                if (input < minValue || input > maxValue) {
                    throw new IllegalArgumentException(
                        "Значение должно быть [" + minValue + " ; " + maxValue + "]");
                }
                break;
            } catch (Exception e) {
                PRINT_STREAM.println("Ошибка ввода. Пожалуйста, введите целое число в допустимом диапазоне.");
                scanner.nextLine();
            }
        }
        return input;
    }


    /**
     * Проверка корректности введенных данных для вещественных чисел
     */
    private static double getInputWithValidation(Scanner scanner, String prompt, double minValue, double maxValue) {
        double input = -1;
        while (true) {
            try {
                PRINT_STREAM.print(prompt);
                PRINT_STREAM.printf(" В диапазоне от %f до %f: ", minValue, maxValue);
                input = scanner.nextDouble();
                if (input < minValue || input > maxValue) {
                    throw new IllegalArgumentException(
                        "Значение должно быть в пределах от " + minValue + " до " + maxValue);
                }
                break;
            } catch (Exception e) {
                PRINT_STREAM.println("Ошибка ввода. Пожалуйста, введите вещественное число"
                    + " в допустимом диапазоне. Число должно быть написано через \",\"");
                scanner.nextLine(); // Очистить буфер
            }
        }
        return input;
    }

    /**
     * Печатает доступные трансформации.
     */
    private static void printAvailableTransformations() {
        PRINT_STREAM.println("Доступные трансформации:");
        for (int i = 0; i < AVAILABLE_TRANSFORMATIONS.length; i++) {
            PRINT_STREAM.printf("%d. %s\n", i + 1, AVAILABLE_TRANSFORMATIONS[i].getClass().getSimpleName());
        }
    }

    /**
     * Позволяет пользователю выбрать трансформации.
     */
    protected static List<Transformation> selectTransformations(Scanner scanner) {
        List<Transformation> selectedTransformations = new ArrayList<>();
        PRINT_STREAM.print("Хотите ли вы выбрать трансформации (y/n)? ");
        String choice = scanner.next();

        if ("y".equalsIgnoreCase(choice)) {
            PRINT_STREAM.print("Введите количество трансформаций для выбора: ");
            int count = getInputWithValidation(scanner, "", 1, AVAILABLE_TRANSFORMATIONS.length);
            for (int i = 0; i < count; i++) {
                PRINT_STREAM.print("Введите номер трансформации (от 1 до " + AVAILABLE_TRANSFORMATIONS.length + "): ");
                int transformationChoice = scanner.nextInt();
                if (transformationChoice >= 1 && transformationChoice <= AVAILABLE_TRANSFORMATIONS.length) {
                    selectedTransformations.add(AVAILABLE_TRANSFORMATIONS[transformationChoice - 1]);
                } else {
                    PRINT_STREAM.println("Некорректный выбор трансформации.");
                }
            }
        } else {
            // Генерация случайных трансформаций
            PRINT_STREAM.print("Введите количество случайных трансформаций для генерации: ");
            int randomCount = getInputWithValidation(scanner, "", 1, AVAILABLE_TRANSFORMATIONS.length);
            selectedTransformations.addAll(generateRandomTransformations(randomCount));
        }
        return selectedTransformations;
    }

    /**
     * Генерирует случайный набор трансформаций.
     */
    private static List<Transformation> generateRandomTransformations(int count) {
        SecureRandom random = new SecureRandom();
        List<Transformation> transformations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(AVAILABLE_TRANSFORMATIONS.length);
            transformations.add(AVAILABLE_TRANSFORMATIONS[randomIndex]);
        }
        return transformations;
    }

    /**
     * Сохраняет изображение фрактала в заданный путь.
     */
    protected static void saveFractalImage(FractalImage image, Path outputPath, ImageFormat format) {
        ImageUtils.save(image, outputPath, format);
        PRINT_STREAM.println("Изображение сохранено: " + outputPath);
    }

    private static void printTime(long startTime) {
        long finalTime = System.nanoTime();
        PRINT_STREAM.printf(LOADING_STATUS, (finalTime - startTime) / NANO);
    }

    public static Path correctPathInput(Scanner scanner) {
        PRINT_STREAM.printf("Укажите директорию для сохранения изображений (при вводе \"default\" они будут сохранены "
            + "в папке \"" + DEFAULT_PATH_FOR_IMAGE + "\"): \n");
        String input = scanner.nextLine().trim();
        while (!input.equals("default")) {
            if (!input.equals("")) {
                try {
                    Path path = Paths.get(input);
                    if (Files.isDirectory(path)) {
                        return path;
                    } else {
                        PRINT_STREAM.println(
                            "Введённый путь не является директорией. Введите директорию или \"default\"");
                    }
                } catch (Exception e) {
                    PRINT_STREAM.println("Путь введён неверно. Введите директорию или \"default\"");
                }
            }
            input = scanner.nextLine();
        }
        return DEFAULT_PATH_FOR_IMAGE;
    }

    protected static ImageFormat inputFormatWithValidation(Scanner scanner) {
        ImageFormat format = null;
        while (format == null) {
            PRINT_STREAM.print("Введите формат изображения (jpeg, bmp, png): ");
            String input = scanner.next().toLowerCase();

            // не маштабируемо( но это и не требуется
            switch (input) {
                case "jpeg":
                    format = ImageFormat.JPEG;
                    break;
                case "bmp":
                    format = ImageFormat.BMP;
                    break;
                case "png":
                    format = ImageFormat.PNG;
                    break;
                default:
                    PRINT_STREAM.println("Некорректный формат. Пожалуйста, выберите один из "
                        + "доступных форматов: jpeg, bmp, png.");
            }
        }
        return format;
    }

    static void printSystemProperties() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");

        // Информация о процессоLре
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        // Информация о памяти
        long totalMemory = Runtime.getRuntime().totalMemory() / BYTE / BYTE; // В MB
        long maxMemory = Runtime.getRuntime().maxMemory() / BYTE / BYTE;     // В MB

        // Вывод информации
        PRINT_STREAM.println("=== System Summary ===");
        PRINT_STREAM.println("Operating System: " + osName + " " + osVersion + " (" + osArch + ")");
        PRINT_STREAM.println("Available Processors (CPU cores): " + availableProcessors);
        PRINT_STREAM.println("Total Memory (Heap): " + totalMemory + " MB");
        PRINT_STREAM.println("Max Memory (Heap): " + maxMemory + " MB\n");
    }

    private static void processFractalGeneration(String mode, Supplier<FractalImage> generator, Path path,
        ImageFormat imageFormat) {
        try {
            PRINT_STREAM.printf("\n%s: \n", mode);
            long startTime = System.nanoTime();
            FractalImage image = generator.get();
            long finalTime = System.nanoTime();
            PRINT_STREAM.printf(LOADING_STATUS, (finalTime - startTime) / NANO);
            saveFractalImage(image, path, imageFormat);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
}
