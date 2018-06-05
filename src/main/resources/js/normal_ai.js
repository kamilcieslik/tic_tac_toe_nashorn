var ComputerStrategy = Java.type("tic_tac_toe.strategy.ComputerStrategy");
var BoardPosition = Java.type("tic_tac_toe.model.BoardPosition");

var NormalAI = Java.extend(ComputerStrategy, {
    getNextAIMove: function (boardSituation) {
        for (var x = 0; x < boardSituation.getSize(); x++) {
            if (boardSituation.getSymbol(new BoardPosition(x, 0)) === " ") {
                return new BoardPosition(x, 0);
            }
            if (boardSituation.getSymbol(new BoardPosition(x, boardSituation.getSize() - 1)) === " ") {
                return new BoardPosition(x, boardSituation.getSize() - 1);
            }
        }

        for (var y = 0; y < boardSituation.getSize(); y++) {
            if (boardSituation.getSymbol(new BoardPosition(0, y)) === " ") {
                return new BoardPosition(0, y);
            }
            if (boardSituation.getSymbol(new BoardPosition(boardSituation.getSize() - 1, y)) === " ") {
                return new BoardPosition(boardSituation.getSize() - 1, y);
            }
        }

        var randomX, randomY;

        do {
            randomX = Math.floor(Math.random() * boardSituation.getSize());
            randomY = Math.floor(Math.random() * boardSituation.getSize());
        } while (boardSituation.getSymbol(new BoardPosition(randomX, randomY)) !== " ");

        return new BoardPosition(randomX, randomY);

    },

    getStrategyName: function () {
        return "Normal AI - kółko na brzegach";
    }
});

var strategy = new NormalAI();
