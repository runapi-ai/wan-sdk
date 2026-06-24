package ai.runapi.wan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.json.Json;
import ai.runapi.wan.types.AspectRatio;
import ai.runapi.wan.types.CompletedTextToImageResponse;
import ai.runapi.wan.types.TextToImageModel;
import ai.runapi.wan.types.TextToImageParams;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WanLocalApiSmokeTest {
  private HttpServer server;
  private final List<CapturedRequest> requests = new ArrayList<CapturedRequest>();

  @BeforeEach
  void startServer() throws IOException {
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext("/api/v1/wan/text_to_image", this::handleTextToImage);
    server.start();
  }

  @AfterEach
  void stopServer() {
    server.stop(0);
  }

  @Test
  void runUsesApacheTransportAgainstLocalApi() throws Exception {
    try (WanClient client =
        WanClient.builder()
            .apiKey("sk-test")
            .baseUrl("http://127.0.0.1:" + server.getAddress().getPort())
            .build()) {
      CompletedTextToImageResponse response =
          client.textToImage()
              .run(
                  TextToImageParams.builder()
                      .model(TextToImageModel.WAN_2_7_IMAGE)
                      .prompt("A small paper lantern on a desk")
                      .aspectRatio(AspectRatio.SQUARE)
                      .outputCount(1)
                      .build(),
                  RequestOptions.builder()
                      .pollingInterval(Duration.ofMillis(1))
                      .pollingMaxWait(Duration.ofSeconds(2))
                      .maxRetries(0)
                      .build());

      assertEquals("completed", response.getStatus().value());
      assertEquals("https://file.runapi.ai/local-smoke.png", response.getImages().get(0).getUrl());
    }

    assertEquals(3, requests.size());
    CapturedRequest create = requests.get(0);
    assertEquals("POST", create.method);
    assertEquals("/api/v1/wan/text_to_image", create.path);
    assertEquals("Bearer sk-test", create.header("Authorization"));
    assertEquals("runapi-sdk-java/0.1.0", create.header("User-Agent"));
    JsonNode body = Json.mapper().readTree(create.body);
    assertEquals("wan-2.7-image", body.get("model").asText());
    assertEquals("A small paper lantern on a desk", body.get("prompt").asText());
    assertEquals("1:1", body.get("aspect_ratio").asText());
    assertEquals(1, body.get("output_count").asInt());

    assertEquals("GET", requests.get(1).method);
    assertEquals("/api/v1/wan/text_to_image/task_local_123", requests.get(1).path);
    assertEquals("GET", requests.get(2).method);
    assertEquals("/api/v1/wan/text_to_image/task_local_123", requests.get(2).path);
  }

  private void handleTextToImage(HttpExchange exchange) throws IOException {
    CapturedRequest captured = CapturedRequest.from(exchange);
    requests.add(captured);

    if ("POST".equals(captured.method)) {
      write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"processing\"}");
      return;
    }

    assertEquals("/api/v1/wan/text_to_image/task_local_123", captured.path);
    if (requests.size() == 2) {
      write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"processing\"}");
    } else {
      write(
          exchange,
          200,
          "{\"id\":\"task_local_123\",\"status\":\"completed\",\"images\":[{\"url\":\"https://file.runapi.ai/local-smoke.png\"}]}");
    }
  }

  private static void write(HttpExchange exchange, int status, String body) throws IOException {
    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
    exchange.getResponseHeaders().set("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, bytes.length);
    try (OutputStream out = exchange.getResponseBody()) {
      out.write(bytes);
    }
  }

  private static final class CapturedRequest {
    private final String method;
    private final String path;
    private final com.sun.net.httpserver.Headers headers;
    private final String body;

    private CapturedRequest(String method, String path, com.sun.net.httpserver.Headers headers, String body) {
      this.method = method;
      this.path = path;
      this.headers = headers;
      this.body = body;
    }

    private static CapturedRequest from(HttpExchange exchange) throws IOException {
      return new CapturedRequest(
          exchange.getRequestMethod(),
          exchange.getRequestURI().getPath(),
          exchange.getRequestHeaders(),
          new String(readAll(exchange), StandardCharsets.UTF_8));
    }

    private String header(String name) {
      List<String> values = headers.get(name);
      if (values == null || values.isEmpty()) {
        return null;
      }
      return values.get(0);
    }

    private static byte[] readAll(HttpExchange exchange) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int read;
      while ((read = exchange.getRequestBody().read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
      return out.toByteArray();
    }
  }
}
