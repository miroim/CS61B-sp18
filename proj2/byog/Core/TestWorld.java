package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Set;

public class TestWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static void addRectangle(TETile[][] world, Rectangle rect) {
        int x = rect.getPosition().getX();
        int y = rect.getPosition().getY();
        for (int i = y; i < y + rect.getHeight(); i += 1) {
            for (int j = x; j < x + rect.getWidth() - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;
                world[j][y] = Tileset.WALL;
                world[j][y + rect.getHeight() - 1] = Tileset.WALL;
            }
            world[x][i] = Tileset.WALL;
            world[x + rect.getWidth() - 1][i] = Tileset.WALL;
        }
    }

    public static void renderAll(TETile[][] world, List<Rectangle> rectList) {
        Set<Position> floors = Rectangle.getIntersectionPosition(rectList);
        for (Rectangle rect : rectList) {
            addRectangle(world, rect);
        }
        // render FLOOR in intersection
        for (Position floor : floors) {
            int x = floor.getX();
            int y = floor.getY();
            world[x][y] = Tileset.FLOOR;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        World w = new World(1454123651);
        w.addRandomRoom();
        List<Rectangle> r = w.connectAllRoom(w.getRectList());
        w.rectListAddAll(r);
        for (Rectangle rr : r) {
            w.rectListAdd(rr);
            renderAll(world, w.getRectList());
            ter.renderFrame(world);
        }
//        ter.renderFrame(world);
    }
}
