package model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Board {

    private final int size;

    private BoardEntity[][] boardState;

    public Board(int size) {
        this.size = size;

        boardState = new BoardEntity[size][size];
        clearBoard();
    }

    public void clearBoard() {
        IntStream.range(0, boardState.length).forEach(x -> Arrays.fill(boardState[x], BoardEntity.EMPTY));
    }

    public int getSize() {
        return size;
    }

    public BoardEntity getEntityAt(BoardPosition boardPosition) {
        return boardState[boardPosition.getX()][boardPosition.getY()];
    }

    public void setEntityAt(BoardPosition boardPosition, BoardEntity playerEntity) {
        boardState[boardPosition.getX()][boardPosition.getY()] = playerEntity;
    }

    public int getNumberOfEmptyPlaces(){
        int number=0;
        for(int i=0;i<5;i++){
            for(int j =0;j<5;j++){
                if(boardState[i][j]==BoardEntity.EMPTY)
                    number++;
            }
        }
        return number;
    }

    public void out(){
        for(int i = 0; i<5;i++){
            for(int j =0 ; j<5 ;j++){
                System.out.println(boardState[i][j] + " "+i+" "+j);
            }
            System.out.println();
        }
    }

}
