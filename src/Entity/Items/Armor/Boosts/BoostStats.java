package Entity.Items.Armor.Boosts;


import Entity.Items.Armor.Boosts.HealthRegen.HealthRegen;

public class BoostStats implements HealthRegen {

    public double hr;

    private int speed;

    public void getBuffs() {
        hr = getHealthRegen();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
