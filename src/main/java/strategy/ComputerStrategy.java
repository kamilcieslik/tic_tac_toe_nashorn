package strategy;


import model.Board;
import model.BoardPosition;

public interface ComputerStrategy {
    BoardPosition getNextAIMove(Board boardSituation);
    default String getStrategyName() { return "Strategy"; }
}


