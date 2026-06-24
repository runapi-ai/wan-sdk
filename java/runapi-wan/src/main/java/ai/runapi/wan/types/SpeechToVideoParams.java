package ai.runapi.wan.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Parameters for Wan speech-to-video generation. */
public final class SpeechToVideoParams {
  private final String model;
  private final String sourceImageUrl;
  private final String sourceAudioUrl;
  private final String prompt;
  private final String callbackUrl;
  private final Integer numFrames;
  private final Integer framesPerSecond;
  private final String outputResolution;
  private final String negativePrompt;
  private final Integer seed;
  private final Integer numInferenceSteps;
  private final Double guidanceScale;
  private final Double shift;
  private final Boolean enableSafetyChecker;

  private SpeechToVideoParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.sourceImageUrl = WanParamUtils.requireNonBlank(builder.sourceImageUrl, "sourceImageUrl");
    this.sourceAudioUrl = WanParamUtils.requireNonBlank(builder.sourceAudioUrl, "sourceAudioUrl");
    this.prompt = builder.prompt;
    this.callbackUrl = builder.callbackUrl;
    this.numFrames = builder.numFrames;
    this.framesPerSecond = builder.framesPerSecond;
    this.outputResolution = builder.outputResolution;
    this.negativePrompt = builder.negativePrompt;
    this.seed = builder.seed;
    this.numInferenceSteps = builder.numInferenceSteps;
    this.guidanceScale = builder.guidanceScale;
    this.shift = builder.shift;
    this.enableSafetyChecker = builder.enableSafetyChecker;
  }

  /** Creates a new SpeechToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/speech-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("source_image_url", sourceImageUrl);
    raw.put("source_audio_url", sourceAudioUrl);
    raw.put("prompt", prompt);
    raw.put("callback_url", callbackUrl);
    raw.put("num_frames", numFrames);
    raw.put("frames_per_second", framesPerSecond);
    raw.put("output_resolution", outputResolution);
    raw.put("negative_prompt", negativePrompt);
    raw.put("seed", seed);
    raw.put("num_inference_steps", numInferenceSteps);
    raw.put("guidance_scale", guidanceScale);
    raw.put("shift", shift);
    raw.put("enable_safety_checker", enableSafetyChecker);
    return WanParamUtils.compact(raw);
  }

  /** Builder for {@link SpeechToVideoParams}. */
  public static final class Builder {
    private String model;
    private String sourceImageUrl;
    private String sourceAudioUrl;
    private String prompt;
    private String callbackUrl;
    private Integer numFrames;
    private Integer framesPerSecond;
    private String outputResolution;
    private String negativePrompt;
    private Integer seed;
    private Integer numInferenceSteps;
    private Double guidanceScale;
    private Double shift;
    private Boolean enableSafetyChecker;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(SpeechToVideoModel value) {
      this.model = value.value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = WanParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }

    /** Sets the source image URL. */
    public Builder sourceImageUrl(String value) {
      this.sourceImageUrl = WanParamUtils.requireNonBlank(value, "sourceImageUrl");
      return this;
    }

    /** Sets the source audio URL. */
    public Builder sourceAudioUrl(String value) {
      this.sourceAudioUrl = WanParamUtils.requireNonBlank(value, "sourceAudioUrl");
      return this;
    }

    /** Sets the text prompt. */
    public Builder prompt(String value) {
      this.prompt = WanParamUtils.requireNonBlank(value, "prompt");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = WanParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Sets the number of output frames. */
    public Builder numFrames(int value) {
      this.numFrames = value;
      return this;
    }

    /** Sets the frames per second. */
    public Builder framesPerSecond(int value) {
      this.framesPerSecond = value;
      return this;
    }

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = WanParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the negative prompt describing what to avoid. */
    public Builder negativePrompt(String value) {
      this.negativePrompt = WanParamUtils.requireNonBlank(value, "negativePrompt");
      return this;
    }

    /** Sets the random seed. */
    public Builder seed(int value) {
      this.seed = value;
      return this;
    }

    /** Sets the number of inference steps. */
    public Builder numInferenceSteps(int value) {
      this.numInferenceSteps = value;
      return this;
    }

    /** Sets the guidance scale. */
    public Builder guidanceScale(double value) {
      this.guidanceScale = value;
      return this;
    }

    /** Sets the noise schedule shift. */
    public Builder shift(double value) {
      this.shift = value;
      return this;
    }

    /** Sets the content safety checker toggle. */
    public Builder enableSafetyChecker(boolean value) {
      this.enableSafetyChecker = value;
      return this;
    }

    /** Builds immutable speech-to-video parameters. */
    public SpeechToVideoParams build() {
      return new SpeechToVideoParams(this);
    }
  }
}
