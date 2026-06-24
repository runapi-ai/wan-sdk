package ai.runapi.wan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.json.Json;
import ai.runapi.wan.types.AspectRatio;
import ai.runapi.wan.types.CompletedTextToImageResponse;
import ai.runapi.wan.types.TextToImageModel;
import ai.runapi.wan.types.TextToImageParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class WanLiveApiSmokeTest {
  @Test
  void textToImageRunAgainstLiveRunApi() throws Exception {
    assumeTrue("true".equals(System.getenv("RUNAPI_JAVA_LIVE_WAN_SMOKE")));

    String baseUrl = requireEnv("RUNAPI_BASE_URL");
    String apiKey = requireEnv("RUNAPI_API_KEY");
    String callbackUrl = requireEnv("RUNAPI_CALLBACK_URL");
    Path outputPath = Paths.get(System.getenv().getOrDefault("RUNAPI_JAVA_LIVE_WAN_OUTPUT", "build/live-wan-smoke-result.json"));
    Files.createDirectories(outputPath.getParent());

    try (WanClient client = WanClient.builder().apiKey(apiKey).baseUrl(baseUrl).build()) {
      CompletedTextToImageResponse response =
          client.textToImage()
              .run(
                  TextToImageParams.builder()
                      .model(TextToImageModel.WAN_2_7_IMAGE)
                      .prompt("A small red cube on a plain white table, studio product photo")
                      .callbackUrl(callbackUrl)
                      .aspectRatio(AspectRatio.SQUARE)
                      .outputResolution("1k")
                      .outputCount(1)
                      .watermark(false)
                      .build(),
                  RequestOptions.builder()
                      .pollingInterval(Duration.ofSeconds(10))
                      .pollingMaxWait(Duration.ofMinutes(12))
                      .maxRetries(0)
                      .build());

      assertEquals("completed", response.getStatus().value());
      assertNotNull(response.getImages());
      assertFalse(response.getImages().isEmpty());
      assertNotNull(response.getImages().get(0).getUrl());

      ObjectNode result = Json.mapper().createObjectNode();
      result.put("id", response.getId());
      result.put("status", response.getStatus().value());
      result.put("image_url", response.getImages().get(0).getUrl());
      result.put("callback_url", callbackUrl);
      Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
    }
  }

  private static String requireEnv(String name) {
    String value = System.getenv(name);
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalStateException(name + " is required");
    }
    return value;
  }
}
