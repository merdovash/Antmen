package Entity.Enemies;

import java.util.ArrayList;

/**
 * Created by vlad on 08.08.16.
 */
public class DropList {
    private ArrayList<Double> chance;
    private ArrayList<Integer> itemID;
    private int size;

    public DropList() {
        chance = new ArrayList<>();
        itemID = new ArrayList<>();
        size = 0;
    }

    public void add(double chance, int id) {
        this.chance.add(chance);
        this.itemID.add(id);
        size++;
    }

    public double getChance(int number) {
        return chance.get(number);
    }

    public int getID(int number) {
        return itemID.get(number);
    }

    public int size() {
        return size;
    }
}
