package Entity.Buffs;


import java.io.Serializable;

public class Buff implements Serializable {
    public static final int STR = 0;
    public static final int INT = 1;
    public static final int DEX = 2;
    public static final int VIT = 3;
    public static final int AGI = 4;
    public static final int SPK = 5;
    public static final int ATTACK_SPEED = 6;
    public static final int ATTACK_DMG = 7;
    public static final int HEALTH = 8;
    public static final int SPELL_DMG = 9;
    public static final int SPELL_COOLDOWN = 10;
    public static final int SPELL_MANACOST = 11;
    public static final int MANA_REFILL = 12;
    public static final int MANA = 13;
    public static final int ENERGY = 14;
    public static final int ENERGY_REFILL = 15;
    public static final int HEALTH_REGEN = 16;
    public static final int DEF = 17;

    public static final int STATS = 0;
    public static final int SPELLS = 1;
    public static final int WEAPON = 2;
    public static final int HEADSET = 3;

    private static int size = 18;
    private static int length = 4;

    private double[][] percent;
    private int[][] abs;
    private long[][][] timer;


    public Buff() {
        percent = new double[length][size];
        abs = new int[length][size];
        timer = new long[length][size][2];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < size; j++) {
                percent[i][j] = 0;
                abs[i][j] = 0;
                timer[i][j][0] = 0;

                timer[i][j][1] = 0;
            }
        }

    }

    public void addBuff(int from, int buff, long time, double value) {
        percent[from][buff] = value;
        timer[from][buff][0] = time;
    }

    public void addBuff(int from, int buff, long time, int value) {
        abs[from][buff] = value;
        timer[from][buff][1] = time;
    }

    public void update(long delta) {
        int minus = (int) ((double) delta / 1000000);
        //System.out.println(Arrays.toString(abs[SPELLS]));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < size; j++) {
                if (timer[i][j][0] > 0) {
                    timer[i][j][0] -= minus;
                    if (timer[i][j][0] < 0) {
                        removeBuff(i, j, 0);
                    }
                }
                if (timer[i][j][1] > 0) {
                    timer[i][j][1] -= minus;
                    if (timer[i][j][1] < 0) {
                        removeBuff(i, j, 1);
                    }
                }
            }
        }
    }

    private void removeBuff(int from, int buff, int type) {
        switch (type) {
            case 0:
                timer[from][buff][type] = 0;
                percent[from][buff] = 0;
                break;
            case 1:
                timer[from][buff][type] = 0;
                abs[from][buff] = 0;
                break;
            default:
        }
    }

    public double getBuff(int buff) {
        int temp1 = 0;
        for (int i = 0; i < length; i++) {
            temp1 += abs[i][buff];
        }
        double temp2 = 0;
        for (int i = 0; i < length; i++) {
            temp2 += percent[i][buff];
        }
        return temp1 * (1 + temp2);
    }

    public double getBuff(int buff, int type) {
        switch (type) {
            case 0:
                int temp1 = 0;
                for (int i = 0; i < length; i++) {
                    temp1 += abs[i][buff];
                }
                return temp1;
            case 1:
                double temp2 = 0;

                for (int i = 0; i < length; i++) {
                    temp2 += percent[i][buff];
                }
                return (1 + temp2);
            default:
                return 0;
        }

    }
}
