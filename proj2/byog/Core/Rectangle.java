package byog.Core;


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
    public Position getRelPosition(Rectangle a) {
        int offsetX = a.position.getX() - this.position.getX();
        int offsetY = a.position.getY() - this.position.getY();
        int x, y;
        if (offsetX == 0) {
            if (offsetY > 0) {
                x = offsetX;
                y = offsetY - this.height;
            } else {
                x = offsetX;
                y = offsetY + a.height;
            }
        } else if (offsetY == 0) {
            if (offsetX > 0) {
                x = offsetX - this.width;
                y = offsetY;
            } else {
                x = offsetX + a.width;
                y = offsetY;
            }
        } else if (offsetX > 0) {
            if (offsetY > 0) {
                x = offsetX - this.width;
                y = offsetY - this.height;
            } else {
                x = offsetX - this.width;
                y = offsetY + a.height;
            }
        } else {
            if (offsetY > 0) {
                x = offsetX + a.width;
                y = offsetY - this.height;
            } else {
                x = offsetX + a.width;
                y = offsetY + a.height;
            }
        }
        return new Position(x, y);
    }


}
