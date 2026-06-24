package ai.runapi.wan.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for Wan video editing. */
public final class EditVideoParams {
  private final String model;
  private final String sourceVideoUrl;
  private final List<String> sourceVideoUrls;
  private final String prompt;
  private final String callbackUrl;
  private final String negativePrompt;
  private final String referenceImageUrl;
  private final String outputResolution;
  private final String aspectRatio;
  private final Integer durationSeconds;
  private final String audioSetting;
  private final Boolean enablePromptExpansion;
  private final Boolean watermark;
  private final Integer seed;
  private final Boolean enableSafetyChecker;
  private final Boolean audio;
  private final Boolean multiShots;

  private EditVideoParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.sourceVideoUrl = builder.sourceVideoUrl;
    this.sourceVideoUrls = WanParamUtils.strings(builder.sourceVideoUrls);
    this.prompt = builder.prompt;
    this.callbackUrl = builder.callbackUrl;
    this.negativePrompt = builder.negativePrompt;
    this.referenceImageUrl = builder.referenceImageUrl;
    this.outputResolution = builder.outputResolution;
    this.aspectRatio = builder.aspectRatio;
    this.durationSeconds = builder.durationSeconds;
    this.audioSetting = builder.audioSetting;
    this.enablePromptExpansion = builder.enablePromptExpansion;
    this.watermark = builder.watermark;
    this.seed = builder.seed;
    this.enableSafetyChecker = builder.enableSafetyChecker;
    this.audio = builder.audio;
    this.multiShots = builder.multiShots;
  }

  /** Creates a new EditVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/edit-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("source_video_url", sourceVideoUrl);
    raw.put("source_video_urls", sourceVideoUrls);
    raw.put("prompt", prompt);
    raw.put("callback_url", callbackUrl);
    raw.put("negative_prompt", negativePrompt);
    raw.put("reference_image_url", referenceImageUrl);
    raw.put("output_resolution", outputResolution);
    raw.put("aspect_ratio", aspectRatio);
    raw.put("duration_seconds", durationSeconds);
    raw.put("audio_setting", audioSetting);
    raw.put("enable_prompt_expansion", enablePromptExpansion);
    raw.put("watermark", watermark);
    raw.put("seed", seed);
    raw.put("enable_safety_checker", enableSafetyChecker);
    raw.put("audio", audio);
    raw.put("multi_shots", multiShots);
    return WanParamUtils.compact(raw);
  }

  /** Builder for {@link EditVideoParams}. */
  public static final class Builder {
    private String model;
    private String sourceVideoUrl;
    private List<String> sourceVideoUrls;
    private String prompt;
    private String callbackUrl;
    private String negativePrompt;
    private String referenceImageUrl;
    private String outputResolution;
    private String aspectRatio;
    private Integer durationSeconds;
    private String audioSetting;
    private Boolean enablePromptExpansion;
    private Boolean watermark;
    private Integer seed;
    private Boolean enableSafetyChecker;
    private Boolean audio;
    private Boolean multiShots;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(EditVideoModel value) {
      this.model = value.value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = WanParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }

    /** Sets the source video URL. */
    public Builder sourceVideoUrl(String value) {
      this.sourceVideoUrl = WanParamUtils.requireNonBlank(value, "sourceVideoUrl");
      return this;
    }

    /** Sets the source video URLs. */
    public Builder sourceVideoUrls(List<String> values) {
      this.sourceVideoUrls = values;
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

    /** Sets the negative prompt describing what to avoid. */
    public Builder negativePrompt(String value) {
      this.negativePrompt = WanParamUtils.requireNonBlank(value, "negativePrompt");
      return this;
    }

    /** Sets the reference image URL. */
    public Builder referenceImageUrl(String value) {
      this.referenceImageUrl = WanParamUtils.requireNonBlank(value, "referenceImageUrl");
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

    /** Sets the duration in seconds. */
    public Builder durationSeconds(int value) {
      this.durationSeconds = value;
      return this;
    }

    /** Sets the audio setting. */
    public Builder audioSetting(String value) {
      this.audioSetting = WanParamUtils.requireNonBlank(value, "audioSetting");
      return this;
    }

    /** Sets the prompt expansion toggle. */
    public Builder enablePromptExpansion(boolean value) {
      this.enablePromptExpansion = value;
      return this;
    }

    /** Sets the watermark toggle. */
    public Builder watermark(boolean value) {
      this.watermark = value;
      return this;
    }

    /** Sets the random seed. */
    public Builder seed(int value) {
      this.seed = value;
      return this;
    }

    /** Sets the content safety checker toggle. */
    public Builder enableSafetyChecker(boolean value) {
      this.enableSafetyChecker = value;
      return this;
    }

    /** Sets the audio generation toggle. */
    public Builder audio(boolean value) {
      this.audio = value;
      return this;
    }

    /** Sets the multi-shot mode toggle. */
    public Builder multiShots(boolean value) {
      this.multiShots = value;
      return this;
    }

    /** Builds immutable edit-video parameters. */
    public EditVideoParams build() {
      return new EditVideoParams(this);
    }
  }
}
