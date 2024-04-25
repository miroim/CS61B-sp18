package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class TestWorld {
    private static final long SEED = 313;
    private static final Random RANDOM = new Random(SEED);


    public static void leftTopCorner(TETile[][] world, Position p) {
        /* generate leftTopCorner
         *
         *         #·#
         *         ··#
         *         ###
         */
        for (int i = p.x; i < p.x + 3; i += 1) {
            world[i][p.y] = Tileset.WALL;
            world[i][p.y + 1] = Tileset.FLOOR;
            world[i][p.y + 2] = Tileset.WALL;
        }
        world[p.x + 2][p.y + 1] = Tileset.WALL;
        world[p.x + 1][p.y + 2] = Tileset.FLOOR;
    }

    public static void leftBottomCorner(TETile[][] world, Position p) {
        /* generate leftBottomCorner
         *
         *         ###
         *         ··#
         *         #·#
         */
        for (int i = p.x; i < p.x + 3; i += 1) {
            world[i][p.y] = Tileset.WALL;
            world[i][p.y + 1] = Tileset.FLOOR;
            world[i][p.y + 2] = Tileset.WALL;
        }
        world[p.x + 2][p.y + 1] = Tileset.WALL;
        world[p.x + 1][p.y] = Tileset.FLOOR;
    }

    public static void rightTopCorner(TETile[][] world, Position p) {
        /* generate leftBottomCorner
         *
         *         #·#
         *         #··
         *         ###
         */
        for (int i = p.x; i < p.x + 3; i += 1) {
            world[i][p.y] = Tileset.WALL;
            world[i][p.y + 1] = Tileset.FLOOR;
            world[i][p.y + 2] = Tileset.WALL;
        }
        world[p.x][p.y + 1] = Tileset.WALL;
        world[p.x + 1][p.y + 2] = Tileset.FLOOR;
    }

    public static void rightBottomCorner(TETile[][] world, Position p) {
        /* generate leftBottomCorner
         *
         *         ###
         *         #··
         *         #·#
         */
        for (int i = p.x; i < p.x + 3; i += 1) {
            world[i][p.y] = Tileset.WALL;
            world[i][p.y + 1] = Tileset.FLOOR;
            world[i][p.y + 2] = Tileset.WALL;
        }
        world[p.x][p.y + 1] = Tileset.WALL;
        world[p.x + 1][p.y] = Tileset.FLOOR;
    }

    public static void addRoom(TETile[][] world, Rectangle rect) {

        for (int i = rect.position.y; i < rect.position.y + rect.width; i += 1) {
            for (int j = rect.position.x + 1; j < rect.position.x + rect.height - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;
                world[j][rect.position.y] = Tileset.WALL;
                world[j][rect.position.y + rect.width - 1] = Tileset.WALL;
            }
            world[rect.position.x][i] = Tileset.WALL;
            world[rect.position.x + rect.height - 1][i] = Tileset.WALL;
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
        w.addRandomRectangle();
        for (Rectangle rect : w.rectList) {
            addRoom(world, rect);
        }


        ter.renderFrame(world);
    }
}
