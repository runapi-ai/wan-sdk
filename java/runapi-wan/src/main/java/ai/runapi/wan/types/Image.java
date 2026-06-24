package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Generated image result. */
public final class Image {
  @JsonProperty("url")
  private String url;

  /** Image URL. */
  public String getUrl() {
    return url;
  }
}
