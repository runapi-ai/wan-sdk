package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Image-to-video model slug. */
public final class ImageToVideoModel extends WanValue {
  /** 2.2 turbo image-to-video model. */
  public static final ImageToVideoModel WAN_2_2_A14B_IMAGE_TO_VIDEO_TURBO =
      new ImageToVideoModel("wan-2.2-a14b-image-to-video-turbo");
  /** 2.5 image-to-video model. */
  public static final ImageToVideoModel WAN_2_5_IMAGE_TO_VIDEO = new ImageToVideoModel("wan-2.5-image-to-video");
  /** 2.6 image-to-video model. */
  public static final ImageToVideoModel WAN_2_6_IMAGE_TO_VIDEO = new ImageToVideoModel("wan-2.6-image-to-video");
  /** 2.6 flash image-to-video model. */
  public static final ImageToVideoModel WAN_2_6_FLASH_IMAGE_TO_VIDEO =
      new ImageToVideoModel("wan-2.6-flash-image-to-video");
  /** 2.7 image-to-video model. */
  public static final ImageToVideoModel WAN_2_7_IMAGE_TO_VIDEO = new ImageToVideoModel("wan-2.7-image-to-video");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public ImageToVideoModel(String value) {
    super(value);
  }
}
