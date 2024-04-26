package byog.Core;


public class Rectangle {
    public Position position;
    public int width;
    public int height;

    public Rectangle(Position p, int w, int h) {
        position = p;
        width = w;
        height = h;
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
    public Position direction(Rectangle a) {
        int x = a.position.getX() - this.position.getX();
        int y = a.position.getY() - this.position.getY();
        return new Position(x, y);
    }

}
