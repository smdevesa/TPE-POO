package frontend.drawablemodel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum Effect {
    SHADOW {
        @Override
        void apply(DrawableFigure figure, GraphicsContext gc, Color color) {
            figure.drawShadow(gc, color);
        }
    },
    GRADIENT {
        @Override
        void apply(DrawableFigure figure, GraphicsContext gc, Color color) {
            figure.drawGradient(gc, color);
        }
    },
    BEVELED {
        @Override
        void apply(DrawableFigure figure, GraphicsContext gc, Color color) {
            figure.drawBeveled(gc, color);
        }
    };

    abstract void apply(DrawableFigure figure, GraphicsContext gc, Color color);
}
