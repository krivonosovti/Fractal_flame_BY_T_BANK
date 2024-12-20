package backend.academy.core.transformations;

import backend.academy.model.Point;

public class HyperbolicTransformation implements Transformation {

    @Override
    public Point apply(Point p) {
        // Calculate r (the radial distance)
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());

        // Calculate theta (the angle in polar coordinates)
        double theta = Math.atan2(p.y(), p.x());

        // Apply the transformation
        double newX = Math.sin(theta) / r;
        double newY = r * Math.cos(theta);

        return new Point(newX, newY);
    }
}
