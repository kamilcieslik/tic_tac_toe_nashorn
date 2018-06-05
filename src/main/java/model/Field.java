package model;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static javafx.controller.MainController.*;


public class Field extends StackPane {
    private int x;
    private int y;
    private Text text = new Text();

    public Field(int x, int y) {
        this.x = x;
        this.y= y;
        Rectangle border = new Rectangle(80, 80);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font(50));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        setOnMouseClicked(event -> {
            if (!playable)
                return;

            if (event.getButton() == MouseButton.PRIMARY) {
                if (getSymbolValue().equals("X")|| getSymbolValue().equals("O"))
                    return;

                drawX();
                gameManager.executePlayerMove(new BoardPosition(this.x,this.y));
                checkState();

                if (playable){
                    gameManager.executeAIMove();
                    checkState();
                }
            }
        });
    }

    public String getSymbolValue() {
        return text.getText();
    }

    private void drawX() {
        text.setStyle("-fx-stroke: Black;");
        text.setFill(Color.web("268798"));
        text.setText("X");
    }

    public void drawO() {
        text.setStyle("-fx-stroke: Black;");
        text.setFill(Color.web("ff0000"));
        text.setText("O");
    }

    public void clear() {
        text.setText(" ");
    }
}
