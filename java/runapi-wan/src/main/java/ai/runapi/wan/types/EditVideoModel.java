package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Edit-video model slug. */
public final class EditVideoModel extends WanValue {
  /** 2.6 video editing model. */
  public static final EditVideoModel WAN_2_6_EDIT_VIDEO = new EditVideoModel("wan-2.6-edit-video");
  /** 2.6 flash video editing model. */
  public static final EditVideoModel WAN_2_6_FLASH_EDIT_VIDEO = new EditVideoModel("wan-2.6-flash-edit-video");
  /** 2.7 video editing model. */
  public static final EditVideoModel WAN_2_7_EDIT_VIDEO = new EditVideoModel("wan-2.7-edit-video");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public EditVideoModel(String value) {
    super(value);
  }
}
