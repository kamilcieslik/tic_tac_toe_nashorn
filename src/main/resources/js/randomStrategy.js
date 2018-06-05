var MoveStrategy = Java.type("move.MoveStrategy");

var BoardPosition = Java.type("model.BoardPosition");
var BoardEntity = Java.type("model.BoardEntity");
var Board = Java.type("model.Board");

var RandomStrategy = Java.extend(MoveStrategy, {
    nextMove: function(boardEntity, boardSituation) {

        var randomX, randomY;

        do {
            randomX = Math.floor(Math.random() * boardSituation.getSize());
            randomY = Math.floor(Math.random() * boardSituation.getSize());
        } while(boardSituation.getEntityAt(new BoardPosition(randomX, randomY)) !== BoardEntity.EMPTY);

        return new BoardPosition(randomX, randomY);
    },
    getIdentifier: function() {
        return "losowa miejsce";
    }
});

var strategy = new RandomStrategy();

