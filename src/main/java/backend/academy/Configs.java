package backend.academy;

import backend.academy.core.transformations.Transformation;
import backend.academy.model.AffineCoefficients;
import backend.academy.model.Rect;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Configs {
    private final int width;
    private final int height;
    private final int atracrotsAmount;
    private final int iterationCount;
    private final int affineCount;
    private final int symmetry;
    private final double gamma;
    private final Rect bounds;
    private final List<Transformation> transformations;
    private final AffineCoefficients[] coefficients;

    private Configs(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.atracrotsAmount = builder.atracrotsAmount;
        this.iterationCount = builder.iterationCount;
        this.affineCount = builder.affineCount;
        this.symmetry = builder.symmetry;
        this.gamma = builder.gamma;
        this.bounds = builder.bounds;
        this.transformations = builder.transformations;
        this.coefficients = builder.coefficients;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public int getAffineCount() {
        return affineCount;
    }

    public int getSymmetry() {
        return symmetry;
    }

    public double getGamma() {
        return gamma;
    }

    public Rect getBounds() {
        return bounds;
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public AffineCoefficients[] getCoefficients() {
        return coefficients;
    }

    public int getAtracrotsAmount() {
        return atracrotsAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    //CHECKSTYLE:OFF
    public static class Builder {
        private int width = 1920;
        private int height = 1080;
        private int atracrotsAmount = 15;
        private int iterationCount = 100000;
        private int affineCount = 15;
        private int symmetry = 1;
        private double gamma = 2.2;
        private Rect bounds = new Rect(-1.777, -1.0, 1.777, 1.0);
        //CHECKSTYLE:ON

        private List<Transformation> transformations = new ArrayList<>();

        private AffineCoefficients[] coefficients = initializeAffineCoefficients(affineCount);

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder atracrotsAmount(int atracrotsAmount) {
            this.atracrotsAmount = atracrotsAmount;
            return this;
        }

        public Builder iterationCount(int iterationCount) {
            this.iterationCount = iterationCount;
            return this;
        }

        public Builder affineCount(int affineCount) {
            this.affineCount = affineCount;
            this.coefficients = initializeAffineCoefficients(affineCount);
            return this;
        }

        public Builder symmetry(int symmetry) {
            this.symmetry = symmetry;
            return this;
        }

        public Builder gamma(double gamma) {
            this.gamma = gamma;
            return this;
        }

        public Builder addTransformation(Transformation transformation) {
            this.transformations.add(transformation);
            return this;
        }

        public Builder addTransformations(Transformation... transformations) {
            this.transformations.addAll(List.of(transformations));
            return this;
        }

        public Builder setTransformations(List<Transformation> transformation) {
            transformations = transformation;
            return this;
        }

        private AffineCoefficients @NotNull [] initializeAffineCoefficients(int affineCount) {
            AffineCoefficients[] localCoefficients = new AffineCoefficients[affineCount];
            for (int i = 0; i < affineCount; i++) {
                localCoefficients[i] = AffineCoefficients.create();
            }
            return localCoefficients;
        }

        public Configs build() {
            return new Configs(this);
        }
    }
}
