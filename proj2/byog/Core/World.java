package byog.Core;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class World {
    private static final long SEED = 313;
    private static final Random RANDOM = new Random(SEED);
    public List<Rectangle> rectList;
    private int num = RandomUtils.uniform(RANDOM, 10, 15);

    public World() {
        rectList = new ArrayList<Rectangle>();
    }

    public Rectangle randomRectangle() {
        int min = 4;
        int max = 10;
        int x = RandomUtils.uniform(RANDOM, Game.WIDTH - max);
        int y = RandomUtils.uniform(RANDOM, Game.HEIGHT - max);
        Position p = new Position(x, y);
        int width = RandomUtils.uniform(RANDOM, min, max);
        int height = RandomUtils.uniform(RANDOM, min, max);

        return new Rectangle(p,width,height);
    }

    public void addRandomRectangle() {
        for (int i = 0; i < num; i++) {
            Rectangle newRect = randomRectangle();

            if (rectList.isEmpty()) {
                rectList.add(newRect);
            } else {
                boolean overlaps = false;
                for (Rectangle rect : rectList) {
                    if (rect.isOverlap(newRect)) {
                        overlaps = true;
                        break;
                    }
                }
                if (!overlaps) {
                    rectList.add(newRect);
                }
            }
        }
    }
}
