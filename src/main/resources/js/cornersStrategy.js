var MoveStrategy = Java.type("move.MoveStrategy");

var BoardPosition = Java.type("model.BoardPosition");
var BoardEntity = Java.type("model.BoardEntity");
var Board = Java.type("model.Board");

var CornersStrategy = Java.extend(MoveStrategy, {
    nextMove: function(boardEntity, boardSituation) {
        for(var x = 0 ; x < boardSituation.getSize() ; x++) {
            if(boardSituation.getEntityAt(new BoardPosition(x, 0)) === BoardEntity.EMPTY) {
                return new BoardPosition(x, 0);
            }
            if(boardSituation.getEntityAt(new BoardPosition(x, boardSituation.getSize()-1)) === BoardEntity.EMPTY) {
                return new BoardPosition(x, boardSituation.getSize()-1);
            }
        }
        for(var y = 0 ; y < boardSituation.getSize() ; y++) {
            if(boardSituation.getEntityAt(new BoardPosition(0, y)) === BoardEntity.EMPTY) {
                return new BoardPosition(0, y);
            }
            if(boardSituation.getEntityAt(new BoardPosition(boardSituation.getSize()-1,y)) === BoardEntity.EMPTY) {
                return new BoardPosition(boardSituation.getSize()-1,y);
            }
        }

        var randomX, randomY;

        do {
            randomX = Math.floor(Math.random() * boardSituation.getSize());
            randomY = Math.floor(Math.random() * boardSituation.getSize());
        } while(boardSituation.getEntityAt(new BoardPosition(randomX, randomY)) !== BoardEntity.EMPTY);

        return new BoardPosition(randomX, randomY);

    },

    getIdentifier: function() {
        return "zajmowanie rogów";
    }
});

var strategy = new CornersStrategy();
