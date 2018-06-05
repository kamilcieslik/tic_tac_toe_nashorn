package javafx.controller;


import javafx.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import jdk.nashorn.api.scripting.ScriptUtils;
import tic_tac_toe.MovementExecutor;
import tic_tac_toe.model.CheckResult;
import tic_tac_toe.model.Field;
import tic_tac_toe.strategy.ComputerStrategy;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController implements Initializable {
    private static CustomMessageBox customMessageBox;

    private List<ComputerStrategy> computerStrategies;
    public static MovementExecutor movementExecutor = new MovementExecutor();

    public static boolean playable = true;
    public static Field[][] board = new Field[5][5];
    private static List<CheckResult> checkResults = new ArrayList<>();

    @FXML
    public Pane flowPaneGameBoard;

    @FXML
    public Button buttonRestart;

    @FXML
    public ComboBox<ComputerStrategy> comboBoxStrategies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        initBoard();

        computerStrategies = new ArrayList<>();

        movementExecutor.startGame();

        loadComputerStrategies(new File("src/main/resources/js"));
        comboBoxStrategies.getItems().addAll(computerStrategies);
        comboBoxStrategies.getSelectionModel().select(0);
        movementExecutor.setComputerStrategy(comboBoxStrategies.getSelectionModel().getSelectedItem());

        comboBoxStrategies.setCellFactory(listView -> new ListCell<ComputerStrategy>() {
            @Override
            protected void updateItem(ComputerStrategy strategy, boolean empty) {
                super.updateItem(strategy, empty);
                if (strategy == null || empty) {
                    setGraphic(null);
                } else {
                    setText(strategy.getStrategyName());
                }
            }
        });

        comboBoxStrategies.setConverter(new StringConverter<ComputerStrategy>() {
            @Override
            public String toString(ComputerStrategy computerStrategy) {
                return computerStrategy == null ? null : computerStrategy.getStrategyName();
            }

            @Override
            public ComputerStrategy fromString(String string) {
                return null;
            }
        });
    }

    public static void checkState() {
        for (CheckResult checkResult : checkResults) {
            if (checkResult.isComplete()) {
                playable = false;
                checkResult(checkResult);
                return;
            }
        }

        if (movementExecutor.getGameBoard().getNumberOfEmptyPlaces() == 0) {
            playable = false;
            customMessageBox.showMessageBox(Alert.AlertType.INFORMATION, "Informacja końcowa",
                    "Rozgrywka zakończona",
                    "Gra nie została rozstrzygnięta - remis.").showAndWait();
        }
    }

    private static void checkResult(CheckResult checkResult) {
        if (checkResult.fields[0].getSymbolValue().equals("X")) {
            customMessageBox.showMessageBox(Alert.AlertType.INFORMATION, "Informacja końcowa",
                    "Rozgrywka zakończona",
                    "Gracz wygrał z komputerem.").showAndWait();
        }
        if (checkResult.fields[0].getSymbolValue().equals("O")) {
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

        movementExecutor.startGame();
    }

    public void comboBoxStrategies_onAction() {
        movementExecutor.setComputerStrategy(comboBoxStrategies.getSelectionModel().getSelectedItem());
    }

    private void initBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = new Field(i, j);
                flowPaneGameBoard.getChildren().add(field);
                board[i][j] = field;
            }
        }

        for (int y = 0; y < 5; y++) {
            checkResults.add(new CheckResult(board[0][y], board[1][y], board[2][y], board[3][y], board[4][y]));
        }

        for (int x = 0; x < 5; x++) {
            checkResults.add(new CheckResult(board[x][0], board[x][1], board[x][2], board[x][3], board[x][4]));
        }

        checkResults.add(new CheckResult(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]));
        checkResults.add(new CheckResult(board[4][0], board[3][1], board[2][2], board[1][3], board[0][4]));
    }

    private void loadComputerStrategies(File directory) {
        File[] scriptFiles = directory.listFiles();
        computerStrategies = Stream.of(Objects.requireNonNull(scriptFiles))
                .map(file -> {
                    try {
                        return getComputerStrategy(file);
                    } catch (FileNotFoundException | ScriptException e) {
                        customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                                "Operacja odczytu strategii AI nie powiodła się.",
                                "Powód: " + e.getMessage()).showAndWait();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private ComputerStrategy getComputerStrategy(File file) throws FileNotFoundException, ScriptException {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.eval(new FileReader(file));

        return (ComputerStrategy) ScriptUtils.convert(scriptEngine.eval("strategy"), ComputerStrategy.class);
    }
}