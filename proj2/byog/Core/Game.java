package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Set;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
        World world = new World(seed);
        world.addRandomRoom();
        List<Rectangle> r = world.connectAllRoom(world.getRectList());
        world.rectListAddAll(r);
        renderAll(finalWorldFrame, world.getRectList());
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

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

    private static void renderAll(TETile[][] world, List<Rectangle> rectList) {
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
}
