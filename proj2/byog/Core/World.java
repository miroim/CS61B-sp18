package byog.Core;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class World {
    /* @parameter: rectList
    Using a rectList to store all room and hall,
    because one side of hall rectangle's length is 3,
    we can easily to know that which one in the rectList
    is room or hall
     */

    /* 1. render first random size room in the scope of World, add to rectList
       2. render another room nearby last one without overlapping(check with rectList)
            and out of scope, add to rectList
       3. render a hall between last two room in rectList, add to rectList
       4. repeat step 2 and step 3, and till can't render a room in World
            or reach the random count of room
     */

    private static final long SEED = 133;
    private static final Random RANDOM = new Random(SEED);
    public List<Rectangle> rectList;
    private final int num = RandomUtils.uniform(RANDOM, 20, 30);

    public World() {
        rectList = new ArrayList<Rectangle>();
    }

    // use random size rectangle to stand room
    public Rectangle randomSizeRoom() {
        /* If the list of rectangle is not empty,
         * generate next rectangle according to last one
         *
         * else random generate first one
         */
        Rectangle lastRect = null;
        int x, y, width, height;
        int min = 5;
        int max = 9;
        Rectangle rectangle;
        do {
            if (!rectList.isEmpty()) {
                lastRect = rectList.get(rectList.size() - 1);
            }
            if (lastRect != null) {
            /* offsetX and offsetY both random return -1, 0, 1
                by using this to control the position of the next rect
             */

                int offsetX = RandomUtils.uniform(RANDOM, -1, 2);
                int offsetY = RandomUtils.uniform(RANDOM, -1, 2);
                x = lastRect.getPosition().getX()
                        + offsetX
                        * RandomUtils
                            .uniform(RANDOM, lastRect.getWidth() + 4,max * 2);
                y = lastRect.getPosition().getY()
                        + offsetY
                        * RandomUtils
                            .uniform(RANDOM, lastRect.getHeight() + 4,max * 2);
            } else {
                x = RandomUtils.uniform(RANDOM, Game.WIDTH - max);
                y = RandomUtils.uniform(RANDOM, Game.HEIGHT - max);
            }
            width = RandomUtils.uniform(RANDOM, min, max);
            height = RandomUtils.uniform(RANDOM, min, max);
            rectangle = new Rectangle(new Position(x, y), width, height);
        } while (rectangle.isOutOfBound());
        return rectangle;
    }

    public void addRandomRoom() {
        for (int i = 0; i < num; i++) {
            Rectangle newRect = randomSizeRoom();

            if (rectList.isEmpty()) {
                rectList.add(newRect);
            } else {
                boolean overlaps = false;
                for (Rectangle rect : rectList) {
                    if (rect.isOverlap(newRect)) {
                        overlaps = true;
                        break;
                    }
                }
                if (!overlaps) {
                    rectList.add(newRect);
                }
            }
        }
    }

    public List<Rectangle> connectRoom(List<Rectangle> roomList) {
        List<Rectangle> result = new ArrayList<>();
        // choose a random room
//        int index = RandomUtils.uniform(RANDOM, roomList.size());
//        Rectangle startRoom = roomList.get(index);
        for (Rectangle rect : rectList) {
            result.addAll(getHall(rect, rect.minDistance(rectList)));
        }
//        for (int i = 0; i < roomList.size() - 1; i += 1) {
//            result.addAll(getHall(roomList.get(i), roomList.get(i + 1)));
//        }
        return result;
    }

    /* generate a corner
        ###
        #·#
        ###
     */
    public static Rectangle corner(Position p) {
        return new Rectangle(p, 3, 3);
    }

    /* generate a horizontal hall by length
        #######
        ·······
        #######
     */
    public static Rectangle horizontalHall(Position p, int length) {
        return new Rectangle(p, length, 3);
    }

    /* generate a vertical hall by length
        #·#
        #·#
        #·#
        #·#
    */
    public static Rectangle verticalHall(Position p, int length) {
        return new Rectangle(p, 3, length);
    }

    private static List<Rectangle> rightLeft(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);
        int y;

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        int offsetX = b_midpoint.getX() - a_midpoint.getX();

        if (a_midpoint.getY() > b_midpoint.getY()) {
            y = b_midpoint.getY();
        } else {
            y = a_midpoint.getY();
        }
        hallList.add(horizontalHall(a_midpoint, offsetX / 2));
        Position p = new Position(a_midpoint.getX() + offsetX / 2 + 1,
                b_midpoint.getY());
        Position p1 = new Position(a_midpoint.getX() + offsetX / 2 - 1, y);
        hallList.add(horizontalHall(p, offsetX / 2 + 1));
        hallList.add(verticalHall(p1, Math.abs(b_midpoint.getY() - a_midpoint.getY()) + 3));
        return hallList;
    }

    private static List<Rectangle> rightBottomOrTop(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);
        int y;

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];

        if (a_midpoint.getY() > b_midpoint.getY()) {
            y = b_midpoint.getY();
        } else {
            y = a_midpoint.getY();
        }
        hallList.add(horizontalHall(a_midpoint, b_midpoint.getX() - a_midpoint.getX() + 1));
        Position p = new Position(b_midpoint.getX(), y);
        hallList.add(verticalHall(p, Math.abs(b_midpoint.getY() - (a_midpoint.getY() + 1)) + 3));
        return hallList;
    }

    /* get hall between two room
     */
    public List<Rectangle> getHall(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        int offsetX = b.getPosition().getX() - a.getPosition().getX();
        int offsetY = b.getPosition().getY() - a.getPosition().getY();
        int x, y, width, height;
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);
        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        String a_edge = a.getEdge(a_midpoint);
        String b_edge = b.getEdge(b_midpoint);
        if (a_edge.equals("right")) {
            if (b_edge.equals("left")) {
                hallList.addAll(rightLeft(a, b));
            } else if (b_edge.equals("bottom") || b_edge.equals("top")) {
                hallList.addAll(rightBottomOrTop(a, b));
            }
        }
        if (b_edge.equals("right")) {
            if (a_edge.equals("left")) {
                hallList.addAll(rightLeft(b, a));
            } else if (a_edge.equals("bottom")) {
                hallList.addAll(rightBottomOrTop(b, a));
            }
        }

        return hallList;
    }
}
