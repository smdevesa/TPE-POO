package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.drawablemodel.*;
import frontend.figurebutton.FigureToggleButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import frontend.figurebutton.*;

import java.util.*;

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

	private final CheckBox shadowBox = new CheckBox("Sombra");
	private final CheckBox gradientBox = new CheckBox("Gradiente");
	private final CheckBox beveledBox = new CheckBox("Biselado");

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

	// Efectos de cada figura
	private final Map<Figure, SortedSet<Effect>> figureEffectsMap = new HashMap<>();

	// Relacion de checkboxes con efectos
	private final Map<CheckBox, Effect> checkBoxEffectMap = new HashMap<>();

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

		HBox stylesBox = new HBox(4);
		Label stylesLabel = new Label("Efectos:");
		CheckBox[] stylesArr = {shadowBox, gradientBox, beveledBox};
		checkBoxEffectMap.put(shadowBox, Effect.SHADOW);
		checkBoxEffectMap.put(gradientBox, Effect.GRADIENT);
		checkBoxEffectMap.put(beveledBox, Effect.BEVELED);
		for(CheckBox checkBox : stylesArr) {
			setCheckBoxActions(checkBox);
		}
		stylesBox.getChildren().add(stylesLabel);
		stylesBox.getChildren().addAll(stylesArr);
		stylesBox.setPadding(new Insets(5));
		stylesBox.setStyle("-fx-background-color: #999");
		stylesBox.setPrefWidth(100);
		stylesBox.setSpacing(10);
		stylesBox.setAlignment(Pos.CENTER);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
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
			if(newFigure != null) {
				figureColorMap.put(newFigure, fillColorPicker.getValue());
				figureEffectsMap.put(newFigure, new TreeSet<>());
				for(CheckBox checkBox : stylesArr) {
					if(checkBox.isSelected()) {
						figureEffectsMap.get(newFigure).add(checkBoxEffectMap.get(checkBox));
					}
				}
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
				if(!selectedFigures.isEmpty()) {
					updateCheckBoxState();
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
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
					figureColorMap.remove(figure);
					figureEffectsMap.remove(figure);
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

		setTop(stylesBox);
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure nonDrawableFigure : canvasState.figures()) {
			// Castear a DrawableFigure sabiendo que se instanció como una clase que implementa DrawableFigure
			DrawableFigure figure = (DrawableFigure) nonDrawableFigure;
			figure.draw(gc, figureColorMap, figureEffectsMap, selectedFigures.contains(figure));
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

	private void setCheckBoxActions(CheckBox checkBox) {
		checkBox.setOnAction(event -> {
			if(!selectedFigures.isEmpty()) {
				for(Figure figure : selectedFigures) {
					updateEffectsRecursively(figure, checkBox.isSelected(), checkBoxEffectMap.get(checkBox));
				}
				redrawCanvas();
			}
		});
	}

	private void updateEffectsRecursively(Figure figure, boolean selected, Effect effect) {
		for(Figure component : figure.getFigures()) {
			// Si esta en el mapa entonces es una figura simple
			if(figureEffectsMap.containsKey(component)) {
				if(selected) {
					figureEffectsMap.get(component).add(effect);
				}
				else {
					figureEffectsMap.get(component).remove(effect);
				}
			}
			else {
				updateEffectsRecursively(component, selected, effect);
			}
		}
	}


	private void updateCheckBoxState() {
		for(CheckBox checkBox : checkBoxEffectMap.keySet()) {
			Status firstStatus = ((DrawableFigure)selectedFigures.get(0)).getStatus(checkBoxEffectMap.get(checkBox), figureEffectsMap);
			for(Figure figure : selectedFigures) {
				if(((DrawableFigure) figure).getStatus(checkBoxEffectMap.get(checkBox), figureEffectsMap) != firstStatus || firstStatus == Status.UNDETERMINED) {
					checkBox.setIndeterminate(true);
					return;
				}
			}
			checkBox.setIndeterminate(false);
			checkBox.setSelected(firstStatus == Status.SELECTED);
		}
	}


}
