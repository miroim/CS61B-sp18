package byog.Core;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class World {
    private static final long SEED = 3333;
    private static final Random RANDOM = new Random(SEED);
    public List<Rectangle> rectList;
    private final int num = RandomUtils.uniform(RANDOM, 5, 10);

    public World() {
        rectList = new ArrayList<Rectangle>();
    }

    // use random size rectangle to stand room
    public Rectangle randomSizeRectangle() {
        /* If the list of rectangle is not empty,
         * generate next rectangle according to last one
         *
         * else random generate first one
         */
        Rectangle lastRect = null;
        int x, y, width, height;
        int min = 4;
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

                x = lastRect.position.getX() + offsetX * (lastRect.width * 2);
                y = lastRect.position.getY() + offsetY * (lastRect.height * 2);
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

    public void addRandomRectangle() {
        for (int i = 0; i < num; i++) {
            Rectangle newRect = randomSizeRectangle();

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

    /* generate a horizontal hall by length
        #######
        ·······
        #######
     */
    public Rectangle horizontalHall(Position p, int length) {
        return new Rectangle(p, length, 3);
    }

    /* generate a vertical hall by length
        #·#
        #·#
        #·#
        #·#
    */
    public Rectangle verticalHall(Position p, int length) {
        return new Rectangle(p, 3, length);
    }
}
