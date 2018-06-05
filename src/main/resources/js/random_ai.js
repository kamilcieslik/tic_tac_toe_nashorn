var ComputerStrategy = Java.type("tic_tac_toe.strategy.ComputerStrategy");
var BoardPosition = Java.type("tic_tac_toe.model.BoardPosition");

var RandomAI = Java.extend(ComputerStrategy, {
    getNextAIMove: function (boardSituation) {

        var randomX, randomY;

        do {
            randomX = Math.floor(Math.random() * boardSituation.getSize());
            randomY = Math.floor(Math.random() * boardSituation.getSize());
        } while (boardSituation.getSymbol(new BoardPosition(randomX, randomY)) !== " ");

        return new BoardPosition(randomX, randomY);
    },

    getStrategyName: function () {
        return "Random AI - kółko w losowym miejscu";
    }
});

var strategy = new RandomAI();
