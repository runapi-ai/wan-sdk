package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Animate model slug. */
public final class AnimateModel extends WanValue {
  /** Animation model that preserves the source subject and applies motion. */
  public static final AnimateModel WAN_2_2_ANIMATE_MOVE = new AnimateModel("wan-2.2-animate-move");
  /** Animation model that replaces the source subject using the reference video. */
  public static final AnimateModel WAN_2_2_ANIMATE_REPLACE = new AnimateModel("wan-2.2-animate-replace");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public AnimateModel(String value) {
    super(value);
  }
}
