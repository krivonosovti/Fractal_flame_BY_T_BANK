package backend.academy.core.transformations;

import backend.academy.model.Point;

public class DiamondTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());

        return new Point(
            Math.sin(theta) * Math.cos(r),
            Math.sin(r) * Math.cos(theta)
        );
    }
}

