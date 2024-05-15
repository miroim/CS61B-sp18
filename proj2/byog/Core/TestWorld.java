package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;
import java.util.Set;

public class TestWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        World w = new World(1234);
        w.addRandomRoom();
        List<Rectangle> r = w.connectAllRoom(w.getRectList());
        w.rectListAddAll(r);
        for (Rectangle rr : r) {
            w.rectListAdd(rr);
            Game.renderAll(world, w);
            ter.renderFrame(world);
        }
    }
}
