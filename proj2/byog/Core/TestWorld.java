package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class TestWorld {

//    public static void leftTopCorner(TETile[][] world, Position p) {
//        /* generate leftTopCorner
//         *
//         *         #·#
//         *         ··#
//         *         ###
//         */
//        for (int i = p.x; i < p.x + 3; i += 1) {
//            world[i][p.y] = Tileset.WALL;
//            world[i][p.y + 1] = Tileset.FLOOR;
//            world[i][p.y + 2] = Tileset.WALL;
//        }
//        world[p.x + 2][p.y + 1] = Tileset.WALL;
//        world[p.x + 1][p.y + 2] = Tileset.FLOOR;
//    }
//
//    public static void leftBottomCorner(TETile[][] world, Position p) {
//        /* generate leftBottomCorner
//         *
//         *         ###
//         *         ··#
//         *         #·#
//         */
//        for (int i = p.x; i < p.x + 3; i += 1) {
//            world[i][p.y] = Tileset.WALL;
//            world[i][p.y + 1] = Tileset.FLOOR;
//            world[i][p.y + 2] = Tileset.WALL;
//        }
//        world[p.x + 2][p.y + 1] = Tileset.WALL;
//        world[p.x + 1][p.y] = Tileset.FLOOR;
//    }
//
//    public static void rightTopCorner(TETile[][] world, Position p) {
//        /* generate leftBottomCorner
//         *
//         *         #·#
//         *         #··
//         *         ###
//         */
//        for (int i = p.x; i < p.x + 3; i += 1) {
//            world[i][p.y] = Tileset.WALL;
//            world[i][p.y + 1] = Tileset.FLOOR;
//            world[i][p.y + 2] = Tileset.WALL;
//        }
//        world[p.x][p.y + 1] = Tileset.WALL;
//        world[p.x + 1][p.y + 2] = Tileset.FLOOR;
//    }
//
//    public static void rightBottomCorner(TETile[][] world, Position p) {
//        /* generate leftBottomCorner
//         *
//         *         ###
//         *         #··
//         *         #·#
//         */
//        for (int i = p.x; i < p.x + 3; i += 1) {
//            world[i][p.y] = Tileset.WALL;
//            world[i][p.y + 1] = Tileset.FLOOR;
//            world[i][p.y + 2] = Tileset.WALL;
//        }
//        world[p.x][p.y + 1] = Tileset.WALL;
//        world[p.x + 1][p.y] = Tileset.FLOOR;
//    }

    public static void addRoom(TETile[][] world, Rectangle rect) {
        for (int i = rect.getPosition().getY(); i < rect.getPosition().getY() + rect.getHeight(); i += 1) {
            for (int j = rect.getPosition().getX() + 1; j < rect.getPosition().getX() + rect.getWidth() - 1; j += 1) {
                world[j][i] = Tileset.FLOOR;
                world[j][rect.getPosition().getY()] = Tileset.WALL;
                world[j][rect.getPosition().getY() + rect.getHeight() - 1] = Tileset.WALL;
            }
            world[rect.getPosition().getX()][i] = Tileset.WALL;
            world[rect.getPosition().getX() + rect.getWidth() - 1][i] = Tileset.WALL;
        }
    }

    public static void addHall(TETile[][] world, Rectangle a, Rectangle b) {
        Position p = a.getRelPosition(b);
        Position hHallPosition = new Position(p.getX() + a.getWidth(), a.getPosition().getY());
        for (int i = a.getPosition().getX() + a.getWidth();
             i < a.getPosition().getX() + a.getWidth() + p.getX(); i += 1) {
            world[i][a.getPosition().getY()] = Tileset.WALL;
            world[i][a.getPosition().getY() + 2] = Tileset.WALL;
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
        for (Rectangle rect : w.roomList) {
            addRoom(world, rect);
        }
        addHall(world, w.roomList.get(1), w.roomList.get(2));

        ter.renderFrame(world);
    }
}
