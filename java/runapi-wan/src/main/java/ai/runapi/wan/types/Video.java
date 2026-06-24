package ai.runapi.wan.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Generated video result. */
public final class Video {
  @JsonProperty("url")
  private String url;

  /** Video URL. */
  public String getUrl() {
    return url;
  }
}
