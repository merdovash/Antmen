package Entity.Enemies;

import Main.GamePanel;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Vision {
    private int visionX;
    private int visionY;
    private Color c;

    private int x;
    private int y;
    private int[] rangexLeft;
    private int[] rangexRight;

    private Rectangle[] r;

    private ArrayList<Rectangle> placel;
    private ArrayList<Rectangle> placer;

    private ArrayList<Rectangle> place;


    public Vision(int x, int y) {
        visionX = x;
        visionY = y / 5;
        c = new Color(1f, 1f, 1f, 0.5f);
        r = new Rectangle[visionY];
    }

    public void update(int x, int y, TileMap tm) {
        double xmap = tm.getx();
        double ymap = tm.gety();
        double size = tm.getTileSize() * GamePanel.SCALE;
        this.x = x;
        this.y = y;

        placel = new ArrayList<>();
        placer = new ArrayList<>();
        //4
        for (int i = 0; i < visionY; i++) {
            for (int j = 1; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y + i * 5) / size), (int) ((x + j * 5) / size)) == 0) {
                        placel.add(new Rectangle((int) (x + xmap + j * 5), (int) (y + ymap + 5 * i), 5, 5));
                    }
                }
            }
        }
        //1
        for (int i = 1; i < visionY; i++) {
            for (int j = 0; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y - i * 5) / size), (int) ((x + j * 5) / size)) == 0) {
                        placel.add(new Rectangle((int) (x + xmap + j * 5), (int) (y + ymap - 5 * i), 5, 5));
                    } else {
                        break;
                    }
                }
            }
        }
        //3
        for (int i = 0; i < visionY; i++) {
            for (int j = 0; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y + i * 5) / size), (int) ((x - j * 5) / size)) == 0) {
                        placel.add(new Rectangle((int) (x + xmap - j * 5), (int) (y + ymap + 5 * i), 5, 5));
                    }
                }
            }
        }
        //2
        for (int i = 1; i < visionY; i++) {
            for (int j = 1; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y - i * 5) / size), (int) ((x - j * 5) / size)) == 0) {
                        placel.add(new Rectangle((int) (x + xmap - j * 5), (int) (y + ymap - 5 * i), 5, 5));
                    } else {
                        break;
                    }
                }
            }
        }

        //4
        for (int i = 0; i < visionY; i++) {
            for (int j = 1; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y + j * 5) / size), (int) ((x + i * 5) / size)) == 0) {
                        placer.add(new Rectangle((int) (x + xmap + i * 5), (int) (y + ymap + 5 * j), 5, 5));
                    }
                }
            }
        }
        //1
        for (int i = 1; i < visionY; i++) {
            for (int j = 0; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y - j * 5) / size), (int) ((x + i * 5) / size)) == 0) {
                        placer.add(new Rectangle((int) (x + xmap + i * 5), (int) (y + ymap - 5 * j), 5, 5));
                    } else {
                        break;
                    }
                }
            }
        }
        //3
        for (int i = 0; i < visionY; i++) {
            for (int j = 0; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y + j * 5) / size), (int) ((x - i * 5) / size)) == 0) {
                        placer.add(new Rectangle((int) (x + xmap - i * 5), (int) (y + ymap + 5 * j), 5, 5));
                    }
                }
            }
        }
        //2
        for (int i = 1; i < visionY; i++) {
            for (int j = 1; j < visionY; j++) {
                if (Math.sqrt(i * i + j * j) < visionY) {
                    if (tm.getType((int) ((y - j * 5) / size), (int) ((x - i * 5) / size)) == 0) {
                        placer.add(new Rectangle((int) (x + xmap - i * 5), (int) (y + ymap - 5 * j), 5, 5));
                    } else {
                        break;
                    }
                }
            }
        }

        placer.retainAll(placel);

    }

    public void update2(int x, int y, TileMap tm) {
        double xmap = tm.getx();
        double ymap = tm.gety();
        double size = tm.getTileSize() * GamePanel.SCALE;
        this.x = x;
        this.y = y;
        rangexRight = new int[visionY];
        rangexLeft = new int[visionY];
        for (int i = 0; i < visionY; i++) {
            while (rangexRight[i] < visionX && tm.getType((int) ((y + i * 5) / size), (int) ((x + rangexRight[i]) / size)) == 0) {
                rangexRight[i]++;
            }
            while (rangexLeft[i] < visionX && tm.getType((int) ((y + i * 5) / size), (int) ((x - rangexLeft[i]) / size)) == 0) {
                rangexLeft[i]++;
            }

            place = new ArrayList<>();

            r[i] = new Rectangle((int) (x - rangexLeft[i] + xmap), (int) (y + i * 5 + ymap), rangexLeft[i] + rangexRight[i], 5);
        }
    }

    public void draw2(Graphics2D g) {
        g.setColor(c);
        for (int i = 0; i < visionY / 5; i++) {
            g.fill(r[i]);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(c);
        for (int i = 0; i < placer.size(); i++) {
            g.fill(placer.get(i));
        }
    }
}
