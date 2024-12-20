package backend.academy.core.transformations;

import backend.academy.model.Point;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        // Calculate r (the radial distance)
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());

        // Apply the transformation
        double newX = (p.x() * p.x() - p.y() * p.y()) / r;
        double newY = 2 * p.x() * p.y() / r;

        return new Point(newX, newY);
    }
}
