package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Board {

    private final int size;

    private String[][] distributionOfMovements;

    public Board(int size) {
        this.size = size;
        distributionOfMovements = new String[size][size];

        clearBoard();
    }

    public void clearBoard() {
        IntStream.range(0, distributionOfMovements.length).forEach(x -> Arrays.fill(distributionOfMovements[x],
                " "));
    }

    public int getSize() {
        return size;
    }

    public String getSymbol(BoardPosition boardPosition) {
        return distributionOfMovements[boardPosition.getX()][boardPosition.getY()];
    }

    public void setSymbol(BoardPosition boardPosition, String playerEntity) {
        distributionOfMovements[boardPosition.getX()][boardPosition.getY()] = playerEntity;
    }

    public int getNumberOfEmptyPlaces(){
        int number=0;

        for(int i=0;i<5;i++){
            for(int j =0;j<5;j++){
                if(distributionOfMovements[i][j].equals(" "))
                    number++;
            }
        }

        return number;
    }
}
