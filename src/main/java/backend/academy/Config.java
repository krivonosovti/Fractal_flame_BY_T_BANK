package backend.academy;

import backend.academy.core.transformations.CrossTransformation;
import backend.academy.core.transformations.DiamondTransformation;
import backend.academy.core.transformations.DiskTransformation;
import backend.academy.core.transformations.HeartTransformation;
import backend.academy.core.transformations.HorseshoeTransformation;
import backend.academy.core.transformations.HyperbolicTransformation;
import backend.academy.core.transformations.LinearTransformation;
import backend.academy.core.transformations.PolarTransformation;
import backend.academy.core.transformations.SinusoidalTransformation;
import backend.academy.core.transformations.SphericalTransformation;
import backend.academy.core.transformations.SpiralTransformation;
import backend.academy.core.transformations.SwirlTransformation;
import backend.academy.core.transformations.Transformation;

public interface Config {
     Transformation[] AVAILABLE_TRANSFORMATIONS = {
        new DiskTransformation(),
        new HeartTransformation(),
        new HorseshoeTransformation(),
        new HyperbolicTransformation(),
        new PolarTransformation(),
        new SinusoidalTransformation(),
        new SphericalTransformation(),
        new SwirlTransformation(),
        new LinearTransformation(),
        new CrossTransformation(),
        new SpiralTransformation(),
        new DiamondTransformation()
    };
    int POINTS = 300;
    int MIN_IMAGE_SIZE = 500;
    int MAX_ITERATIONS = 100_000;
    int MIN_ITERATIONS = 100;
    int MAX_WIDTH = 3840;
    int MAX_HEIGHT = 2160;
    int MAX_AFFINE_COUNT = 20;
    int MIN_AFFINE_COUNT = 1;
    double MIN_GAMMA = 0.1;
    double MAX_GAMMA = 10.0;
    int MIN_SYMMETRY = 1;
    int MAX_SYMMETRY = 10;
}
