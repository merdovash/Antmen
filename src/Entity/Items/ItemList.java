package Entity.Items;

import Entity.Items.Armor.Headset.Helmet;
import Entity.Items.Loot.Branch;

import java.util.ArrayList;

public class ItemList {
    private static ArrayList<Integer> id;
    private static ArrayList<String> adressImage;
    private static ArrayList<Item> item;
    private static int size;
    static {
        id = new ArrayList<>();
        adressImage = new ArrayList<>();
        item = new ArrayList<>();
        size = 0;
        add(1, "/Items/Loot/branch.gif", new Branch());
        add(2, "/Items/Headset/helmet.gif", new Helmet());
    }

    private static void add(int id, String adress, Item item) {
        ItemList.id.add(id);
        adressImage.add(adress);
        ItemList.item.add(item);
        size++;
    }

    static String getAddressImage(int id) {
        for (int i = 0; i < size; i++) {
            if (ItemList.id.get(i) == id) {
                return (adressImage.get(i));
            }
        }
        return null;
    }

    public static Item getItem(int id) {
        return item.get(id - 1);
    }
}
