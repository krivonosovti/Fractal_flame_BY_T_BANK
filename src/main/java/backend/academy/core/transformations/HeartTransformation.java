package backend.academy.core.transformations;

import backend.academy.model.Point;

public class HeartTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        return new Point(
            r * Math.sin(theta * r),
            -r * Math.cos(theta * r)
        );
    }
}
