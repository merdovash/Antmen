package Entity.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad on 08.08.16.
 */
public class ItemList {
    private static final Map<Integer, String> list;
    static {
        list = new HashMap<>();
        list.put(1,"/Items/Loot/branch.gif");
    }

    public static String getString(int id){
        return list.get(id);
    }
}
