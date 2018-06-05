package tic_tac_toe.strategy;


import tic_tac_toe.model.Board;
import tic_tac_toe.model.BoardPosition;

public interface ComputerStrategy {
    BoardPosition getNextAIMove(Board boardSituation);
    String getStrategyName();
}


