package Entity.Enemies;

import java.util.ArrayList;

public class DropList {
    private ArrayList<Integer> id;
    private ArrayList<Double> chance;

    private int size;

    public DropList() {
        id = new ArrayList<>();
        chance = new ArrayList<>();
        size = 0;
    }

    public void add(double chance, int id) {
        this.chance.add(chance);
        this.id.add(id);
        size++;
    }

    public int size() {
        return size;
    }

    public double getChance(int n) {
        return chance.get(n);
    }

    public int getID(int n) {
        return id.get(n);
    }
}
