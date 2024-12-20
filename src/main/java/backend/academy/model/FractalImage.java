package backend.academy.model;

import org.jetbrains.annotations.NotNull;

public record FractalImage(Pixel[] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(0, 0, 0, 0); // Инициализация пустыми пикселями
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (!contains(x, y)) {
            throw new IllegalArgumentException("Coordinates out of bounds.");
        }
        return data[y * width + x];
    }

    public Point zoom(@NotNull Point point, @NotNull Rect bounds) {
        int x = (int) ((point.x() - bounds.minX()) / (bounds.maxX() - bounds.minX()) * width);
        int y = (int) ((point.y() - bounds.minY()) / (bounds.maxY() - bounds.minY()) * height);
        return new Point(x, y);
    }

}
