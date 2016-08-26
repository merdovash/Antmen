package Entity.Items.Loot;


public class Branch extends Loot {


    public Branch() {
        adress = "/Items/Loot/branch.gif";
        ID=1;
        weight = 1;
        super.init();
    }


    @Override
    public Object[][] getBuff() {
        return new Object[0][];
    }

    @Override
    public long[] trajectory(long X) {
        return new long[0];
    }
}
