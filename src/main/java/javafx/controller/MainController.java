package javafx.controller;


import javafx.CustomMessageBox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.util.StringConverter;
import manager.GameManager;
import model.Combo;
import model.Tile;
import move.MoveStrategy;
import move.MoveStrategyFromJSFile;


import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController implements Initializable {
    private static CustomMessageBox customMessageBox;

    private List<MoveStrategy> computerStrategies;
    private MoveStrategyFromJSFile mover;
    public static GameManager gameManager = new GameManager();

    public static boolean playable = true;
    public static Tile[][] board = new Tile[5][5];
    private static List<Combo> combos = new ArrayList<>();

    @FXML
    public Pane flowPaneGameBoard;
    private static Pane root;

    @FXML
    public Button buttonRestart;

    @FXML
    public ComboBox<MoveStrategy> comboBoxStrategies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        initBoard();

        root = new Pane();
        flowPaneGameBoard.getChildren().add(root);

        computerStrategies = new ArrayList<>();

        gameManager.startNewGame();
        mover = new MoveStrategyFromJSFile();

        loadFromDirectory(new File("src/main/resources/js"));
        comboBoxStrategies.getItems().addAll(computerStrategies);
        comboBoxStrategies.getSelectionModel().select(0);
        gameManager.setEnemyMoveStrategy(comboBoxStrategies.getSelectionModel().getSelectedItem());

        comboBoxStrategies.setCellFactory(listView -> new ListCell<MoveStrategy>() {
            @Override
            protected void updateItem(MoveStrategy strategy, boolean empty) {
                super.updateItem(strategy, empty);
                if (strategy == null || empty) {
                    setGraphic(null);
                } else {
                    setText(strategy.getIdentifier());
                }
            }
        });

        comboBoxStrategies.setConverter(new StringConverter<MoveStrategy>() {
            @Override
            public String toString(MoveStrategy strategy) {
                return strategy == null ? null : strategy.getIdentifier();
            }

            @Override
            public MoveStrategy fromString(String string) {
                return null;
            }
        });
    }

    public static void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                return;
            }
        }

        if (gameManager.getGameBoard().getNumberOfEmptyPlaces() == 0) {
            playable = false;
            customMessageBox.showMessageBox(Alert.AlertType.INFORMATION, "Informacja końcowa",
                    "Rozgrywka zakończona",
                    "Gra nie została rozstrzygnięta - remis.").showAndWait();
        }
    }

    private static void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStrokeWidth(5);
        line.setStyle("-fx-stroke: red;");
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[4].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[4].getCenterY())));
        timeline.play();
        if (combo.tiles[0].getValue().equals("X")) {
            customMessageBox.showMessageBox(Alert.AlertType.INFORMATION, "Informacja końcowa",
                    "Rozgrywka zakończona",
                    "Gracz wygrał z komputerem.").showAndWait();
        }
        if (combo.tiles[0].getValue().equals("O")) {
            customMessageBox.showMessageBox(Alert.AlertType.INFORMATION, "Informacja końcowa",
                    "Rozgrywka zakończona",
                    "Komputer wygrał z graczem.").showAndWait();
        }
    }

    public void buttonRestart_onAction() {
        playable = true;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j].clear();
            }
        }

        root.getChildren().clear();
        gameManager.startNewGame();
    }

    public void comboBoxStrategies_onAction() {
        gameManager.setEnemyMoveStrategy(comboBoxStrategies.getSelectionModel().getSelectedItem());
    }

    private void initBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(i, j);
                flowPaneGameBoard.getChildren().add(tile);
                board[i][j] = tile;
            }
        }

        for (int y = 0; y < 5; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y], board[3][y], board[4][y]));
        }

        for (int x = 0; x < 5; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2], board[x][3], board[x][4]));
        }

        combos.add(new Combo(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]));
        combos.add(new Combo(board[4][0], board[3][1], board[2][2], board[1][3], board[0][4]));
    }

    private void loadFromDirectory(File directory) {
        File[] scriptFiles = directory.listFiles();
        System.out.println(Arrays.toString(scriptFiles));
        assert scriptFiles != null;
        computerStrategies = Stream.of(scriptFiles)
                .map(file -> {
                    try {
                        return mover.load(file);
                    } catch (FileNotFoundException | ScriptException e) {
                        customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                                "Operacja odczytu strategii AI nie powiodła się.",
                                "Powód: " + e.getMessage()).showAndWait();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}