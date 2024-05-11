package byog.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rectangle {
    private final Position position;
    private final int width;
    private final int height;

    public Rectangle(Position p, int w, int h) {
        position = p;
        width = w;
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }

    public double distance(Rectangle a) {
        return Math.sqrt(Math.pow(this.position.getX() - a.position.getX(), 2)
                            + Math.pow(this.position.getY() - a.position.getY(), 2));
    }

    public Rectangle minDistance(List<Rectangle> rectList) {
        int index = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < rectList.size() - 1; i += 1) {
            double curDistance = this.distance(rectList.get(i));
            if (curDistance < minDistance && curDistance != 0) {
                minDistance = curDistance;
                index = i;
            }
        }
        System.out.println(index);
        return rectList.get(index);
    }

    public boolean isOverlap(Rectangle a) {
        /*
                                ┏━━━━━━━━━━━━━━━┓
                                ┃        end    ┃  <- this
                        ┏━━━━━━━━━━━━━━━┓       ┃
                   a->  ┃       ┃       ┃       ┃
                        ┃       ┗━━━━━━━┃━━━━━━━┛
                        ┃     start     ┃
                        ┗━━━━━━━━━━━━━━━┛
         */
        // calculate the intersection with two rectangle
        int startX = Math.max(this.position.getX(), a.position.getX());
        int startY = Math.max(this.position.getY(), a.position.getY());
        int endX = Math.min(this.getRight().getX(), a.getRight().getX());
        int endY = Math.min(this.getTop().getY(), a.getTop().getY());

        // check if the intersection is empty
        return startX <= endX && startY <= endY;
    }

    public boolean isOutOfBound() {
        return this.position.getX() + this.width > Game.WIDTH
                || this.position.getY() + this.height > Game.HEIGHT
                || this.position.getX() < 0
                || this.position.getY() < 0;
    }

    // return position of the midpoint on the top edge of rectangle
    public Position getTop() {
        int x = this.position.getX() + this.width / 2 - 1;
        int y = this.position.getY() + this.height - 1;
        return new Position(x, y);
    }

    // return position of the midpoint on the bottom edge of rectangle
    public Position getBottom() {
        int x = this.position.getX() + this.width / 2 - 1;
        int y = this.position.getY() - 1;
        return new Position(x, y);
    }

    // return position of the midpoint on the left edge of rectangle
    public Position getLeft() {
        int x = this.position.getX() - 1;
        int y = this.position.getY() + this.height / 2 - 1;
        return new Position(x, y);
    }

    // return position of the midpoint on the right edge of rectangle
    public Position getRight() {
        int x = this.position.getX() + this.width - 1;
        int y = this.position.getY() + this.height / 2 - 1;
        return new Position(x, y);
    }

    // return the closest two midpoint between two rectangle
    public static Position[] getClosestMidpoint(Rectangle a, Rectangle b) {
        /*
         * 1. Get the midpoint from two rectangle, and separately store them in two array list
         * 2. Calculate the distance between the midpoint of both rectangle
         * 3. Find and return the position with minimum distance
         */
        List<Position> a_midpoints = new ArrayList<>(4);
        List<Position> b_midpoints = new ArrayList<>(4);
        Position[] closest_midpoint = new Position[2];
        double minimum_distance = Double.MAX_VALUE;
        a_midpoints.add(a.getLeft());
        a_midpoints.add(a.getRight());
        a_midpoints.add(a.getTop());
        a_midpoints.add(a.getBottom());
        b_midpoints.add(b.getLeft());
        b_midpoints.add(b.getRight());
        b_midpoints.add(b.getTop());
        b_midpoints.add(b.getBottom());
        for (Position a_midpoint : a_midpoints) {
            for (Position b_midpoint : b_midpoints) {
                double distance = a_midpoint.getDistance(b_midpoint);
                if (distance < minimum_distance) {
                    minimum_distance = distance;
                    closest_midpoint[0] = a_midpoint;
                    closest_midpoint[1] = b_midpoint;
                }
            }
        }
        return closest_midpoint;
    }

    // Determining the midpoint in which edge of rectangle
    public String getEdge(Position m) {
        Position left = this.getLeft();
        Position right = this.getRight();
        Position top = this.getTop();
        if (m.equals(left)) {
            return "left";
        } else if (m.equals(right)) {
            return "right";
        } else if (m.equals(top)) {
            return "top";
        } else{
            return "bottom";
        }
    }

    // Determine the intersection position list of a list rectangle
    public static Set<Position> getIntersectionPosition(List<Rectangle> rectList) {
        Set<Position> floors = new HashSet<>();
        for (int i = 0; i < rectList.size(); i += 1) {
            for (int j = i + 1; j < rectList.size(); j += 1) {
                Rectangle rect1 = rectList.get(i);
                Rectangle rect2 = rectList.get(j);
                // Check if overlap between two rectangle
                if (rect1.isOverlap(rect2)) {
                    // Determine the border position
                    int minX = Math.max(rect1.getPosition().getX(), rect2.getPosition().getX());
                    int maxX = Math.min(rect1.getRight().getX(), rect2.getRight().getX());
                    int minY = Math.max(rect1.getPosition().getY(), rect2.getPosition().getY());
                    int maxY = Math.min(rect1.getTop().getY(), rect2.getTop().getY());

                    // Add the intersection or tangency part to floors
                    for (int x = minX + 1; x < maxX; x += 1) {
                        floors.add(new Position(x, minY));
                        floors.add(new Position(x, maxY));
                    }
                    for (int y = minY + 1; y < maxY; y += 1) {
                        floors.add(new Position(minX, y));
                        floors.add(new Position(maxX, y));
                    }
                }
            }
        }
        return floors;
    }
}
