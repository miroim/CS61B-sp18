package byog.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private static Random RANDOM;
    public List<Rectangle> rectList;
    private final int num;

    public World(long s) {
        RANDOM = new Random(s);
        rectList = new ArrayList<>();
        num = RandomUtils.uniform(RANDOM, 10, 20);
    }

    // use random size rectangle to stand room
    public Rectangle randomSizeRoom() {
        /* If the list of rectangle is not empty,
         * generate next rectangle according to random one in list
         *
         * else random generate first one
         */
        Rectangle randomRect = null;
        int x, y, width, height;
        int min = 6;
        int max = 10;
        Rectangle rectangle;
        do {
            if (!rectList.isEmpty()) {
                int index = RandomUtils.uniform(RANDOM, rectList.size());
                randomRect = rectList.get(index);
            }
            if (randomRect != null) {
            /* offsetX and offsetY both random return -1, 0, 1
                by using this to control the position of the next rect
             */

                int offsetX = RandomUtils.uniform(RANDOM, -1, 2);
                int offsetY = RandomUtils.uniform(RANDOM, -1, 2);
                x = randomRect.getPosition().getX()
                        + offsetX
                        * RandomUtils
                            .uniform(RANDOM, randomRect.getWidth() + 4,max * 2);
                y = randomRect.getPosition().getY()
                        + offsetY
                        * RandomUtils
                            .uniform(RANDOM, randomRect.getHeight() + 4,max * 2);
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
        while (num != rectList.size()) {
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

    /* 1. Random pick a room in roomList
    *  2. connect this room with a minimum distance room in list
    *  3. connect the minimum distance room with its minimum distance room (not been connected)
    *  4. repeat step 2. and step 4. until the last room
    */
    public List<Rectangle> connectAllRoom(List<Rectangle> roomList) {
        List<Rectangle> result = new ArrayList<>();
        List<Rectangle> connectedRooms = new ArrayList<>();
        // Choose a random starting room
        Rectangle currentRoom = roomList.get(RandomUtils.uniform(RANDOM, roomList.size()));
        connectedRooms.add(currentRoom);
        currentRoom.isConnected = true;

        int n = roomList.size() - 1;
        while (n > 0) {
            Rectangle nextRoom = currentRoom.minDistance(roomList);
            if (!connectedRooms.isEmpty()) {
                Rectangle temp = nextRoom.minPath(connectedRooms);
                if (currentRoom.distance(nextRoom) > temp.distance(nextRoom)) {
                    currentRoom = temp;
                }
            }
            result.addAll(currentRoom.connect(nextRoom));
            connectedRooms.add(nextRoom);
            if (nextRoom.isConnected()) {
                n -= 1;
            }
            currentRoom = nextRoom;
        }

        List<Rectangle> removeList = new ArrayList<>();
        for (int i = 0; i < result.size(); i += 1) {
            for (Rectangle rect : result) {
                Rectangle a = result.get(i);
                if (a.isCompletelyCoveredBy(rect) && !a.equals(rect)) {
                    removeList.add(a);
                }
            }
        }
        for (Rectangle rect : removeList) {
            result.remove(rect);
        }
        return result;
    }
}
