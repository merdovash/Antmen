package Entity.Players;

import java.io.Serializable;

public class Param implements Serializable {
    private int value;

    Param(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    public void addAbs(int add) {
        value += add;
    }
}
