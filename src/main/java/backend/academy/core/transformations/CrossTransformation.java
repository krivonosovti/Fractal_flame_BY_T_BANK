package backend.academy.core.transformations;

import backend.academy.model.Point;

public class CrossTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double factor = Math.sqrt(1 / (p.x() * p.x() - p.y() * p.y()) * (p.x() * p.x() - p.y() * p.y()));

        return new Point(
            p.x() * factor,
            p.y() * factor
        );
    }
}
