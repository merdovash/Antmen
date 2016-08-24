package Entity.Buffs;

public interface Buffable {

    void addBuff(Buff b);

    default double newValue(double oldValue, int buff, Buff b) {
        return (oldValue + b.getBuff(buff, 0)) * b.getBuff(buff, 1);
    }

    default double newRValue(double oldValue, int buff, Buff b) {
        return (oldValue - b.getBuff(buff, 0)) / b.getBuff(buff, 1);
    }
}
