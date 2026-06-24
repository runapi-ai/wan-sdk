package ai.runapi.wan.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Bounding boxes for one source image. */
public final class BoundingBoxGroup {
  private final List<BoundingBox> boxes;

  private BoundingBoxGroup(List<BoundingBox> boxes) {
    this.boxes = WanParamUtils.requiredList(boxes, "boxes");
  }

  /** Creates a group of bounding boxes. */
  public static BoundingBoxGroup of(BoundingBox... boxes) {
    return new BoundingBoxGroup(Arrays.asList(boxes));
  }

  /** Returns the bounding boxes in this group. */
  public List<BoundingBox> getBoxes() {
    return boxes;
  }

  List<List<Integer>> toList() {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    for (BoundingBox box : boxes) {
      result.add(box.toList());
    }
    return Collections.unmodifiableList(result);
  }
}
