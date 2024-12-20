package backend.academy.core.transformations;

import backend.academy.model.Point;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        return new Point(p.x() / (r * r), p.y() / (r * r));
    }
}
