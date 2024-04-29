package byog.Core;


import com.sun.org.apache.regexp.internal.RE;

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

    /* use position to represent the relative direction and distance
    *  positive x is right, negative x is left
    *  positive y is top, negative y is bottom
    *   e.g. (4, -7) is in the right bottom side
    */
    public Rectangle getRelPosition(Rectangle a) {
//        List<Rectangle> hallList = new ArrayList<Rectangle>();
        int offsetX = a.position.getX() - this.position.getX();
        int offsetY = a.position.getY() - this.position.getY();
        int x, y, width, height;
        if (offsetX == 0) {
            x = this.getPosition().getX();
            width = 3;
            if (offsetY > 0) {
                y = this.getPosition().getY() + this.getHeight() - 1;
                height = offsetY - this.getHeight() + 2;
            } else {
                y = a.getPosition().getY() + a.getHeight() - 1;
                height = Math.abs(offsetY) - a.getHeight() + 2;
            }
            return new Rectangle(new Position(x, y), width, height);
//            hallList.add(new Rectangle(new Position(x, y), width, height));
        } else {
            y = this.getPosition().getY();
            height = 3;
            if (offsetX > 0) {
                x = this.getPosition().getX() + this.getWidth() - 1;
                width = offsetX - this.getWidth() + 2;
            } else {
                x = a.getPosition().getX() + a.getWidth() - 1;
                width = Math.abs(offsetX) - a.getWidth() + 2;
            }
            return new Rectangle(new Position(x, y), width, height);
//            hallList.add(new Rectangle(new Position(x, y), width, height));
        }
//        } else if (offsetX > 0) {
//            if (offsetY > 0) {
//                x = offsetX - this.width;
//                y = offsetY - this.height;
//            } else {
//                x = offsetX - this.width;
//                y = offsetY + a.height;
//            }
//        } else {
//            if (offsetY > 0) {
//                x = offsetX + a.width;
//                y = offsetY - this.height;
//            } else {
//                x = offsetX + a.width;
//                y = offsetY + a.height;
//            }
//        }
//        return rect;
    }


}
