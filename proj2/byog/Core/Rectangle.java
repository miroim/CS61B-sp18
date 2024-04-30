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

    public double distance(Rectangle a) {
        return Math.sqrt(Math.pow(this.position.getX() - a.position.getX(), 2)
                            + Math.pow(this.position.getY() - a.position.getY(), 2));
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

    // return position y of top side
    public int getTopSide() {
        return this.position.getY() + this.height;
    }

    // return position x of right side
    public int getRightSide() {
        return this.position.getX() + this.width;
    }


}
