package manager;


import model.Board;
import model.BoardEntity;
import model.BoardPosition;
import move.MoveStrategy;

import static javafx.controller.MainController.board;

public class GameManager {

    private MoveStrategy enemyMoveStrategy;

    private Board gameBoard;

    private int boardSize = 5;

    private BoardEntity playerEntity = BoardEntity.X;

    private BoardEntity aiEntity = BoardEntity.O;


    public GameManager() {

    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void startNewGame() {
        this.gameBoard = new Board(boardSize);
    }

    public void executePlayerMove(BoardPosition boardPosition){
        gameBoard.setEntityAt(boardPosition, playerEntity);
    }

    public void executeAiMove() {
        BoardPosition boardPosition = enemyMoveStrategy.nextMove(aiEntity, gameBoard);
        gameBoard.setEntityAt(boardPosition, aiEntity);
        board[boardPosition.getX()][boardPosition.getY()].drawO();
    }

    public int getNumbersOfEmptyPlaces(){
        return gameBoard.getNumberOfEmptyPlaces();
    }

    public void setEnemyMoveStrategy(MoveStrategy enemyMoveStrategy) {
        this.enemyMoveStrategy = enemyMoveStrategy;
    }
}


