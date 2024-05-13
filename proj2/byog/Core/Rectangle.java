package byog.Core;

import java.util.*;

public class Rectangle {
    private final Position position;
    private final int width;
    private final int height;
    public boolean isConnected = false;

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
        Position[] midpoints = getClosestMidpoint(this, a);
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        return Math.sqrt(Math.pow(a_midpoint.getX() - b_midpoint.getX(), 2)
                            + Math.pow(a_midpoint.getY() - b_midpoint.getY(), 2));
    }

    public Rectangle minDistance(List<Rectangle> rectList) {
        Rectangle res = null;
        double minDistance = Double.MAX_VALUE;
        for (Rectangle rect : rectList) {
            double curDistance = this.distance(rect);
            if (curDistance < minDistance && !this.equals(rect) && !rect.isConnected()) {
                minDistance = curDistance;
                res = rect;
            }
        }
        return res;
    }

    public Rectangle minPath(List<Rectangle> rectList) {
        int index = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < rectList.size(); i += 1) {
            double curDistance = this.distance(rectList.get(i));
            if (curDistance < minDistance && curDistance != 0) {
                minDistance = curDistance;
                index = i;
            }
        }
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

    public boolean isCompletelyCoveredBy(Rectangle other) {
        // Check if the rectangles have a length or width of 3
        if (width == 3 && other.width == 3 || (height == 3 && other.height == 3)) {
            // Check if the current rectangle is completely covered by the other rectangle
            return other.position.getX() <= position.getX() &&
                    other.position.getY() <= position.getY() &&
                    other.position.getX() + other.width >= position.getX() + width &&
                    other.position.getY() + other.height >= position.getY() + height;
        }
        return false;
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
                if (rect1.isOverlap(rect2) && !rect1.equals(rect2)) {
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

    // Determine rectangle is the same as another rectangle
    public boolean equals(Rectangle rect) {
        return position.equals(rect.position) && width == rect.width && height == rect.height ;
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

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        int offsetX = b_midpoint.getX() - a_midpoint.getX();
        int offsetY = Math.abs(b_midpoint.getY() - a_midpoint.getY());

        int y = Math.min(a_midpoint.getY(), b_midpoint.getY());
        if (offsetY < 4) {
            Position p = new Position(a_midpoint.getX(), y);
            hallList.add(horizontalHall(p, offsetX + 2));
        } else {
            hallList.add(horizontalHall(a_midpoint, offsetX / 2));
            Position p = new Position(a_midpoint.getX() + offsetX / 2 + 1,
                    b_midpoint.getY());
            Position p1 = new Position(a_midpoint.getX() + offsetX / 2 - 1, y);
            hallList.add(horizontalHall(p, offsetX - offsetX / 2 + 1));
            hallList.add(verticalHall(p1, Math.abs(b_midpoint.getY() - a_midpoint.getY()) + 3));
        }
        return hallList;
    }

    private static List<Rectangle> rightBottomOrTop(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];

        int y = Math.min(a_midpoint.getY(), b_midpoint.getY());
        hallList.add(horizontalHall(a_midpoint, b_midpoint.getX() - a_midpoint.getX() + 1));
        Position p = new Position(b_midpoint.getX(), y);
        hallList.add(verticalHall(p, Math.abs(b_midpoint.getY() - (a_midpoint.getY() + 1)) + 3));
        return hallList;
    }

    private static List<Rectangle> bottomTop(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        int offsetX = Math.abs(b_midpoint.getX() - a_midpoint.getX());
        int offsetY = b_midpoint.getY() - a_midpoint.getY();
        int x = Math.min(a_midpoint.getX(), b_midpoint.getX());

        if (offsetX < 4) {
            Position p = new Position(x + 1, a_midpoint.getY());
            hallList.add(verticalHall(p, offsetY + 2));
        } else {
            hallList.add(verticalHall(a_midpoint, offsetY / 2));

            Position p = new Position(b_midpoint.getX(), a_midpoint.getY() + offsetY / 2 + 1);
            hallList.add(verticalHall(p, offsetY / 2 + 2));

            Position p1 = new Position(x, a_midpoint.getY() + offsetY / 2 - 1);
            hallList.add(horizontalHall(p1, Math.abs(b_midpoint.getX() - a_midpoint.getX()) + 3));
        }
        return hallList;
    }

    private static List<Rectangle> leftTopOrBottom(Rectangle a, Rectangle b) {
        List<Rectangle> hallList = new ArrayList<>();
        Position[] midpoints = Rectangle.getClosestMidpoint(a, b);

        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];

        int y = Math.min(a_midpoint.getY(), b_midpoint.getY());
        Position p = new Position(b_midpoint.getX() + 2, a_midpoint.getY());
        hallList.add(horizontalHall(p, a_midpoint.getX() - b_midpoint.getX()));
        Position p1 = new Position(b_midpoint.getX(), y);
        hallList.add(verticalHall(p1, Math.abs(b_midpoint.getY() - (a_midpoint.getY() + 1)) + 3));

        return hallList;
    }

    /* get hall between two room
     */
    public List<Rectangle> connect(Rectangle a) {
        a.isConnected = true;
        List<Rectangle> hallList = new ArrayList<>();

        Position[] midpoints = Rectangle.getClosestMidpoint(a, this);
        // the closest two edge between two rectangle
        Position a_midpoint = midpoints[0];
        Position b_midpoint = midpoints[1];
        String a_edge = a.getEdge(a_midpoint);
        String b_edge = this.getEdge(b_midpoint);
        if (a_edge.equals("right")) {
            if (b_edge.equals("left")) {
                hallList.addAll(rightLeft(a, this));
            } else if (b_edge.equals("bottom") || b_edge.equals("top")) {
                hallList.addAll(rightBottomOrTop(a, this));
            }
        } else if (a_edge.equals("top")) {
            if (b_edge.equals("bottom")) {
                hallList.addAll(bottomTop(a, this));
            }
        } else if (a_edge.equals("left")) {
            if (b_edge.equals("bottom") || b_edge.equals("top")) {
                hallList.addAll(leftTopOrBottom(a, this));
            }
        } else if (a_edge.equals("bottom")) {
            if (b_edge.equals("top")) {
                hallList.addAll(bottomTop(this, a));
            }
        }
        if (b_edge.equals("right")) {
            if (a_edge.equals("left")) {
                hallList.addAll(rightLeft(this, a));
            } else if (a_edge.equals("bottom") || a_edge.equals("top")) {
                hallList.addAll(rightBottomOrTop(this, a));
            }
        } else if (b_edge.equals("top")) {
            if (a_edge.equals("bottom")) {
                hallList.addAll(bottomTop(this, a));
            }
        } else if (b_edge.equals("left")) {
            if (a_edge.equals("bottom") || a_edge.equals("top")) {
                hallList.addAll(leftTopOrBottom(this, a));
            }
        }
        return hallList;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void print() {
        System.out.println("(x: " + position.getX()
                + ", y: " + position.getY()
                + ") width: " + width
                + " height: " + height);
    }
}
