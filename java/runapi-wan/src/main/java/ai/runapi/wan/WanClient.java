package ai.runapi.wan;

import ai.runapi.core.BaseClient;
import ai.runapi.core.ClientOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.wan.resources.AnimateResource;
import ai.runapi.wan.resources.EditVideoResource;
import ai.runapi.wan.resources.ImageToVideoResource;
import ai.runapi.wan.resources.SpeechToVideoResource;
import ai.runapi.wan.resources.TextToImageResource;
import ai.runapi.wan.resources.TextToVideoResource;
import java.net.URI;

/** Wan model-family Java SDK client. */
public final class WanClient extends BaseClient {
  private final TextToVideoResource textToVideo;
  private final ImageToVideoResource imageToVideo;
  private final SpeechToVideoResource speechToVideo;
  private final AnimateResource animate;
  private final TextToImageResource textToImage;
  private final EditVideoResource editVideo;

  private WanClient(ClientOptions options) {
    super(options);
    this.textToVideo = new DefaultTextToVideoResource(transport(), options());
    this.imageToVideo = new DefaultImageToVideoResource(transport(), options());
    this.speechToVideo = new DefaultSpeechToVideoResource(transport(), options());
    this.animate = new DefaultAnimateResource(transport(), options());
    this.textToImage = new DefaultTextToImageResource(transport(), options());
    this.editVideo = new DefaultEditVideoResource(transport(), options());
  }

  /** Creates a new Wan client builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Text-to-video generation operations. */
  public TextToVideoResource textToVideo() {
    return textToVideo;
  }

  /** Image-to-video generation operations. */
  public ImageToVideoResource imageToVideo() {
    return imageToVideo;
  }

  /** Speech-to-video generation operations. */
  public SpeechToVideoResource speechToVideo() {
    return speechToVideo;
  }

  /** Animation operations. */
  public AnimateResource animate() {
    return animate;
  }

  /** Text-to-image generation operations. */
  public TextToImageResource textToImage() {
    return textToImage;
  }

  /** Video editing operations. */
  public EditVideoResource editVideo() {
    return editVideo;
  }

  /** Builder for {@link WanClient}. */
  public static final class Builder extends BaseClient.Builder<Builder> {
    private Builder() {}

    /** Sets the API key. If omitted, the SDK reads {@code RUNAPI_API_KEY}. */
    @Override
    public Builder apiKey(String value) {
      return super.apiKey(value);
    }

    /** Sets the base URL. */
    @Override
    public Builder baseUrl(String value) {
      return super.baseUrl(value);
    }

    /** Sets the base URL from a URI. */
    @Override
    public Builder baseUrl(URI value) {
      return super.baseUrl(value);
    }

    /** Sets a custom transport. User-provided transports are not closed by SDK clients. */
    @Override
    public Builder transport(HttpTransport value) {
      return super.transport(value);
    }

    /** Builds a Wan client. */
    @Override
    public WanClient build() {
      return new WanClient(options.build());
    }
  }
}
