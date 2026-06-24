package ai.runapi.wan.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Bounding box coordinates in [x1, y1, x2, y2] order. */
public final class BoundingBox {
  private final int x1;
  private final int y1;
  private final int x2;
  private final int y2;

  private BoundingBox(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  /** Creates a bounding box from corner coordinates. */
  public static BoundingBox of(int x1, int y1, int x2, int y2) {
    return new BoundingBox(x1, y1, x2, y2);
  }

  /** Returns the left coordinate. */
  public int getX1() {
    return x1;
  }

  /** Returns the top coordinate. */
  public int getY1() {
    return y1;
  }

  /** Returns the right coordinate. */
  public int getX2() {
    return x2;
  }

  /** Returns the bottom coordinate. */
  public int getY2() {
    return y2;
  }

  List<Integer> toList() {
    return Collections.unmodifiableList(Arrays.asList(x1, y1, x2, y2));
  }
}
