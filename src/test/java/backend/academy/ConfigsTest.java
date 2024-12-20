package backend.academy;

import backend.academy.core.transformations.Transformation;
import backend.academy.model.Point;
import backend.academy.model.Rect;
import org.junit.jupiter.api.Test;

import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigsTest {


    @Test
    void testDefaultBuilderValues() {
        // Use the builder to create a Configs instance with default values
        Configs configs = Configs.builder().build();

        // Verify default values
        assertEquals(1920, configs.getWidth());
        assertEquals(1080, configs.getHeight());
        assertEquals(15, configs.getAtracrotsAmount());
        assertEquals(100000, configs.getIterationCount());
        assertEquals(15, configs.getAffineCount());
        assertEquals(1, configs.getSymmetry());
        assertEquals(2.2, configs.getGamma());
        assertEquals(new Rect(-1.777, -1.0, 1.777, 1.0), configs.getBounds());
        assertNotNull(configs.getTransformations());
        assertEquals(0, configs.getTransformations().size());
        assertNotNull(configs.getCoefficients());
        assertEquals(15, configs.getCoefficients().length); // Default affineCount
    }

    @Test
    void testBuilderOverrides() {
        // Override default values
        Configs configs = Configs.builder()
            .width(1280)
            .height(720)
            .atracrotsAmount(10)
            .iterationCount(50000)
            .affineCount(10)
            .symmetry(2)
            .gamma(1.8)
            .build();

        // Verify overridden values
        assertEquals(1280, configs.getWidth());
        assertEquals(720, configs.getHeight());
        assertEquals(10, configs.getAtracrotsAmount());
        assertEquals(50000, configs.getIterationCount());
        assertEquals(10, configs.getAffineCount());
        assertEquals(2, configs.getSymmetry());
        assertEquals(1.8, configs.getGamma());
    }

    @Test
    void testAddSingleTransformation() {
        Transformation transformation = new Transformation() {
            @Override
            public Point apply(Point point) {
                return point;
            }
        };

        // Add a single transformation
        Configs configs = Configs.builder()
            .addTransformation(transformation)
            .build();

        // Verify that the transformation was added
        assertEquals(1, configs.getTransformations().size());
        assertSame(transformation, configs.getTransformations().get(0));
    }

    @Test
    void testAddMultipleTransformations() {
        Transformation transformation1 = new Transformation() {
            @Override
            public Point apply(Point point) {
                return point;
            }
        };
        Transformation transformation2 = new Transformation() {
            @Override
            public Point apply(Point point) {
                return point;
            }
        };

        // Add multiple transformations
        Configs configs = Configs.builder()
            .addTransformations(transformation1, transformation2)
            .build();

        // Verify that both transformations were added
        assertEquals(2, configs.getTransformations().size());
        assertSame(transformation1, configs.getTransformations().get(0));
        assertSame(transformation2, configs.getTransformations().get(1));
    }

    @Test
    void testSetTransformations() {
        Transformation transformation1 = new Transformation() {
            @Override
            public Point apply(Point point) {
                return point;
            }
        };
        Transformation transformation2 = new Transformation() {
            @Override
            public Point apply(Point point) {
                return point;
            }
        };
        List<Transformation> transformations = List.of(transformation1, transformation2);

        // Set the transformations list directly
        Configs configs = Configs.builder()
            .setTransformations(transformations)
            .build();

        // Verify that the transformations list was set correctly
        assertEquals(2, configs.getTransformations().size());
        assertSame(transformation1, configs.getTransformations().get(0));
        assertSame(transformation2, configs.getTransformations().get(1));
    }

    @Test
    void testAffineCoefficientsInitialization() {
        // Use the builder to create a Configs instance with custom affineCount
        Configs configs = Configs.builder()
            .affineCount(5)
            .build();

        // Verify that the coefficients array has the expected length
        assertEquals(5, configs.getCoefficients().length);
    }
}
