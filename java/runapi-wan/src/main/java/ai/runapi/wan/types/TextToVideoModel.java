package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Text-to-video model slug. */
public final class TextToVideoModel extends WanValue {
  /** 2.2 turbo text-to-video model. */
  public static final TextToVideoModel WAN_2_2_A14B_TEXT_TO_VIDEO_TURBO =
      new TextToVideoModel("wan-2.2-a14b-text-to-video-turbo");
  /** 2.5 text-to-video model. */
  public static final TextToVideoModel WAN_2_5_TEXT_TO_VIDEO = new TextToVideoModel("wan-2.5-text-to-video");
  /** 2.6 text-to-video model. */
  public static final TextToVideoModel WAN_2_6_TEXT_TO_VIDEO = new TextToVideoModel("wan-2.6-text-to-video");
  /** 2.7 text-to-video model. */
  public static final TextToVideoModel WAN_2_7_TEXT_TO_VIDEO = new TextToVideoModel("wan-2.7-text-to-video");
  /** 2.7 reference-to-video model. */
  public static final TextToVideoModel WAN_2_7_R2V = new TextToVideoModel("wan-2.7-r2v");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToVideoModel(String value) {
    super(value);
  }
}
