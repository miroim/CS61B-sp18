package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long SEED;
    private static String move;
    private static boolean isSave;

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
        handleInput(input);

        World world = new World(SEED);
        world.addRandomRoom();
        Position playerPosition = world.getPlayerStartPosition();

        List<Rectangle> r = world.connectAllRoom(world.getRectList());
        world.rectListAddAll(r);
        renderAll(finalWorldFrame, world);
        Position playerCurrentPosition = getPlayerCurrentPosition(move, world, playerPosition);

        renderPlayer(finalWorldFrame, playerCurrentPosition);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    private static void addRectangle(TETile[][] world, Rectangle rect) {
        int x = rect.getPosition().getX();
        int y = rect.getPosition().getY();
        for (int i = y; i < y + rect.getHeight(); i += 1) {
            for (int j = x; j < x + rect.getWidth() - 1; j += 1) {
                world[j][y] = Tileset.WALL;
                world[j][y + rect.getHeight() - 1] = Tileset.WALL;
            }
            world[x][i] = Tileset.WALL;
            world[x + rect.getWidth() - 1][i] = Tileset.WALL;
        }
    }

    private static void renderPlayer(TETile[][] world, Position p) {
        world[p.getX()][p.getY()] = Tileset.PLAYER;
    }

    private static void renderAll(TETile[][] world, World w) {
        // render WALL
        for (Rectangle rect : w.getRectList()) {
            addRectangle(world, rect);
        }
        // render FLOOR
        for (Position floor : w.getFloors()) {
            int x = floor.getX();
            int y = floor.getY();
            world[x][y] = Tileset.FLOOR;
        }
    }

    private void handleInput(String input) {
        input = input.toLowerCase();
        Pattern inputPattern = Pattern.compile("n(\\d+)s([wasd]+)(:q)?");
        Matcher inputMatcher = inputPattern.matcher(input);
        if (inputMatcher.matches()) {
            SEED = Long.parseLong(inputMatcher.group(1));
            move = inputMatcher.group(2);
            isSave = input.endsWith(":q");
        }
    }
    private static Position getPlayerCurrentPosition(String input, World w, Position p) {
        int x = p.getX();
        int y = p.getY();
        for (int i = 0; i < input.length(); i += 1) {
            switch (input.charAt(i)) {
                case 'w' :
                    if (w.isFloors(new Position(x, y + 1))) {
                        y += 1;
                    }
                    break;
                case 's' :
                    if (w.isFloors(new Position(x, y - 1))) {
                        y -= 1;
                    }
                    break;
                case 'a' :
                    if (w.isFloors(new Position(x - 1, y))) {
                        x -= 1;
                    }
                    break;
                case 'd' :
                    if (w.isFloors(new Position(x + 1, y))) {
                        x += 1;
                    }
                    break;
            }
        }
        return new Position(x, y);
    }
}
