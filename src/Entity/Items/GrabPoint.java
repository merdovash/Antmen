package Entity.Items;

public class GrabPoint {
    private int x;
    private int y;
    private byte side;
    private int width;

    public GrabPoint(int x, int y, boolean side, int width) {
        this.x = x;
        this.y = y;
        if (side) {
            this.side = -1;
        } else {
            this.side = 1;
        }
        this.width = width;
    }

    public void update(int x, int y, boolean side) {
        this.x = x;
        this.y = y;
        if (side) {
            this.side = 1;
        } else {
            this.side = -1;
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getSide() {
        return side;
    }
}
