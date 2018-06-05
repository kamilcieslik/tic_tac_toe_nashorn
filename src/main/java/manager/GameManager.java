package manager;


import model.Board;
import model.BoardPosition;
import strategy.ComputerStrategy;

import static javafx.controller.MainController.board;

public class GameManager {

    private ComputerStrategy computerStrategy;

    private Board gameBoard;

    public GameManager() {

    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void startGame() {
        int boardSize = 5;
        this.gameBoard = new Board(boardSize);
    }

    public void executePlayerMove(BoardPosition boardPosition){
        gameBoard.setSymbol(boardPosition, "X");
    }

    public void executeAIMove() {
        BoardPosition boardPosition = computerStrategy.getNextAIMove(gameBoard);
        gameBoard.setSymbol(boardPosition, "O");
        board[boardPosition.getX()][boardPosition.getY()].drawO();
    }

    public void setComputerStrategy(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }
}


