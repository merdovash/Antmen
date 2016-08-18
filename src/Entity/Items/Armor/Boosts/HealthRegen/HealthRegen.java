package Entity.Items.Armor.Boosts.HealthRegen;

public interface HealthRegen {
    double healthRegen = 1;

    default double getHealthRegen() {
        return healthRegen;
    }
}
