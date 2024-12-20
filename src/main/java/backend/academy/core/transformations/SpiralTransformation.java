package backend.academy.core.transformations;

import backend.academy.model.Point;

public class SpiralTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        // Calculate r (the radial distance)
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());

        // Calculate theta (the angle in polar coordinates)
        double theta = Math.atan2(p.y(), p.x());

        // Apply the transformation
        double newX = Math.cos(theta) + Math.sin(r);
        double newY = Math.sin(theta) - Math.cos(r);

        return new Point(newX / r, newY / r);
    }
}
