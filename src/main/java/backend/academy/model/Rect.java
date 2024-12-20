package backend.academy.model;

public record Rect(double minX, double minY, double maxX, double maxY) {
    public boolean contains(Point p) {
        return p.x() >= minX && p.x() <= maxX && p.y() >= minY && p.y() <= maxY;
    }
}
