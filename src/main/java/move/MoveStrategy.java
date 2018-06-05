package move;


import model.Board;
import model.BoardEntity;
import model.BoardPosition;

public interface MoveStrategy {
    BoardPosition nextMove(BoardEntity boardEntity, Board boardSituation);
    default String getIdentifier() { return "Strategy"; }

}


