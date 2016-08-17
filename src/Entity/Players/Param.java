package Entity.Players;

public class Param {
    private int value;

    Param(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void addAbs(int add) {
        value += add;
    }
}
