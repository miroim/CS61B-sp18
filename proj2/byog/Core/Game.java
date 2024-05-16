package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static boolean gameOver;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.enableDoubleBuffering();
        ter.initialize(WIDTH, HEIGHT);
        drawGameIndex();
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

        handleInput(input);

        return startGame(SEED);
    }
    public static TETile[][] startGame(Long seed) {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        World world = new World(seed);
        world.addRandomRoom();
        Position playerPosition = world.getPlayerPosition();
        List<Rectangle> r = world.connectAllRoom(world.getRectList());
        world.rectListAddAll(r);
        renderAll(finalWorldFrame, world);
        Position playerCurrentPosition = getPlayerCurrentPosition(move, world, playerPosition);
        renderPlayer(finalWorldFrame, playerCurrentPosition);

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

    public static void renderAll(TETile[][] world, World w) {
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
        Pattern inputPattern = Pattern.compile("n(\\d+)s([wasd]+)?(:q)?");
        Matcher inputMatcher = inputPattern.matcher(input);
        if (inputMatcher.matches()) {
            SEED = Long.parseLong(inputMatcher.group(1));
            move = inputMatcher.group(2);
            gameOver = input.endsWith(":q");
        }
    }
    private static Position getPlayerCurrentPosition(String input, World w, Position p) {
        if (input == null) {
            return p;
        }
        int x = p.getX();
        int y = p.getY();
        Position newPosition = new Position(x, y);
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
                default:
                    break;
            }
        }
        w.setPlayerPosition(newPosition);
        return newPosition;
    }

    public void drawGameIndex() {
        drawGameFirstPage();
        while(!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                switch (c) {
                    case 'n':
                        SEED = getSeed();
                        handlePlayerMoveAction();

                        break;
                    case 'l':
                        drawFrame("load");
//                        World w = loadWorld();
                        break;
                    case 'q':
//                        saveWorld(w);
                        drawFrame("Quit");
                        System.exit(0);
                        break;
                    default:
                }
            }
        }
    }

    public void handlePlayerMoveAction() {
        move = "";
        while (!gameOver) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            move += String.valueOf(key).replaceAll("[^wasd]", "");
            System.out.println(move);
            ter.renderFrame(startGame(SEED));
        }
    }

    public Long getSeed() {
        StringBuilder input = new StringBuilder();

        while (!input.toString().endsWith("s")) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (input.length() != 0 && key == 's') {
                Font bigFont = new Font("Monaco", Font.BOLD, 16);
                StdDraw.setFont(bigFont);
                return Long.parseLong(input.toString());
            }
            input.append(String.valueOf(key).replaceAll("[^0-9]", ""));
            drawFrame("Enter game seed: " + input);
        }
        StdDraw.pause(500);
        return Long.parseLong(input.toString());
    }

    public void drawFrame(String s) {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        // Draw the GUI
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.text(midWidth, 0.3 * HEIGHT, "Press 's' to start game");
        }

        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }

    public void drawGameFirstPage() {
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        Font smallFont = new Font("Monaco", Font.ITALIC, 20);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(0.5 * WIDTH, 0.8 * HEIGHT, "CS61B: THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(0.5 * WIDTH, 0.4 * HEIGHT, "New Game (N)");
        StdDraw.text(0.5 * WIDTH, 0.35 * HEIGHT, "Load Game (L)");
        StdDraw.text(0.5 * WIDTH, 0.3 * HEIGHT, "Quit (Q)");
        StdDraw.show();
    }

    private static World loadWorld() {
        File f = new File("./world.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                World loadWorld = (World) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        return new World(SEED);
    }
    private static void saveWorld(World w) {
        File f = new File("./world.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
