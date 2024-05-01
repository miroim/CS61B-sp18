package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class TestWorld {

    public static void addRoom(TETile[][] world, Rectangle rect) {
        for (int i = rect.getPosition().getY(); i < rect.getPosition().getY() + rect.getHeight(); i += 1) {
            for (int j = rect.getPosition().getX(); j < rect.getPosition().getX() + rect.getWidth() - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;

                if (rect.getWidth() != 3 || (rect.getHeight() == 3 && rect.getWidth() == 3)) {
                    world[j][rect.getPosition().getY()] = Tileset.WALL;
                    world[j][rect.getPosition().getY() + rect.getHeight() - 1] = Tileset.WALL;
                }
            }
            if (rect.getHeight() != 3 || (rect.getHeight() == 3 && rect.getWidth() == 3)) {
                world[rect.getPosition().getX()][i] = Tileset.WALL;
                world[rect.getPosition().getX() + rect.getWidth() - 1][i] = Tileset.WALL;
            }
        }
    }

    public static void addHall(TETile[][] world, Rectangle rect) {
        for (int i = rect.getPosition().getY(); i < rect.getPosition().getY() + rect.getHeight(); i += 1) {
            for (int j = rect.getPosition().getX(); j < rect.getPosition().getX() + rect.getWidth() - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;
                if (rect.getWidth() != 3) {
                    world[j][rect.getPosition().getY()] = Tileset.WALL;
                    world[j][rect.getPosition().getY() + rect.getHeight() - 1] = Tileset.WALL;
                }
            }
            if (rect.getHeight() != 3) {
                world[rect.getPosition().getX()][i] = Tileset.WALL;
                world[rect.getPosition().getX() + rect.getWidth() - 1][i] = Tileset.WALL;
            }
        }
    }



    public static void main(String[] args) {
        int WIDTH = 80;
        int HEIGHT = 30;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        World w = new World();
//        w.addRandomRoom();

//        List<Rectangle> r = w.connectRoom(w.rectList);
//        w.rectList.addAll(r);
//        for (Rectangle rect : w.rectList) {
//            addRoom(world, rect);
//        }
//
//        for (Rectangle rect : w.hallWithoutCornerList) {
//            addHall(world, rect);
//        }


//
//        w.rectList.addAll(w.getHall(w.rectList.get(0), w.rectList.get(0).minDistance(w.rectList)));
//        addRoom(world, w.rectList.get(10));
//
//        w.rectList.addAll(w.getHall(w.rectList.get(1), w.rectList.get(1).minDistance(w.rectList)));
//        addRoom(world, w.rectList.get(11));
//
//        Rectangle a = w.rectList.get(2).minDistance(w.rectList);
//        w.rectList.addAll(w.getHall(w.rectList.get(2),a));
//
//        addRoom(world, w.rectList.get(12));
//        addRoom(world, w.rectList.get(13));
//        addRoom(world, w.rectList.get(14));
//        addRoom(world, w.rectList.get(15));


//        w.rectList.addAll(w.getHall(w.rectList.get(2), w.rectList.get(3)));
//        addRoom(world, w.rectList.get(12));
        Rectangle b = new Rectangle(new Position(71, 2), 8, 7);
        Rectangle c = new Rectangle(new Position(62, 3), 5, 6);
        addRoom(world, b);
        addRoom(world, c);
        w.rectList.addAll(w.getHall(b, c));
        addRoom(world, w.getHall(b, c).get(0));

        ter.renderFrame(world);
    }
}
