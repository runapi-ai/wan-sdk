package ai.runapi.wan.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Parameters for Wan animation. */
public final class AnimateParams {
  private final String model;
  private final String sourceImageUrl;
  private final String referenceVideoUrl;
  private final String callbackUrl;
  private final String outputResolution;
  private final Boolean enableSafetyChecker;

  private AnimateParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.sourceImageUrl = WanParamUtils.requireNonBlank(builder.sourceImageUrl, "sourceImageUrl");
    this.referenceVideoUrl = WanParamUtils.requireNonBlank(builder.referenceVideoUrl, "referenceVideoUrl");
    this.callbackUrl = builder.callbackUrl;
    this.outputResolution = builder.outputResolution;
    this.enableSafetyChecker = builder.enableSafetyChecker;
  }

  /** Creates a new AnimateParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/animate";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("source_image_url", sourceImageUrl);
    raw.put("reference_video_url", referenceVideoUrl);
    raw.put("callback_url", callbackUrl);
    raw.put("output_resolution", outputResolution);
    raw.put("enable_safety_checker", enableSafetyChecker);
    return WanParamUtils.compact(raw);
  }

  /** Builder for {@link AnimateParams}. */
  public static final class Builder {
    private String model;
    private String sourceImageUrl;
    private String referenceVideoUrl;
    private String callbackUrl;
    private String outputResolution;
    private Boolean enableSafetyChecker;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(AnimateModel value) {
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

    /** Sets the reference video URL. */
    public Builder referenceVideoUrl(String value) {
      this.referenceVideoUrl = WanParamUtils.requireNonBlank(value, "referenceVideoUrl");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = WanParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = WanParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the content safety checker toggle. */
    public Builder enableSafetyChecker(boolean value) {
      this.enableSafetyChecker = value;
      return this;
    }

    /** Builds immutable animate parameters. */
    public AnimateParams build() {
      return new AnimateParams(this);
    }
  }
}
