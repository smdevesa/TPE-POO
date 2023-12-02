package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.drawablemodel.*;
import frontend.figurebutton.FigureToggleButton;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import frontend.figurebutton.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final FigureToggleButton rectangleButton = new RectangleButton("Rectángulo");
	private final FigureToggleButton circleButton = new CircleButton("Círculo");
	private final FigureToggleButton squareButton = new SquareButton("Cuadrado");
	private final FigureToggleButton ellipseButton = new EllipseButton("Elipse");
	private final ToggleButton groupButton = new ToggleButton("Agrupar");
	private final ToggleButton ungroupButton = new ToggleButton("Desagrupar");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

	// Selector de color de relleno
	private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private List<Figure> selectedFigures = new ArrayList<>();

	// StatusBar
	private final StatusPane statusPane;

	// Control de rectangulo imaginario
	private ImaginaryRectangle imaginaryRectangle;

	// Colores de relleno de cada figura
	private final Map<Figure, Color> figureColorMap = new HashMap<>();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, groupButton, ungroupButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		canvas.setOnMousePressed(event -> {
			System.out.println("pressed");
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			System.out.println("released");
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return;
			}
			Figure newFigure = null;
			FigureToggleButton[] arr = {rectangleButton, circleButton, squareButton, ellipseButton};
			for(FigureToggleButton figureToggleButton : arr) {
				if(figureToggleButton.isSelected()) {
					newFigure = figureToggleButton.createFigure(startPoint, endPoint);
				}
			}
			figureColorMap.put(newFigure, fillColorPicker.getValue());
			if(newFigure != null) {
				canvasState.addFigure(newFigure);
			}
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figure.belongs(eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			System.out.println("clicked");
			if(selectionButton.isSelected()) {
				if(imaginaryRectangle != null) {
					boolean found = false;
					StringBuilder label = new StringBuilder("Se seleccionó: ");
					for(Figure figure : canvasState.figures()){
						if(figure.isInRectangle(imaginaryRectangle)){
							found = true;
							addSelectedFigure(label, figure);
						}
						printSelectionLabel(found, label.toString());
					}
					canvasState.deleteFigure(imaginaryRectangle);
					imaginaryRectangle = null;
				}
				else {
					selectedFigures = new ArrayList<>();
					Point eventPoint = new Point(event.getX(), event.getY());
					boolean found = false;
					StringBuilder label = new StringBuilder("Se seleccionó: ");
					for (Figure figure : canvasState.figures()) {
						if (figure.belongs(eventPoint) && selectedFigures.isEmpty()) {
							found = true;
							addSelectedFigure(label, figure);
						}
					}
					printSelectionLabel(found, label.toString());
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			System.out.println("dragged");
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				if(!selectedFigures.isEmpty()) {
					double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
					double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
					for(Figure figure : selectedFigures) {
						figure.move(diffX, diffY);
					}
				}
				else {
					if(imaginaryRectangle != null) {
						canvasState.deleteFigure(imaginaryRectangle);
					}
					imaginaryRectangle = new ImaginaryRectangle(startPoint, eventPoint);
					canvasState.addFigure(imaginaryRectangle);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				for(Figure figure : selectedFigures) {
					canvasState.deleteFigure(figure);
				}
				selectedFigures = new ArrayList<>();
				redrawCanvas();
			}
		});

		groupButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				GroupedFigure groupedFigure = new DrawableGroupedFigure(selectedFigures);
				canvasState.removeAll(selectedFigures);
				canvasState.addFigure(groupedFigure);
				selectedFigures = new ArrayList<>();
				redrawCanvas();
			}
		});

		ungroupButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				for(Figure figure : selectedFigures) {
					canvasState.deleteFigure(figure);
					canvasState.addAll(figure.getFigures());
					}
				}
				selectedFigures = new ArrayList<>();
				redrawCanvas();
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure nonDrawableFigure : canvasState.figures()) {
			// Castear a DrawableFigure sabiendo que se instanció como una clase que implementa DrawableFigure
			DrawableFigure figure = (DrawableFigure) nonDrawableFigure;
			if(selectedFigures.contains(figure) || figure.equals(imaginaryRectangle)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			figure.draw(gc, figureColorMap);
		}
	}

	private void addSelectedFigure(StringBuilder label, Figure figure) {
		selectedFigures.add(figure);
		label.append(figure);
	}

	private void printSelectionLabel(Boolean found, String label) {
		if (found) {
			statusPane.updateStatus(label);
		} else {
			statusPane.updateStatus("Ninguna figura encontrada");
		}
	}

}
