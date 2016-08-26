package Entity.Items;

import Entity.Items.Armor.Headset.Helmet;
import Entity.Items.Loot.Branch;
import Entity.Items.Weapons.Knifes.Knife;
import Entity.Items.Weapons.Swords.Sword;

import java.util.ArrayList;

public class ItemList {
    private static ArrayList<Integer> id;
    private static ArrayList<String> adressImage;
    private static ArrayList<Item> item;

    static {
        id = new ArrayList<>();
        adressImage = new ArrayList<>();
        item = new ArrayList<>();
        add(1, "/Items/Loot/branch.gif", new Branch());
        add(2, "/Items/Armory/Headset/helmetIco.gif", new Helmet());
        add(3, "/Items/Weapons/Swords/swordIco.gif", new Sword());
        add(4, "/Items/Weapons/Knifes/knifeIco.gif", new Knife());
    }

    private static void add(int id, String adress, Item item) {
        ItemList.id.add(id);
        adressImage.add(adress);
        ItemList.item.add(item);
    }

    public static String getAddressImage(int id) {
        return adressImage.get(id - 1);
    }

    public static Item getItem(int id) {
        return item.get(id - 1);
    }
}
