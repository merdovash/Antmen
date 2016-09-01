package Entity.NewSkills;

import Entity.Players.Stats;
import TileMap.TileMap;

import java.util.ArrayList;
import java.util.Arrays;

public class SkillManager {
    //basic
    private TileMap tileMap;
    private Stats stats;

    //learnedSpells
    private static ArrayList<ArrayList<Skill>> skillList;

    static {
        skillList = new ArrayList<>();

    }

    private LearnedSkillList learned;

    //skills on panel
    private int[] skill;
    private Skill[] skills;

    //cooldown
    private double[][] cooldown;

    //hand
    private boolean[] hands;
    public static final byte UP_LEFT_HAND = 0;
    public static final byte UU_RIGHT_HAND = 1;
    public static final byte DOWN_LEFT_HAND = 2;
    public static final byte DOWN_RIGHT_HAND = 3;

    public SkillManager(TileMap tm, Stats s) {
        tileMap = tm;
        stats = s;
        hands = new boolean[4];
        Arrays.fill(hands, true);

        skill = new int[10];
        cooldown = new double[10][2];
    }

    public void useSpell(int place) {
        if (cooldown[place][0] == 0 && stats.mana.consumpAbs(skills[place].getCost()) && noBusy(skills[place].getHand())) {
            hands[skills[place].getHand()] = false;
            cooldown[place][0] = skills[place].getCooldown();
            cooldown[place][1] = skills[place].getCooldown();
            skills[place].start(stats, tileMap);
        }
    }

    private boolean noBusy(int hand) {
        return hands[hand];
    }

    public boolean learn(int id) {
        return learned.add(id);
    }

    public void set(int id, int place) {

    }

}
