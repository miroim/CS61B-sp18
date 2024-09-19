package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(seed, 40, 40);
        game.startGame();
    }

    public MemoryGame(int seed, int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        this.round = 1;
        this.gameOver = false;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

    }

    public String generateRandomString(int n) {
        // Generate random string of letters of length n
        StringBuilder result = new StringBuilder();
        while (result.length() != n) {
            result.append(CHARACTERS[rand.nextInt(26)]);
        }
        return result.toString();
    }

    public void drawFrame(String s) {
        // Take the string and display it in the center of the screen
        double x = (double) width / 2;
        double y = (double) height / 2;

        flashSequence(s);

        // If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.setPenColor(Color.white);
            StdDraw.text(35, 38, "round: " + round);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        double x = (double) width / 2;
        double y = (double) height / 2;
        // Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            StdDraw.setPenColor(Color.blue);
            StdDraw.text(x, y, String.valueOf(letters.charAt(i)));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        StringBuilder result = new StringBuilder();
        while (result.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                result.append(StdDraw.nextKeyTyped());
                StdDraw.setPenColor(Color.blue);
                StdDraw.text(20, 20, result.toString());
                StdDraw.show();
                StdDraw.clear(Color.black);
                StdDraw.pause(1000);
            }
        }
        return result.toString();
    }

    public void startGame() {
        String s;
        String stringFromUser;
        while (true) {
            drawFrame("round: " + round);
            StdDraw.pause(500);
            StdDraw.clear(Color.black);
            s = generateRandomString(round);
            drawFrame(s);
            stringFromUser = solicitNCharsInput(round);
            if (s.equals(stringFromUser)) {
                round += 1;
            } else {
                StdDraw.pause(500);
                StdDraw.clear(Color.black);
                String end = "Game Over! You made it to round: ";
                drawFrame(end + round);
                StdDraw.show();
                StdDraw.pause(1000);
                round = 1;
                break;
            }
        }
    }

}
