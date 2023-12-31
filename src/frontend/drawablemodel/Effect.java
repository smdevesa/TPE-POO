package frontend.drawablemodel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//Enum para diferenciar y aplicar los efectos a las figuras.
public enum Effect {
    SHADOW {
        @Override
        void apply(DrawableStyleableFigure figure, GraphicsContext gc, Color color) {
            figure.drawShadow(gc, color);
        }
    },
    BEVELED {
        @Override
        void apply(DrawableStyleableFigure figure, GraphicsContext gc, Color color) {
            figure.drawBeveled(gc, color);
        }
    },
    GRADIENT {
        @Override
        void apply(DrawableStyleableFigure figure, GraphicsContext gc, Color color) {
            figure.drawGradient(gc, color);
        }
    };

    abstract void apply(DrawableStyleableFigure figure, GraphicsContext gc, Color color);
}
