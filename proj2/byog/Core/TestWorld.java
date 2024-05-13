package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Set;

public class TestWorld {

    public static void addRectangle(TETile[][] world, Rectangle rect) {
        for (int i = rect.getPosition().getY(); i < rect.getPosition().getY() + rect.getHeight(); i += 1) {
            for (int j = rect.getPosition().getX(); j < rect.getPosition().getX() + rect.getWidth() - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;
                world[j][rect.getPosition().getY()] = Tileset.WALL;
                world[j][rect.getPosition().getY() + rect.getHeight() - 1] = Tileset.WALL;
            }
                world[rect.getPosition().getX()][i] = Tileset.WALL;
                world[rect.getPosition().getX() + rect.getWidth() - 1][i] = Tileset.WALL;
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
        List<Rectangle> r = w.connectAllRoom(w.rectList);
        w.rectList.addAll(r);
        renderAll(world, w.rectList);
        ter.renderFrame(world);
    }
}
