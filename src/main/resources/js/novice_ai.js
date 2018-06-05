var ComputerStrategy = Java.type("tic_tac_toe.strategy.ComputerStrategy");
var BoardPosition = Java.type("tic_tac_toe.model.BoardPosition");

var NoviceAI = Java.extend(ComputerStrategy, {
    getNextAIMove: function (boardSituation) {

        for (var x = 0; x < boardSituation.getSize(); x++) {
            for (var y = 0; y < boardSituation.getSize(); y++) {
                if (boardSituation.getSymbol(new BoardPosition(x, y)) === " ") {
                    return new BoardPosition(x, y);
                }
            }
        }

        return null;
    },

    getStrategyName: function () {
        return "Novice AI - kółko w 1. wolnym miejscu";
    }
});

var strategy = new NoviceAI();
