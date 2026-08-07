package ai.runapi.wan.types;

import ai.runapi.core.errors.ValidationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for Wan text-to-video generation. */
public final class TextToVideoParams {
  private final String model;
  private final String prompt;
  private final String callbackUrl;
  private final Integer durationSeconds;
  private final String outputResolution;
  private final String aspectRatio;
  private final String ratio;
  private final String negativePrompt;
  private final List<String> referenceImageUrls;
  private final List<String> referenceVideoUrls;
  private final String firstFrameImageUrl;
  private final String referenceAudioUrl;
  private final Boolean enablePromptExpansion;
  private final Integer seed;
  private final String acceleration;
  private final Boolean enableSafetyChecker;
  private final Boolean watermark;
  private final String backgroundAudioUrl;
  private final Boolean multiShots;

  private TextToVideoParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.prompt = WanParamUtils.requireNonBlank(builder.prompt, "prompt");
    this.callbackUrl = builder.callbackUrl;
    this.durationSeconds = builder.durationSeconds;
    this.outputResolution = builder.outputResolution;
    this.aspectRatio = builder.aspectRatio;
    this.ratio = builder.ratio;
    this.negativePrompt = builder.negativePrompt;
    this.referenceImageUrls = WanParamUtils.strings(builder.referenceImageUrls);
    this.referenceVideoUrls = WanParamUtils.strings(builder.referenceVideoUrls);
    this.firstFrameImageUrl = builder.firstFrameImageUrl;
    this.referenceAudioUrl = builder.referenceAudioUrl;
    this.enablePromptExpansion = builder.enablePromptExpansion;
    this.seed = builder.seed;
    this.acceleration = builder.acceleration;
    this.enableSafetyChecker = builder.enableSafetyChecker;
    this.watermark = builder.watermark;
    this.backgroundAudioUrl = builder.backgroundAudioUrl;
    this.multiShots = builder.multiShots;
    validateR2vReferenceMedia();
  }

  /** Creates a new TextToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/text-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("prompt", prompt);
    raw.put("callback_url", callbackUrl);
    raw.put("duration_seconds", durationSeconds);
    raw.put("output_resolution", outputResolution);
    raw.put("aspect_ratio", aspectRatio);
    raw.put("ratio", ratio);
    raw.put("negative_prompt", negativePrompt);
    raw.put("reference_image_urls", referenceImageUrls);
    raw.put("reference_video_urls", referenceVideoUrls);
    raw.put("first_frame_image_url", firstFrameImageUrl);
    raw.put("reference_audio_url", referenceAudioUrl);
    raw.put("enable_prompt_expansion", enablePromptExpansion);
    raw.put("seed", seed);
    raw.put("acceleration", acceleration);
    raw.put("enable_safety_checker", enableSafetyChecker);
    raw.put("watermark", watermark);
    raw.put("background_audio_url", backgroundAudioUrl);
    raw.put("multi_shots", multiShots);
    return WanParamUtils.compact(raw);
  }

  private void validateR2vReferenceMedia() {
    if (!TextToVideoModel.WAN_2_7_R2V.value().equals(model)) {
      return;
    }
    int count = size(referenceImageUrls) + size(referenceVideoUrls);
    if (count < 1 || count > 5) {
      throw new ValidationException(
          "reference_image_urls or reference_video_urls must include between 1 and 5 total entries");
    }
  }

  private static int size(List<?> values) {
    return values == null ? 0 : values.size();
  }

  /** Builder for {@link TextToVideoParams}. */
  public static final class Builder {
    private String model;
    private String prompt;
    private String callbackUrl;
    private Integer durationSeconds;
    private String outputResolution;
    private String aspectRatio;
    private String ratio;
    private String negativePrompt;
    private List<String> referenceImageUrls;
    private List<String> referenceVideoUrls;
    private String firstFrameImageUrl;
    private String referenceAudioUrl;
    private Boolean enablePromptExpansion;
    private Integer seed;
    private String acceleration;
    private Boolean enableSafetyChecker;
    private Boolean watermark;
    private String backgroundAudioUrl;
    private Boolean multiShots;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToVideoModel value) {
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

    /** Sets the alternate aspect ratio value. */
    public Builder ratio(String value) {
      this.ratio = WanParamUtils.requireNonBlank(value, "ratio");
      return this;
    }

    /** Sets the negative prompt describing what to avoid. */
    public Builder negativePrompt(String value) {
      this.negativePrompt = WanParamUtils.requireNonBlank(value, "negativePrompt");
      return this;
    }

    /** Sets the reference image URLs. */
    public Builder referenceImageUrls(List<String> values) {
      this.referenceImageUrls = values;
      return this;
    }

    /** Sets the reference video URLs. */
    public Builder referenceVideoUrls(List<String> values) {
      this.referenceVideoUrls = values;
      return this;
    }

    /** Sets the first frame image URL. */
    public Builder firstFrameImageUrl(String value) {
      this.firstFrameImageUrl = WanParamUtils.requireNonBlank(value, "firstFrameImageUrl");
      return this;
    }

    /** Sets the reference audio URL. */
    public Builder referenceAudioUrl(String value) {
      this.referenceAudioUrl = WanParamUtils.requireNonBlank(value, "referenceAudioUrl");
      return this;
    }

    /** Sets the prompt expansion toggle. */
    public Builder enablePromptExpansion(boolean value) {
      this.enablePromptExpansion = value;
      return this;
    }

    /** Sets the random seed; unsupported by wan-2.6-text-to-video. */
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

    /** Sets the background audio URL. */
    public Builder backgroundAudioUrl(String value) {
      this.backgroundAudioUrl = WanParamUtils.requireNonBlank(value, "backgroundAudioUrl");
      return this;
    }

    /** Controls whether the generated video uses multiple shots with transitions instead of one continuous shot. */
    public Builder multiShots(boolean value) {
      this.multiShots = value;
      return this;
    }

    /** Builds immutable text-to-video parameters. */
    public TextToVideoParams build() {
      return new TextToVideoParams(this);
    }
  }
}
