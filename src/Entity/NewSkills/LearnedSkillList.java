package Entity.NewSkills;

import java.util.ArrayList;

public class LearnedSkillList {
    private ArrayList<Integer> id;
    private ArrayList<Integer> level;
    private ArrayList<Integer> maxLevel;

    public LearnedSkillList() {
        id = new ArrayList<>();
        level = new ArrayList<>();
    }

    public void add(int id, int level) {
        if (!have(id)) {
            this.id.add(id);
            this.level.add(level);
        }
    }

    public boolean add(int id) {
        if (!have(id)) {
            this.id.add(id);
            this.level.add(1);
            return true;
        } else {
            return upgrade(id);
        }
    }

    private boolean upgrade(int id) {
        if (level.get(this.id.indexOf(id)) < maxLevel.get(this.id.indexOf(id))) {
            this.level.set(this.id.indexOf(id), level.get(this.id.indexOf(id)) + 1);
            return true;
        }
        return false;
    }

    public boolean have(int id) {
        for (int i = 0; i < this.id.size(); i++) {
            if (this.id.get(i) == id) {
                return true;
            }
        }
        return false;
    }
}
