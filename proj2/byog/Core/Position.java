package byog.Core;

public class Position {
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
