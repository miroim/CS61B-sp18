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
        w.addRandomRoom();

        List<Rectangle> r = w.connectRoom(w.rectList);
        w.rectList.addAll(r);
        for (Rectangle rect : w.rectList) {
            addRoom(world, rect);
        }

        for (Rectangle rect : w.hallWithoutCornerList) {
            addHall(world, rect);
        }

//        addRoom(world, w.rectList.get(0));
//        addRoom(world, w.rectList.get(4));
//        addRoom(world, w.rectList.get(8));
//        addRoom(world, w.rectList.get(12));
//        addRoom(world, w.rectList.get(14));
//        addRoom(world, w.rectList.get(16));
//        addRoom(world, w.rectList.get(18));
//        addRoom(world, w.rectList.get(20));
//        addRoom(world, w.rectList.get(24));
//
//
//
//        addRoom(world, w.rectList.get(3));
//        addRoom(world, w.rectList.get(1));
//        addRoom(world, w.rectList.get(2));
//        addRoom(world, w.rectList.get(7));
//        addRoom(world, w.rectList.get(5));
//        addRoom(world, w.rectList.get(6));
//        addRoom(world, w.rectList.get(11));
//        addRoom(world, w.rectList.get(9));
//        addRoom(world, w.rectList.get(10));
//        addRoom(world, w.rectList.get(13));
//        addRoom(world, w.rectList.get(15));
//        addRoom(world, w.rectList.get(17));
//        addRoom(world, w.rectList.get(19));
//
//        addRoom(world, w.rectList.get(23));
//        addRoom(world, w.rectList.get(21));
//        addRoom(world, w.rectList.get(22));







//        addRoom(world, l.get(1));
//        addRoom(world, l.get(0));


        ter.renderFrame(world);
    }
}
