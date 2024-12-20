package backend.academy.model;

public record Pixel(int r, int g, int b, int hitCount) {
    public Pixel {
        if (hitCount < 0) {
            throw new IllegalArgumentException("Hit count can't be negative.");
        }
    }
}
