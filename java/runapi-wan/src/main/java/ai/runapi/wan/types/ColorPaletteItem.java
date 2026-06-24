package ai.runapi.wan.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Color palette constraint for text-to-image generation. */
public final class ColorPaletteItem {
  private final String hex;
  private final Double ratio;

  private ColorPaletteItem(Builder builder) {
    this.hex = WanParamUtils.requireNonBlank(builder.hex, "hex");
    this.ratio = builder.ratio;
  }

  /** Creates a new color palette item builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the hex color. */
  public String getHex() {
    return hex;
  }

  /** Returns the optional color ratio. */
  public Double getRatio() {
    return ratio;
  }

  Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("hex", hex);
    raw.put("ratio", ratio);
    // Compact here too: the parent params only compact the top-level map, so an
    // unset optional ratio would otherwise be serialized as "ratio": null inside
    // the color_palette list instead of being omitted.
    return WanParamUtils.compact(raw);
  }

  /** Builder for {@link ColorPaletteItem}. */
  public static final class Builder {
    private String hex;
    private Double ratio;

    private Builder() {}

    /** Sets the hex color. */
    public Builder hex(String value) {
      this.hex = WanParamUtils.requireNonBlank(value, "hex");
      return this;
    }

    /** Sets the color ratio. */
    public Builder ratio(double value) {
      this.ratio = value;
      return this;
    }

    /** Builds an immutable color palette item. */
    public ColorPaletteItem build() {
      return new ColorPaletteItem(this);
    }
  }
}
