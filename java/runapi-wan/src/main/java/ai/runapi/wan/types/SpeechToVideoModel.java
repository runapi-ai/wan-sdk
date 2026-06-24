package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Speech-to-video model slug. */
public final class SpeechToVideoModel extends WanValue {
  /** 2.2 turbo speech-to-video model. */
  public static final SpeechToVideoModel WAN_2_2_A14B_SPEECH_TO_VIDEO_TURBO =
      new SpeechToVideoModel("wan-2.2-a14b-speech-to-video-turbo");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public SpeechToVideoModel(String value) {
    super(value);
  }
}
