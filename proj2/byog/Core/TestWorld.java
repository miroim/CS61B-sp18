package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
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
//        w.addRandomRoom();
//
//        List<Rectangle> r = w.connectRoom(w.rectList);
//        w.rectList.addAll(r);
//        addHall1(world, w.rectList);

//        for (Rectangle rect : w.rectList) {
//            addRoom(world, rect);
//        }


        Rectangle b = new Rectangle(new Position(40, 2), 8, 7);
        Rectangle c = new Rectangle(new Position(62, 10), 5, 6);

//        Rectangle d = new Rectangle(new Position(40, 2), 8, 7);
//        Rectangle e = new Rectangle(new Position(54, 20), 5, 6);
        Rectangle d = new Rectangle(new Position(40, 15), 8, 7);
        Rectangle e = new Rectangle(new Position(54, 2), 5, 6);
        Position[] midpoints = Rectangle.getClosestMidpoint(d, e);
        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        String d_edge = d.getEdge(a_midpoint);
        String e_edge = e.getEdge(b_midpoint);
        System.out.println(d_edge +" "+e_edge);


        List<Rectangle> l = new ArrayList<>();
        l.add(d);
        l.add(e);

        l.addAll(w.getHall(d, e));
        renderAll(world, l);

        ter.renderFrame(world);
    }
}
