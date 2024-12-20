package backend.academy.core;

import backend.academy.Configs;
import backend.academy.model.FractalImage;
import org.jetbrains.annotations.NotNull;

public final class SingleThreadFractalGenerator extends Render {

    private SingleThreadFractalGenerator() {}

    public static  FractalImage getFractalImage(@NotNull Configs fractal) throws IllegalArgumentException {
        SingleThreadFractalGenerator singleThread = new SingleThreadFractalGenerator();
        return singleThread.getImage(fractal);
    }

    @Override
    protected FractalImage getImage(Configs fractal) throws IllegalArgumentException {
        FractalImage image = FractalImage.create(fractal.getWidth(), fractal.getHeight());
        for (int p = 0; p < fractal.getAtracrotsAmount(); ++p) {
            generate(fractal, image);
        }
        FractalCorrection.applyIntensityCorrection(image, fractal.getGamma());
        return image;
    }
}
