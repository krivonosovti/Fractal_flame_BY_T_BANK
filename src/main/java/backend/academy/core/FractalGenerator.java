package backend.academy.core;

import backend.academy.Configs;
import backend.academy.model.FractalImage;
import org.jetbrains.annotations.NotNull;

public interface FractalGenerator {
    void generate(@NotNull Configs configs, FractalImage image);
}
