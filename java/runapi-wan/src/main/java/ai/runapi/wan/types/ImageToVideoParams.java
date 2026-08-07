package ai.runapi.wan.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Parameters for Wan image-to-video generation. */
public final class ImageToVideoParams {
  private final String model;
  private final String prompt;
  private final String callbackUrl;
  private final String firstFrameImageUrl;
  private final String lastFrameImageUrl;
  private final String sourceVideoUrl;
  private final Integer durationSeconds;
  private final String outputResolution;
  private final String aspectRatio;
  private final String negativePrompt;
  private final Boolean enablePromptExpansion;
  private final Integer seed;
  private final String acceleration;
  private final Boolean enableSafetyChecker;
  private final Boolean watermark;
  private final Boolean audio;
  private final Boolean multiShots;
  private final String drivingAudioUrl;
  private final String backgroundAudioUrl;
  private final String ratio;

  private ImageToVideoParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.prompt = builder.prompt;
    this.callbackUrl = builder.callbackUrl;
    this.firstFrameImageUrl = builder.firstFrameImageUrl;
    this.lastFrameImageUrl = builder.lastFrameImageUrl;
    this.sourceVideoUrl = builder.sourceVideoUrl;
    this.durationSeconds = builder.durationSeconds;
    this.outputResolution = builder.outputResolution;
    this.aspectRatio = builder.aspectRatio;
    this.negativePrompt = builder.negativePrompt;
    this.enablePromptExpansion = builder.enablePromptExpansion;
    this.seed = builder.seed;
    this.acceleration = builder.acceleration;
    this.enableSafetyChecker = builder.enableSafetyChecker;
    this.watermark = builder.watermark;
    this.audio = builder.audio;
    this.multiShots = builder.multiShots;
    this.drivingAudioUrl = builder.drivingAudioUrl;
    this.backgroundAudioUrl = builder.backgroundAudioUrl;
    this.ratio = builder.ratio;
  }

  /** Creates a new ImageToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/image-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("prompt", prompt);
    raw.put("callback_url", callbackUrl);
    raw.put("first_frame_image_url", firstFrameImageUrl);
    raw.put("last_frame_image_url", lastFrameImageUrl);
    raw.put("source_video_url", sourceVideoUrl);
    raw.put("duration_seconds", durationSeconds);
    raw.put("output_resolution", outputResolution);
    raw.put("aspect_ratio", aspectRatio);
    raw.put("negative_prompt", negativePrompt);
    raw.put("enable_prompt_expansion", enablePromptExpansion);
    raw.put("seed", seed);
    raw.put("acceleration", acceleration);
    raw.put("enable_safety_checker", enableSafetyChecker);
    raw.put("watermark", watermark);
    raw.put("audio", audio);
    raw.put("multi_shots", multiShots);
    raw.put("driving_audio_url", drivingAudioUrl);
    raw.put("background_audio_url", backgroundAudioUrl);
    raw.put("ratio", ratio);
    return WanParamUtils.compact(raw);
  }

  /** Builder for {@link ImageToVideoParams}. */
  public static final class Builder {
    private String model;
    private String prompt;
    private String callbackUrl;
    private String firstFrameImageUrl;
    private String lastFrameImageUrl;
    private String sourceVideoUrl;
    private Integer durationSeconds;
    private String outputResolution;
    private String aspectRatio;
    private String negativePrompt;
    private Boolean enablePromptExpansion;
    private Integer seed;
    private String acceleration;
    private Boolean enableSafetyChecker;
    private Boolean watermark;
    private Boolean audio;
    private Boolean multiShots;
    private String drivingAudioUrl;
    private String backgroundAudioUrl;
    private String ratio;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(ImageToVideoModel value) {
      this.model = value.value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = WanParamUtils.requireNonBlankTrim(value, "model");
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

    /** Sets the first frame image URL. */
    public Builder firstFrameImageUrl(String value) {
      this.firstFrameImageUrl = WanParamUtils.requireNonBlank(value, "firstFrameImageUrl");
      return this;
    }

    /** Sets the last frame image URL. */
    public Builder lastFrameImageUrl(String value) {
      this.lastFrameImageUrl = WanParamUtils.requireNonBlank(value, "lastFrameImageUrl");
      return this;
    }

    /** Sets the source video URL. */
    public Builder sourceVideoUrl(String value) {
      this.sourceVideoUrl = WanParamUtils.requireNonBlank(value, "sourceVideoUrl");
      return this;
    }

    /** Sets the duration in seconds. */
    public Builder durationSeconds(int value) {
      this.durationSeconds = value;
      return this;
    }

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = WanParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the output aspect ratio. */
    public Builder aspectRatio(AspectRatio value) {
      this.aspectRatio = value.value();
      return this;
    }

    /** Sets the output aspect ratio. */
    public Builder aspectRatio(String value) {
      this.aspectRatio = WanParamUtils.requireNonBlankTrim(value, "aspectRatio");
      return this;
    }

    /** Sets the negative prompt describing what to avoid. */
    public Builder negativePrompt(String value) {
      this.negativePrompt = WanParamUtils.requireNonBlank(value, "negativePrompt");
      return this;
    }

    /** Sets the prompt expansion toggle. */
    public Builder enablePromptExpansion(boolean value) {
      this.enablePromptExpansion = value;
      return this;
    }

    /** Sets the random seed; unsupported by wan-2.6 image-to-video models. */
    public Builder seed(int value) {
      this.seed = value;
      return this;
    }

    /** Sets the acceleration mode. */
    public Builder acceleration(String value) {
      this.acceleration = WanParamUtils.requireNonBlank(value, "acceleration");
      return this;
    }

    /** Sets the content safety checker toggle. */
    public Builder enableSafetyChecker(boolean value) {
      this.enableSafetyChecker = value;
      return this;
    }

    /** Sets the watermark toggle. */
    public Builder watermark(boolean value) {
      this.watermark = value;
      return this;
    }

    /** Sets the audio generation toggle. */
    public Builder audio(boolean value) {
      this.audio = value;
      return this;
    }

    /** Controls whether the generated video uses multiple shots with transitions instead of one continuous shot. */
    public Builder multiShots(boolean value) {
      this.multiShots = value;
      return this;
    }

    /** Sets the driving audio URL. */
    public Builder drivingAudioUrl(String value) {
      this.drivingAudioUrl = WanParamUtils.requireNonBlank(value, "drivingAudioUrl");
      return this;
    }

    /** Sets the background audio URL. */
    public Builder backgroundAudioUrl(String value) {
      this.backgroundAudioUrl = WanParamUtils.requireNonBlank(value, "backgroundAudioUrl");
      return this;
    }

    /** Sets the alternate aspect ratio value. */
    public Builder ratio(String value) {
      this.ratio = WanParamUtils.requireNonBlank(value, "ratio");
      return this;
    }

    /** Builds immutable image-to-video parameters. */
    public ImageToVideoParams build() {
      return new ImageToVideoParams(this);
    }
  }
}
