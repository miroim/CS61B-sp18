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

    private static final long SEED = 4033;
    private static final Random RANDOM = new Random(SEED);
    public List<Rectangle> rectList;
    public List<Rectangle> hallWithoutCornerList;
    private final int num = RandomUtils.uniform(RANDOM, 20, 30);

    public World() {
        rectList = new ArrayList<Rectangle>();
        hallWithoutCornerList = new ArrayList<Rectangle>();
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
        int max = 8;
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
        List<Rectangle> temp = roomList;
        // choose a random room
        int index = RandomUtils.uniform(RANDOM, temp.size());
        Rectangle startRoom = temp.get(index);
        for (Rectangle rect : rectList) {
            if (startRoom.distance(rect) < 12) {
                result.addAll(getHall(startRoom, rect));
            }
        }
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


//    /* generate a horizontal hall by length
//        #######
//        ·······
//        #######
//     */
//    public static Rectangle horizontalHall(Position p, int length) {
//        return new Rectangle(p, length, 3);
//    }
//
//    /* generate a vertical hall by length
//        #·#
//        #·#
//        #·#
//        #·#
//    */
//    public static Rectangle verticalHall(Position p, int length) {
//        return new Rectangle(p, 3, length);
//    }

    /* get hall between two room
     */
    public List<Rectangle> getHall(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<Rectangle>();
        int offsetX = b.getPosition().getX() - a.getPosition().getX();
        int offsetY = b.getPosition().getY() - a.getPosition().getY();
        int x, y, width, height;
        if (offsetX == 0) {
            x = a.getPosition().getX() + 1;
            width = 3;
            if (offsetY > 0) {
                y = a.getPosition().getY() + a.getHeight() - 1;
                height = offsetY - a.getHeight() + 2;
            } else {
                y = b.getPosition().getY() + b.getHeight() - 1;
                height = Math.abs(offsetY) - b.getHeight() + 2;
            }
            hallWithoutCornerList.add(new Rectangle(new Position(x, y), width, height));
            hallList.add(new Rectangle(new Position(x, y), width, height));

        } else if (offsetY == 0){
            y = a.getPosition().getY() + 2;
            height = 3;
            if (offsetX > 0) {
                x = a.getPosition().getX() + a.getWidth() - 1;
                width = offsetX - a.getWidth() + 3;
            } else {
                x = b.getPosition().getX() + b.getWidth() - 1;
                width = Math.abs(offsetX) - b.getWidth() + 3;
            }
            hallWithoutCornerList.add(new Rectangle(new Position(x, y), width, height));
            hallList.add(new Rectangle(new Position(x, y), width, height));

        } else if (offsetX > 0) {
            x = a.getRightSide() - 1;
            width = offsetX - a.getWidth() + 3;
            if (offsetY > 0) {
                y = a.getTopSide() - 3;
                height = offsetY - a.getHeight() + 2;
                hallWithoutCornerList.add(new Rectangle(new Position(x + width - 2, y + 2), 3, height));
                hallList.add(new Rectangle(new Position(x + width - 2, y + 2), 3, height));
            } else {
                y = a.getPosition().getY();
                height = Math.abs(offsetY) - b.getHeight() + 2;
                hallWithoutCornerList.add(new Rectangle(new Position(x + width - 2, b.getTopSide() - 1),
                        3, height + 1));
                hallList.add(new Rectangle(new Position(x + width - 2, b.getTopSide() - 1),
                        3, height + 1));
            }
            hallWithoutCornerList.add(new Rectangle(new Position(x, y), width, 3));
            hallList.add(new Rectangle(new Position(x, y), width, 3));
            hallList.add(corner(new Position(x + width - 2, y)));

        } else {
            x = b.getRightSide() - 1;
            width = Math.abs(offsetX) - b.getWidth() + 4;
            if (offsetY > 0) {
                y = b.getPosition().getY();
                height = offsetY - a.getHeight() + 2;
                hallWithoutCornerList.add(new Rectangle(new Position(a.getPosition().getX() + 1, a.getTopSide() - 1),
                        3, height));
                hallList.add(new Rectangle(new Position(a.getPosition().getX() + 1, a.getTopSide() - 1),
                        3, height));
            } else {
                y = b.getPosition().getY();
                height = Math.abs(offsetY) - b.getHeight() + 2;
                hallWithoutCornerList.add(new Rectangle(new Position(x + width - 2, b.getTopSide() - 2),
                        3, height + 1));
                hallList.add(new Rectangle(new Position(x + width - 2, b.getTopSide() - 2),
                        3, height + 1));
            }
            hallWithoutCornerList.add(new Rectangle(new Position(x, y), width, 3));
            hallList.add(new Rectangle(new Position(x, y), width, 3));
            hallList.add(corner(new Position(x + width - 2, y)));

        }
        return hallList;
    }
}
