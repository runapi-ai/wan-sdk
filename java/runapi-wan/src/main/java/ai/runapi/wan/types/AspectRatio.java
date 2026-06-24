package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Aspect ratio value. */
public final class AspectRatio extends WanValue {
  /** Square 1:1 aspect ratio. */
  public static final AspectRatio SQUARE = new AspectRatio("1:1");
  /** Landscape 16:9 aspect ratio. */
  public static final AspectRatio LANDSCAPE = new AspectRatio("16:9");
  /** Portrait 9:16 aspect ratio. */
  public static final AspectRatio PORTRAIT = new AspectRatio("9:16");
  /** Four-by-three aspect ratio. */
  public static final AspectRatio FOUR_THREE = new AspectRatio("4:3");
  /** Three-by-four aspect ratio. */
  public static final AspectRatio THREE_FOUR = new AspectRatio("3:4");
  /** Ultrawide 21:9 aspect ratio. */
  public static final AspectRatio TWENTY_ONE_NINE = new AspectRatio("21:9");
  /** Panoramic 8:1 aspect ratio. */
  public static final AspectRatio EIGHT_ONE = new AspectRatio("8:1");
  /** Tall panoramic 1:8 aspect ratio. */
  public static final AspectRatio ONE_EIGHT = new AspectRatio("1:8");

  /** Creates an aspect ratio value from a literal ratio string. */
  @JsonCreator
  public AspectRatio(String value) {
    super(value);
  }
}
