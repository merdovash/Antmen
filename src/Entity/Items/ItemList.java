package Entity.Items;

import java.util.ArrayList;

public class ItemList {
    private static ArrayList<Integer> id;
    private static ArrayList<String> adressImage;
    private static ArrayList<Integer> weight;
    private static int size;
    static {
        id = new ArrayList<>();
        adressImage = new ArrayList<>();
        weight = new ArrayList<>();
        size = 0;
        add(1, "/Items/Loot/branch.gif", 1);
    }

    private static void add(int id, String adress, int weight) {
        ItemList.id.add(id);
        adressImage.add(adress);
        ItemList.weight.add(weight);
        size++;
    }

    public static String getAddressImage(int id) {
        for (int i = 0; i < size; i++) {
            if (ItemList.id.get(i) == id) {
                return (adressImage.get(i));
            }
        }
        return null;
    }

    public static int getWeight(int id) {
        for (int i = 0; i < size; i++) {
            if (ItemList.id.get(i) == id) {
                return (weight.get(i));
            }
        }
        return 65;
    }
}
