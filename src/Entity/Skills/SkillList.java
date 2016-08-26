package Entity.Skills;

import Entity.Skills.PhysicalSkill.BasicAttack;
import Entity.Skills.PhysicalSkill.MultiAttack;
import Entity.Skills.Spells.BuffingSpell.IncreasePower;
import Entity.Skills.Spells.FireBall.FireBall;
import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SkillList {
    private static int FIREBALL = 0;

    private ArrayList<SpellManageable[]> skills;
    private ArrayList<int[]> place;
    private ArrayList<Integer> levels;

    public SkillList() {

        skills = new ArrayList<SpellManageable[]>();
        place = new ArrayList<>();
        levels = new ArrayList<>();
        //FireBall
        skills.add(new SpellManageable[]{
                new FireBall(0),
                new FireBall(1),
                new FireBall(2),
                new FireBall(3),
                new FireBall(4),
                new FireBall(5),
                new FireBall(6),
                new FireBall(7),
                new FireBall(8),
                new FireBall(9),
                new FireBall(10),
        });
        place.add(new int[]{(int) (500 * GamePanel.SCALE), (int) (250 * GamePanel.SCALE)});
        levels.add(0);
        //Basic Attack
        skills.add(new SpellManageable[]{
                new BasicAttack()
        });
        place.add(new int[]{(int) (555 * GamePanel.SCALE), (int) (250 * GamePanel.SCALE)});
        levels.add(0);
        //multi Attack
        skills.add(new SpellManageable[]{
                new MultiAttack(0),
                new MultiAttack(1),
                new MultiAttack(2),
                new MultiAttack(3),
        });
        place.add(new int[]{(int) (610 * GamePanel.SCALE), (int) (250 * GamePanel.SCALE)});
        levels.add(1);
        //buff
        skills.add(new SpellManageable[]{
                new IncreasePower(0),
                new IncreasePower(1),
                new IncreasePower(2),
                new IncreasePower(3),
        });
        place.add(new int[]{(int) (665 * GamePanel.SCALE), (int) (250 * GamePanel.SCALE)});
        levels.add(1);


    }

    public BufferedImage getImage(int i) {
        return skills.get(i)[levels.get(i)].getIco();
    }

    public int size() {
        return skills.size();
    }

    public int getX(int i) {
        return place.get(i)[0];
    }

    public int getY(int i) {
        return place.get(i)[1];
    }
}
