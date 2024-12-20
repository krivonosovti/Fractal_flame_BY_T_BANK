package backend.academy.core;

import backend.academy.Configs;
import backend.academy.model.FractalImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

public final class MultiThreadFractalGenerator extends Render {

    private static final int AVAILABLE_THREAD = Runtime.getRuntime().availableProcessors();

    private MultiThreadFractalGenerator() {}

    public static  FractalImage getFractalImage(@NotNull Configs fractal) throws IllegalArgumentException {
        MultiThreadFractalGenerator multiThread = new MultiThreadFractalGenerator();
        return multiThread.getImage(fractal);
    }

    @Override
    protected FractalImage getImage(@NotNull Configs fractal) throws IllegalArgumentException {
        FractalImage image = FractalImage.create(fractal.getWidth(), fractal.getHeight());
        try (ExecutorService executor = Executors.newFixedThreadPool(AVAILABLE_THREAD)) {
            for (int p = 0; p < fractal.getAtracrotsAmount(); ++p) {
                Runnable task = () -> generate(fractal, image);
                executor.execute(task);
            }
        }
        FractalCorrection.applyIntensityCorrection(image, fractal.getGamma());
        return image;
    }

}
