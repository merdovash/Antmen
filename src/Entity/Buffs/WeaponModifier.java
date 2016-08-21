package Entity.Buffs;

public class WeaponModifier {
    public static final int NORMAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int WIND = 3;
    public static final int EARTH = 4;
    public static final int HOLY = 5;
    public static final int SHADOW = 6;

    private int damage;
    private int type;
    private double power;

    public WeaponModifier(int damage, int type, double power) {
        this.damage = damage;
        this.type = type;
        this.power = power;
    }

    public double[] getDamage() {
        return new double[]{damage, type, power};
    }

}
