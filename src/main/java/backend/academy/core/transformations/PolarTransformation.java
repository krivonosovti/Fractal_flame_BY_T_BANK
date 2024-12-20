package backend.academy.core.transformations;

import backend.academy.model.Point;

public class PolarTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        // Calculate r (the radial distance)
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());

        // Calculate theta (the angle in polar coordinates)
        double theta = Math.atan2(p.y(), p.x());

        // Apply the transformation
        double newX = theta / Math.PI;
        double newY = r - 1;

        return new Point(newX, newY);
    }
}
