package ai.runapi.wan.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for Wan text-to-image generation. */
public final class TextToImageParams {
  private final String model;
  private final String prompt;
  private final String callbackUrl;
  private final String aspectRatio;
  private final String outputResolution;
  private final Integer outputCount;
  private final Boolean enableSequential;
  private final Boolean thinkingMode;
  private final Boolean watermark;
  private final Integer seed;
  private final Boolean enableSafetyChecker;
  private final List<String> sourceImageUrls;
  private final List<ColorPaletteItem> colorPalette;
  private final List<BoundingBoxGroup> bboxList;

  private TextToImageParams(Builder builder) {
    this.model = WanParamUtils.requireNonBlankTrim(builder.model, "model");
    this.prompt = WanParamUtils.requireNonBlank(builder.prompt, "prompt");
    this.callbackUrl = builder.callbackUrl;
    this.aspectRatio = builder.aspectRatio;
    this.outputResolution = builder.outputResolution;
    this.outputCount = builder.outputCount;
    this.enableSequential = builder.enableSequential;
    this.thinkingMode = builder.thinkingMode;
    this.watermark = builder.watermark;
    this.seed = builder.seed;
    this.enableSafetyChecker = builder.enableSafetyChecker;
    this.sourceImageUrls = WanParamUtils.strings(builder.sourceImageUrls);
    this.colorPalette = WanParamUtils.list(builder.colorPalette, "colorPalette");
    this.bboxList = WanParamUtils.list(builder.bboxList, "bboxList");
  }

  /** Creates a new TextToImageParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "wan/text-to-image";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", model);
    raw.put("prompt", prompt);
    raw.put("callback_url", callbackUrl);
    raw.put("aspect_ratio", aspectRatio);
    raw.put("output_resolution", outputResolution);
    raw.put("output_count", outputCount);
    raw.put("enable_sequential", enableSequential);
    raw.put("thinking_mode", thinkingMode);
    raw.put("watermark", watermark);
    raw.put("seed", seed);
    raw.put("enable_safety_checker", enableSafetyChecker);
    raw.put("source_image_urls", sourceImageUrls);
    raw.put("color_palette", colorPaletteToMaps(colorPalette));
    raw.put("bbox_list", boundingBoxGroupsToLists(bboxList));
    return WanParamUtils.compact(raw);
  }

  private static List<Map<String, Object>> colorPaletteToMaps(List<ColorPaletteItem> values) {
    if (values == null) {
      return null;
    }
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (ColorPaletteItem item : values) {
      result.add(item.toMap());
    }
    return result;
  }

  private static List<List<List<Integer>>> boundingBoxGroupsToLists(List<BoundingBoxGroup> values) {
    if (values == null) {
      return null;
    }
    List<List<List<Integer>>> result = new ArrayList<List<List<Integer>>>();
    for (BoundingBoxGroup group : values) {
      result.add(group.toList());
    }
    return result;
  }

  /** Builder for {@link TextToImageParams}. */
  public static final class Builder {
    private String model;
    private String prompt;
    private String callbackUrl;
    private String aspectRatio;
    private String outputResolution;
    private Integer outputCount;
    private Boolean enableSequential;
    private Boolean thinkingMode;
    private Boolean watermark;
    private Integer seed;
    private Boolean enableSafetyChecker;
    private List<String> sourceImageUrls;
    private List<ColorPaletteItem> colorPalette;
    private List<BoundingBoxGroup> bboxList;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToImageModel value) {
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

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = WanParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the number of generated outputs. */
    public Builder outputCount(int value) {
      this.outputCount = value;
      return this;
    }

    /** Sets the sequential generation toggle. */
    public Builder enableSequential(boolean value) {
      this.enableSequential = value;
      return this;
    }

    /** Sets the enhanced reasoning toggle. */
    public Builder thinkingMode(boolean value) {
      this.thinkingMode = value;
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

    /** Sets the source image URLs. */
    public Builder sourceImageUrls(List<String> values) {
      this.sourceImageUrls = values;
      return this;
    }

    /** Sets the color palette constraints. */
    public Builder colorPalette(List<ColorPaletteItem> values) {
      this.colorPalette = values;
      return this;
    }

    /** Sets the bounding box constraints. */
    public Builder bboxList(List<BoundingBoxGroup> values) {
      this.bboxList = values;
      return this;
    }

    /** Builds immutable text-to-image parameters. */
    public TextToImageParams build() {
      return new TextToImageParams(this);
    }
  }
}
