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
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PaintPane extends BorderPane {

	// BackEnd
	private final CanvasState canvasState;

	// Constantes de reescalado
	private final static double SCALE_POSITIVE_MULTIPLIER = 1.25;
	private final static double SCALE_NEGATIVE_MULTIPLIER = 0.75;

	private final static String CONTROLLERS_BACKGROUND_COLOR = "-fx-background-color: #999";
	private static final int CONTROLLERS_PADDING = 5;

	// Checkboxes barra superior
	private static final int HBOX_SPACING = 10;

	// Canvas y relacionados
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 600;
	private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private static final int BUTTON_MIN_WIDTH = 90;
	private static final int VBOX_SPACING = 10;
	private static final int BUTTON_BOX_PREF_WIDTH = 100;

	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final FigureToggleButton rectangleButton = new RectangleButton("Rectángulo");
	private final FigureToggleButton circleButton = new CircleButton("Círculo");
	private final FigureToggleButton squareButton = new SquareButton("Cuadrado");
	private final FigureToggleButton ellipseButton = new EllipseButton("Elipse");
	private final ToggleButton groupButton = new ToggleButton("Agrupar");
	private final ToggleButton ungroupButton = new ToggleButton("Desagrupar");
	private final ToggleButton rotateButton = new ToggleButton("Girar D");
	private final ToggleButton flipHButton = new ToggleButton("Voltear H");
	private final ToggleButton flipVButton = new ToggleButton("Voltear V");
	private final ToggleButton scalePButton = new ToggleButton("Escalar +");
	private final ToggleButton scaleNButton = new ToggleButton("Escalar -");
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

	// Checkboxes Barra Horizontal Superior.
	private final CheckBox shadowBox = new CheckBox("Sombra");
	private final CheckBox gradientBox = new CheckBox("Gradiente");
	private final CheckBox beveledBox = new CheckBox("Biselado");

	// Selector de color de relleno
	private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar varias figuras
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
		initializeUI();
		setEventHandlers();
	}

	private void initializeUI() {
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, groupButton, ungroupButton, rotateButton, flipHButton, flipVButton, scalePButton, scaleNButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(BUTTON_MIN_WIDTH);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(VBOX_SPACING);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(CONTROLLERS_PADDING));
		buttonsBox.setStyle(CONTROLLERS_BACKGROUND_COLOR);
		buttonsBox.setPrefWidth(BUTTON_BOX_PREF_WIDTH);
		gc.setLineWidth(1);

		HBox stylesBox = new HBox(HBOX_SPACING);
		Label stylesLabel = new Label("Efectos:");
		CheckBox[] stylesArr = {shadowBox, gradientBox, beveledBox};
		checkBoxEffectMap.put(shadowBox, Effect.SHADOW);
		checkBoxEffectMap.put(gradientBox, Effect.GRADIENT);
		checkBoxEffectMap.put(beveledBox, Effect.BEVELED);
		for (CheckBox checkBox : stylesArr) {
			setCheckBoxActions(checkBox);
		}
		stylesBox.getChildren().add(stylesLabel);
		stylesBox.getChildren().addAll(stylesArr);
		stylesBox.setPadding(new Insets(CONTROLLERS_PADDING));
		stylesBox.setStyle(CONTROLLERS_BACKGROUND_COLOR);
		stylesBox.setAlignment(Pos.CENTER);

		setTop(stylesBox);
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void setEventHandlers() {
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

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
			for (FigureToggleButton figureToggleButton : arr) {
				if (figureToggleButton.isSelected()) {
					newFigure = figureToggleButton.createFigure(startPoint, endPoint);
				}
			}
			if (newFigure != null) {
				figureColorMap.put(newFigure, fillColorPicker.getValue());
				figureEffectsMap.put(newFigure, new TreeSet<>());
				for (CheckBox checkBox : checkBoxEffectMap.keySet()) {
					if (checkBox.isSelected()) {
						figureEffectsMap.get(newFigure).add(checkBoxEffectMap.get(checkBox));
					}
				}
				canvasState.add(newFigure);
			}
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (Figure figure : canvasState.figures()) {
				if (figure.belongs(eventPoint)) {
					found = true;
					label.append(figure);
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				if (imaginaryRectangle != null) {
					printSelectionLabelIf(figure -> figure.isInRectangle(imaginaryRectangle));
					canvasState.remove(imaginaryRectangle);
					imaginaryRectangle = null;
				} else {
					selectedFigures = new ArrayList<>();
					Point eventPoint = new Point(event.getX(), event.getY());
					printSelectionLabelIf(figure -> figure.belongs(eventPoint) && selectedFigures.isEmpty());
				}
				if (!selectedFigures.isEmpty()) {
					updateCheckBoxState();
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				if (!selectedFigures.isEmpty()) {
					double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
					double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
					for (Figure figure : selectedFigures) {
						figure.move(diffX, diffY);
					}
				} else {
					if (imaginaryRectangle != null) {
						canvasState.remove(imaginaryRectangle);
					}
					imaginaryRectangle = new ImaginaryRectangle(startPoint, eventPoint);
					canvasState.add(imaginaryRectangle);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> doToSelectedFigures(figure -> {
			canvasState.remove(figure);
			figureColorMap.remove(figure);
			figureEffectsMap.remove(figure);
		}));

		ungroupButton.setOnAction(event -> doToSelectedFigures(figure -> {
			canvasState.remove(figure);
			canvasState.addAll(figure.getFigures());
		}));


		groupButton.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				GroupedFigure groupedFigure = new DrawableGroupedFigure(selectedFigures);
				canvasState.removeAll(selectedFigures);
				canvasState.add(groupedFigure);
				selectedFigures = new ArrayList<>();
				redrawCanvas();
			}
		});

		rotateButton.setOnAction(event -> doToSelectedFigures(Figure::rotate));
		flipHButton.setOnAction(event -> doToSelectedFigures(Figure::flipH));
		flipVButton.setOnAction(event -> doToSelectedFigures(Figure::flipV));
		scalePButton.setOnAction(event -> doToSelectedFigures(figure -> figure.scale(SCALE_POSITIVE_MULTIPLIER)));
		scaleNButton.setOnAction(event -> doToSelectedFigures(figure -> figure.scale(SCALE_NEGATIVE_MULTIPLIER)));

	}

	//Se encarga de dibujar todas las figuras en el canvas.
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Figure nonDrawableFigure : canvasState.figures()) {
			// Castear a DrawableFigure sabiendo que se instanció como una clase que implementa DrawableFigure
			DrawableFigure figure = (DrawableFigure) nonDrawableFigure;
			figure.draw(gc, figureColorMap, figureEffectsMap, selectedFigures.contains(figure));
		}
	}

	// Agrega una figura a la lista de figuras seleccionadas y actualiza el label de seleccion.
	private void addSelectedFigure(StringBuilder label, Figure figure) {
		selectedFigures.add(figure);
		label.append(figure);
	}

	// Imprime el label de seleccion en la barra de estado.
	private void printSelectionLabel(Boolean found, String label) {
		if (found) {
			statusPane.updateStatus(label);
		} else {
			statusPane.updateStatus("Ninguna figura encontrada");
		}
	}

	// Setea las acciones de los checkboxes.
	private void setCheckBoxActions(CheckBox checkBox) {
		checkBox.setOnAction(event -> {
			if (!selectedFigures.isEmpty()) {
				for (Figure figure : selectedFigures) {
					updateEffectsRecursively(figure, checkBox.isSelected(), checkBoxEffectMap.get(checkBox));
				}
				redrawCanvas();
			}
		});
	}

	// Actualiza los efectos de una figura y sus componentes recursivamente.
	private void updateEffectsRecursively(Figure figure, boolean selected, Effect effect) {
		for (Figure component : figure.getFigures()) {
			// Si esta en el mapa entonces es una figura simple
			if (figureEffectsMap.containsKey(component)) {
				if (selected) {
					figureEffectsMap.get(component).add(effect);
				} else {
					figureEffectsMap.get(component).remove(effect);
				}
			} else {
				updateEffectsRecursively(component, selected, effect);
			}
		}
	}

	// Actualiza el estado de todos los checkboxes utilizando el metodo updateSingleCheckBox.
	private void updateCheckBoxState() {
		for (CheckBox checkBox : checkBoxEffectMap.keySet()) {
			updateSingleCheckBox(checkBox);
		}
	}

	//Actualiza el estado de un checkbox en particular en base a lo que devuelva el getStatus de todas las figuras seleccionadas.
	private void updateSingleCheckBox(CheckBox checkBox) {
		Status firstStatus = ((DrawableFigure) selectedFigures.get(0)).getStatus(checkBoxEffectMap.get(checkBox), figureEffectsMap);
		for (Figure figure : selectedFigures) {
			if (((DrawableFigure) figure).getStatus(checkBoxEffectMap.get(checkBox), figureEffectsMap) != firstStatus || firstStatus == Status.UNDETERMINED) {
				checkBox.setSelected(false);
				checkBox.setIndeterminate(true);
				return;
			}
		}
		checkBox.setIndeterminate(false);
		checkBox.setSelected(firstStatus == Status.SELECTED);
	}

	// Utilizando la interfaz funcional Consumer, este metodo ejecuta una accion sobre todas las figuras seleccionadas.
	private void doToSelectedFigures(Consumer<Figure> f) {
		if (!selectedFigures.isEmpty()) {
			for (Figure figure : selectedFigures) {
				f.accept(figure);
			}
			selectedFigures = new ArrayList<>();
			redrawCanvas();
		}
	}

	// Utiliza la interfaz Predicate para fijarse si una figura cumple con cierta condicion y en caso de ser asi, la agrega a addSelectedFigure.
	private void printSelectionLabelIf(Predicate<Figure> predicate) {
		boolean found = false;
		StringBuilder label = new StringBuilder("Se seleccionó: ");
		for (Figure figure : canvasState.figures()) {
			if (predicate.test(figure)) {
				found = true;
				addSelectedFigure(label, figure);
			}
		}
		printSelectionLabel(found, label.toString());
	}

}
