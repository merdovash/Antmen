package Entity.Players;

import Entity.Items.ItemList;

public class Inventory {
    private int[][] places;
    private int[][] size;
    public int width;
    public int height;

    Inventory() {
        width = 10;
        height = 4;
        places = new int[height][width];
        size = new int[height][width];
    }

    public boolean addItem(int id) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (places[i][j] == 0) {
                    places[i][j] = id;
                    size[i][j] = ItemList.getWeight(id);
                    return true;
                } else if (places[i][j] == id && size[i][j] + ItemList.getWeight(id) < 64) {
                    size[i][j] += 1;
                    return true;
                }
            }
        }
        return false;
    }

    public int getID(int x, int y) {
        return places[y][x];
    }

    public int getSize(int x, int y) {
        return size[y][x];
    }
}
