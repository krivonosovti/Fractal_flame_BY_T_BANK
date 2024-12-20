package backend.academy.core.transformations;

import backend.academy.model.Point;

public class DiskTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        double factor = 1 / Math.PI * r;

        return new Point(
            factor * Math.sin(Math.PI * theta),
            factor * Math.cos(Math.PI * theta)
        );
    }
}
