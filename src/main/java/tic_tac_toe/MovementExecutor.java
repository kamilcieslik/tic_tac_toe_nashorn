package tic_tac_toe;


import tic_tac_toe.model.Board;
import tic_tac_toe.model.BoardPosition;
import tic_tac_toe.strategy.ComputerStrategy;

import static javafx.controller.MainController.board;

public class MovementExecutor {

    private ComputerStrategy computerStrategy;

    private Board gameBoard;

    public MovementExecutor() {

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


