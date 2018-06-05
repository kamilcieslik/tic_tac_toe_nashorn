var ComputerStrategy = Java.type("strategy.ComputerStrategy");
var BoardPosition = Java.type("model.BoardPosition");

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
