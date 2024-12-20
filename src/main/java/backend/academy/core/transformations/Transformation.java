package backend.academy.core.transformations;

import backend.academy.model.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    int TRANSFORMATION_AMOUNT = 12;
}
