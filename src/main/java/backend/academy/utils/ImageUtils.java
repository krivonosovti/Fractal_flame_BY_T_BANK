package backend.academy.utils;


import backend.academy.model.FractalImage;
import backend.academy.model.ImageFormat;
import backend.academy.model.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() { }

    private static final int SIXTEEN = 16;
    private static final int EIGHT = 8;

    public static void save(FractalImage image, Path path, ImageFormat format) {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Путь не является директорией");
        }
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                int color = (pixel.r() << SIXTEEN) | (pixel.g() << EIGHT) | pixel.b();
                bufferedImage.setRGB(x, y, color);
            }
        }

        File file = Path.of(path.toString(),
            "Fractal_Flame_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                + "." + format.name()).toFile();

        try {
            ImageIO.write(bufferedImage, format.name(), file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
        }
    }
}
