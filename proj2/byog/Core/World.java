package byog.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable {

    /* 1. render first random size room in the scope of World, add to rectList
       2. render another room nearby rectangle in rectList without overlapping
            and doesn't out of scope, add to rectList
       3. repeat step 2, until the number of rectangle reach the random count of room
       4. random pick a rectangle, pick the closest rectangle and add hall between
            add them both to list connectedRoom
       5. find next closest rectangle(unconnected), connect hall to the closest one
            in connectedRoom
       6. repeat step 5, until all rectangle be connected
     */

    private static final long serialVersionUID = 123123123123123L;
    private static Random RANDOM;
    private List<Rectangle> rectList;
    private List<Position> floors;
    private Position player;
    private final int num;

    public World(long s) {
        RANDOM = new Random(s);
        rectList = new ArrayList<>();
        floors = new ArrayList<>();
        num = RandomUtils.uniform(RANDOM, 10, 20);
    }

    public List<Rectangle> getRectList() {
        return rectList;
    }

    public void rectListAdd(Rectangle rectangle) {
        rectList.add(rectangle);
    }

    public void rectListAddAll(List<Rectangle> list) {
        rectList.addAll(list);
    }

    public Rectangle getRandomRoom() {
        int randomIndex = RandomUtils.uniform(RANDOM, rectList.size());
        return rectList.get(randomIndex);
    }

    public List<Position> getFloors() {
        return floors;
    }

    private void getFloors(List<Rectangle> list) {
        for (Rectangle rect : list) {
            floors.addAll(getFloors(rect));
        }
    }

    private List<Position> getFloors(Rectangle rect) {
        List<Position> list = new ArrayList<>();
        int x = rect.getPosition().getX();
        int y = rect.getPosition().getY();
        for (int i = y + 1; i < y + rect.getHeight() - 1; i += 1) {
            for (int j = x + 1; j < x + rect.getWidth() - 1; j += 1) {
                list.add(new Position(j, i));
            }
        }
        return list;
    }

    public boolean isFloors(Position p) {
        if (floors == null || p == null) {
            return false;
        }
        for (Position floor : floors) {
            if (floor.equals(p)) {
                return true;
            }
        }
        return false;
    }

    private Position getPlayerStartPosition() {
        Rectangle startRoom = getRandomRoom();
        int x = startRoom.getBottom().getX();
        int y = startRoom.getLeft().getY();
        return new Position(x, y);
    }

    public Position getPlayerPosition() {
        return player;
    }

    public void setPlayerPosition(Position p) {
        player = p;
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
                            .uniform(RANDOM, randomRect.getWidth() + 4, max * 2);
                y = randomRect.getPosition().getY()
                        + offsetY
                        * RandomUtils
                            .uniform(RANDOM, randomRect.getHeight() + 4, max * 2);
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
        player = getPlayerStartPosition();
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
        currentRoom.setConnected(true);

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
        getFloors(roomList);
        getFloors(result);

        roomList.addAll(result);
        floors.addAll(Rectangle.getIntersectionPosition(roomList));
        return result;
    }
}
