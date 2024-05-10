package byog.Core;

import java.util.ArrayList;
import java.util.List;

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
        int endX = Math.min(this.position.getX() + this.width, a.position.getX() + a.width);
        int endY = Math.min(this.position.getY() + this.height, a.position.getY() + a.height);

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
        int x = this.position.getX() + this.width / 2;
        int y = this.position.getY() + this.height;
        return new Position(x, y);
    }

    // return position of the midpoint on the bottom edge of rectangle
    public Position getBottom() {
        int x = this.position.getX() + this.width / 2;
        int y = this.position.getY();
        return new Position(x, y);
    }

    // return position of the midpoint on the left edge of rectangle
    public Position getLeft() {
        int x = this.position.getX();
        int y = this.position.getY() + this.height / 2;
        return new Position(x, y);
    }

    // return position of the midpoint on the right edge of rectangle
    public Position getRight() {
        int x = this.position.getX() + this.width;
        int y = this.position.getY() + this.height / 2;
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
        Position bottom = this.getBottom();
        if (m.equals(left)) {
            return "left";
        } else if (m.equals(right)) {
            return "right";
        } else if (m.equals(top)) {
            return "top";
        } else if (m.equals(bottom)) {
            return "bottom";
        } else {
            return null;
        }
    }
}
