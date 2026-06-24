package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Text-to-image model slug. */
public final class TextToImageModel extends WanValue {
  /** Standard 2.7 image generation model. */
  public static final TextToImageModel WAN_2_7_IMAGE = new TextToImageModel("wan-2.7-image");
  /** Pro 2.7 image generation model. */
  public static final TextToImageModel WAN_2_7_IMAGE_PRO = new TextToImageModel("wan-2.7-image-pro");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToImageModel(String value) {
    super(value);
  }
}
