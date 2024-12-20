package backend.academy.core;

import backend.academy.Configs;
import backend.academy.model.AffineCoefficients;
import backend.academy.model.FractalImage;
import backend.academy.model.Point;
import java.security.SecureRandom;
import org.jetbrains.annotations.NotNull;

public abstract class Render implements FractalGenerator {

    protected abstract FractalImage getImage(Configs fractal);

    private static final int STEP_SHIFT = -20;

    @Override
    public void generate(@NotNull Configs configs, FractalImage image) throws IllegalArgumentException {
        SecureRandom random = new SecureRandom();
        Point point = randomPoint(random, configs);
        for (int step = STEP_SHIFT; step < configs.getIterationCount(); step++) {
            int transformationIndex = random.nextInt(configs.getTransformations().size());
            int coefficientIndex = random.nextInt(configs.getCoefficients().length);

            point = configs.getCoefficients()[coefficientIndex].transformPoint(point);
            point = configs.getTransformations().get(transformationIndex).apply(point);

            if (step > 0) {
                if (configs.getSymmetry() < 1) { // недостежимо при запуске из cli
                    throw new IllegalArgumentException("Symmetry < 1");
                }

                for (int rot = 0; rot < configs.getSymmetry(); rot++) {
                    if (configs.getBounds().contains(point)) {
                        Point newPoint = image.zoom(point, configs.getBounds());
                        updateImage(image, configs.getCoefficients()[coefficientIndex], newPoint, configs.getWidth());
                    }
                    point = rotate(point, configs.getSymmetry());
                }
            }
        }
    }

    protected Point rotate(Point point, int symmetry) {
        double theta = ((2 * Math.PI) / (symmetry));
        return new Point(
            point.x() * Math.cos(theta) - point.y() * Math.sin(theta),
            point.x() * Math.sin(theta) + point.y() * Math.cos(theta));
    }

    private void updateImage(@NotNull FractalImage image, @NotNull AffineCoefficients coefficient,
        Point point, int width) {
        int x1 = (int) point.x();
        int y1 = (int) point.y();
        if (image.contains(x1, y1)) {
            image.data()[y1 * width + x1] = coefficient.hitPixel(image.pixel(x1, y1));
        }
    }

    double random(double min, double max, SecureRandom random) {
        return min + (max - min) * random.nextDouble();
    }

    protected Point randomPoint(SecureRandom random, Configs configs) {
        return new Point(
            random(configs.getBounds().minX(), configs.getBounds().maxX(), random),
            random(configs.getBounds().minY(), configs.getBounds().maxY(), random)
        );
    }
}
