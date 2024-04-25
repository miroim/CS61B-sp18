package byog.Core;

import org.junit.Test;
import static org.junit.Assert.*;

public class Rectangle {
    public Position position;
    public int width;
    public int height;

    // ┛┗┏ ━┃ ┓ mark for draw rectangle
    public Rectangle(Position p, int w, int h) {
        position = p;
        width = w;
        height = h;
    }

    public static boolean isPointOverlapRectangle(Position p, Rectangle a) {
        return (p.x >= a.position.x && p.x <= a.position.x + a.width
                && p.y >= a.position.y && p.y <= a.position.y + a.height);
    }

    public boolean isOverlap(Rectangle b) {
        /*
                bLeftTop       bRightTop
                        ┏━━━━━┓
                        ┃  b  ┃
                        ┗━━━━━┛
             b.position       bRightBottom
         */

        Position bLeftTop = new Position(b.position.x, b.position.y + b.height);
        Position bRightTop = new Position(b.position.x + b.width, b.position.y + b.height);
        Position bRightBottom = new Position(b.position.x + b.width, b.position.y);

        return isPointOverlapRectangle(b.position, this)
                || isPointOverlapRectangle(bLeftTop, this)
                || isPointOverlapRectangle(bRightTop, this)
                || isPointOverlapRectangle(bRightBottom, this);
    }

}
