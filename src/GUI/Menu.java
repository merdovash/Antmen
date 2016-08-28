package GUI;

public class Menu extends Button {

    public Menu(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    private Window window;

    public void add(Window w) {
        window = w;
    }

    public Window activate() {
        return window;
    }
}
