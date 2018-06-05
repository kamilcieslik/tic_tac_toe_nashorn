package model;

public class Combo {
    public Tile[] tiles;
    public Combo(Tile... tiles) {
        this.tiles = tiles;
    }

    public boolean isComplete() {
        if (tiles[0].getValue().isEmpty())
            return false;

        return !tiles[0].getValue().equals(" ") && (tiles[0].getValue().equals(tiles[1].getValue())
                && tiles[0].getValue().equals(tiles[2].getValue())
                && tiles[0].getValue().equals(tiles[3].getValue())
                && tiles[0].getValue().equals(tiles[4].getValue()));
    }
}
