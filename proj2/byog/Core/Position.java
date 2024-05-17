package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    private final int x;
    private final int y;

    public Position(int a, int b) {
        x = a;
        y = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // return distance between two point
    public double getDistance(Position p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    // Determining whether two position is equal

    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }
}
