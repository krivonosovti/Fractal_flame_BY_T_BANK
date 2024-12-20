package backend.academy.core.transformations;

import backend.academy.model.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double newX = p.x() * Math.sin(r * r) - p.y() * Math.cos(r * r);
        double newY =  p.x() * Math.cos(r * r) + p.y() * Math.sin(r * r);
        return new Point(newX, newY);
    }
}
