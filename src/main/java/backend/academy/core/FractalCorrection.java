package backend.academy.core;

import backend.academy.model.FractalImage;
import backend.academy.model.Pixel;

public final class FractalCorrection {
    private FractalCorrection() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final int RGB_BOUND = 255;

    public static void applyIntensityCorrection(FractalImage image, double gamma) {
        int height = image.height();
        int width = image.width();
        double max = 0.0;

        // Вычисление максимального логарифмированного значения счетчика
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = image.pixel(x, y);
                if (p.hitCount() > 0) {
                    double normal = Math.log10(p.hitCount());
                    if (normal > max) {
                        max = normal;
                    }
                }
            }
        }

        // Применение нормализации и гамма-коррекции
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = image.pixel(x, y);

                if (p.hitCount() > 0) {
                    double normalized = Math.log10(p.hitCount()) / max;

                    // Коррекция каждого цветового канала
                    int red = (int) (p.r() * Math.pow(normalized, 1.0 / gamma));
                    int green = (int) (p.g() * Math.pow(normalized, 1.0 / gamma));
                    int blue = (int) (p.b() * Math.pow(normalized, 1.0 / gamma));

                    // Ограничиваем значения каналов в диапазоне 0–255
                    red = Math.min(RGB_BOUND, Math.max(0, red));
                    green = Math.min(RGB_BOUND, Math.max(0, green));
                    blue = Math.min(RGB_BOUND, Math.max(0, blue));

                    // Обновление пикселя
                    image.data()[y * width + x] = new Pixel(red, green, blue, p.hitCount());
                }
            }
        }
    }
}
